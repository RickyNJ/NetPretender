package com.rickynj.repository.valkey;

import com.rickynj.device.DeviceManager;
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

public class ValkeyClient {
    transient public final Logger logger  = LoggerFactory.getLogger(ValkeyClient.class);
    private String checksum;
    private RedissonClient client;
    private static ValkeyClient valkeyClient;

    public static ValkeyClient getValkeyClient() {
        if (valkeyClient == null) {
            valkeyClient = new ValkeyClient(com.rickynj.config.Config.redissonConfigFile, com.rickynj.config.Config.commandsFile);
        }
        return valkeyClient;
    }

    public String getValueFromValkey(String key, CommandContext ctx){
        String fullKey = ctx.device.name + "." + key;
        logger.info("getting value for key: {}", fullKey);
        RBucket<String> rBucket = client.getBucket(fullKey);
        return rBucket.get();
    }

    public void setValueInValkey(String key, String val, CommandContext ctx) {
        String fullKey = ctx.device.name + "." + key;
        logger.info("setting value for key: {}", fullKey);
        RBucket<String> rBucket = client.getBucket(fullKey);
        rBucket.set(val);
    }

    // TODO: obviously look at the error handling here
    private ValkeyClient(String configPath, String commandPath)  {
        try {
            Config config = Config.fromYAML(new FileInputStream(configPath));
            this.client = Redisson.create(config);
            checksum = getChecksum(commandPath);
            logger.info("{}, {}", configPath, checksum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // the file name will act as key to get the checksum of the cached devicemanager
    // the checksum will be the key to get the actual devicemanager
    public DeviceManager getCachedDeviceManagerIfExists(String commandFileName) {
        logger.info("looking for a cached device for filename {}", commandFileName);
        RBucket<String> cachedChecksumBucket = client.getBucket(commandFileName);
        String cachedCheckSum = cachedChecksumBucket.get();
        if (cachedCheckSum == null) {
            logger.info("file has never been cached before");
            // file has never been hashed before, or has been deleted.
            return null;
        } else {
            logger.info("file has been cached before");
            RBucket<DeviceManager> deviceManagerRBucket = client.getBucket(checksum);
            DeviceManager deviceManager = deviceManagerRBucket.get();
            if (deviceManager == null) {
                logger.info("file has changed, deleting old cache");
                // file has changed, delete previous cache
                RBucket<DeviceManager> oldDeviceManager = client.getBucket(cachedCheckSum);
                oldDeviceManager.delete();
            }
            logger.info("devicemanager found: {}", deviceManager);
            return deviceManager;
        }
    }

    public void cacheNewDeviceManager(String commandFileName, DeviceManager deviceManager) {
        logger.info("saving new devicemanager to cache, {} {}", commandFileName, deviceManager);

        RBucket<String> checksumCache = client.getBucket(commandFileName);
        checksumCache.set(getChecksum(commandFileName));
        RBucket<DeviceManager> deviceManagerRBucket = client.getBucket(checksum);
        deviceManagerRBucket.set(deviceManager);
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