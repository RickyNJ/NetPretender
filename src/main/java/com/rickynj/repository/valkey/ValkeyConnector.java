package com.rickynj.repository.valkey;

import com.rickynj.device.DeviceManager;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.FileInputStream;
import java.io.IOException;

public class ValkeyConnector {
    public RedissonClient client;

    public ValkeyConnector() throws IOException {
        Config config = Config.fromYAML(new FileInputStream("src/main/resources/redisson.yaml"));
//        Config config = Config.fromYAML(new FileInputStream("/opt/configs/redisson.yaml"));
        this.client = Redisson.create(config);
        RBucket<DeviceManager> dm = client.getBucket("dm");
    }
}
