package com.rickynj.repository.valkey;

import com.rickynj.device.DeviceManager;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.FileInputStream;
import java.io.IOException;

public class ValkeyConnector {
    private RedissonClient client;

    public ValkeyConnector(DeviceManager deviceManager) throws IOException {
        Config config = Config.fromYAML(new FileInputStream("/opt/configs/redisson.yaml"));
        this.client = Redisson.create(config);
        RBucket<DeviceManager> dm = client.getBucket("dm");
        dm.set(deviceManager);
        System.out.println(dm.get());
    }
}
