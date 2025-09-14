package com.rickynj.repository.valkey;

import com.rickynj.exception.ValkeyClientException;
import com.rickynj.organisation.Organisation;
import com.rickynj.domain.CommandContext;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.rickynj.config.Constants.COMMANDSFILE;
import static com.rickynj.config.Constants.REDISSONCONFIGFILEPATH;

public class ValkeyClient {
    public final Logger logger  = LoggerFactory.getLogger(ValkeyClient.class);
    private String checksum;
    private RedissonClient client;
    private static ValkeyClient valkeyClient;

    public static ValkeyClient getValkeyClient() {
        if (valkeyClient == null && Files.exists(Path.of(REDISSONCONFIGFILEPATH))) {
            valkeyClient = new ValkeyClient(REDISSONCONFIGFILEPATH);
        }
        return valkeyClient;
    }

    public String getValueFromValkey(String key, CommandContext ctx){
        String fullKey = ctx.device.name + "." + key;
//        logger.info("getting value for key in valkey: {}", fullKey);
        RBucket<String> rBucket = client.getBucket(fullKey);
        return rBucket.get();
    }

    public void setValueInValkey(String key, String val, CommandContext ctx) {
        String fullKey = ctx.device.name + "." + key;
//        logger.info("setting value for key: {}", fullKey);
        RBucket<String> rBucket = client.getBucket(fullKey);
        rBucket.set(val);
    }

    // TODO: obviously look at the error handling here
    private ValkeyClient(String configPath)  {
        try {
            Config config = Config.fromYAML(new FileInputStream(configPath));
            this.client = Redisson.create(config);
        } catch (Exception e) {
            throw new ValkeyClientException("Exception while starting Valkey client: ", e);
        }
    }


    // the file name will act as key to get the checksum of the cached devicemanager
    // the checksum will be the key to get the actual devicemanager
    public Organisation getCachedDeviceManagerIfExists() {
//        logger.info("looking for a cached device for filename {}", COMMANDSFILE);
        RBucket<String> cachedCheckSumBucket = client.getBucket(COMMANDSFILE);
        String cachedCheckSum = cachedCheckSumBucket.get();
        if (cachedCheckSum == null) {
//            logger.info("file has never been cached before");
            // no checksum has been stored for this filename
            // file has never been hashed before, or has been deleted.
            return null;
        } else {
//            logger.info("file has been cached before");
            checksum = getChecksum(COMMANDSFILE);
            RBucket<Organisation> deviceManagerRBucket = client.getBucket(checksum);
            Organisation organisation = deviceManagerRBucket.get();
            if (organisation == null) {
//                logger.info("file has changed, deleting old cache");
                // file has changed, delete previous cache
                RBucket<Organisation> oldDeviceManager = client.getBucket(cachedCheckSum);
                oldDeviceManager.delete();
            }
//            logger.info("devicemanager found: {}", organisation);
            return organisation;
        }
    }

    public void cacheNewDeviceManager(Organisation organisation) {
//        logger.info("saving new devicemanager to cache, {} {}", COMMANDSFILE, organisation);
        RBucket<String> checksumCache = client.getBucket(COMMANDSFILE);
        checksumCache.set(getChecksum(COMMANDSFILE));
        RBucket<Organisation> deviceManagerRBucket = client.getBucket(checksum);
        deviceManagerRBucket.set(organisation);
    }

    private String getChecksum(String filepath)  {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(Path.of(filepath)));
            return new BigInteger(1, hash).toString(16);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException("Error trying to get checksum of file", e);
        }
    }
}