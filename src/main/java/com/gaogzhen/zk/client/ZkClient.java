package com.gaogzhen.zk.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author: Administrator
 * @createTime: 2024/02/19 10:52
 */
public class ZkClient {

    private String connectStr = "node1:2181,node2:2181,node3:2181";
    private int sessionTimeout = 2000;

    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {

        zkClient = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                // System.out.println("==============");
                // List<String> children = null;
                // try {
                //     children = zkClient.getChildren("/", true);
                //
                //     for (String child : children) {
                //         System.out.println(child);
                //     }
                //     System.out.println("==============");
                // } catch (KeeperException e) {
                //     throw new RuntimeException(e);
                // } catch (InterruptedException e) {
                //     throw new RuntimeException(e);
                // }

            }
        });


    }

    @Test
    public void create() throws InterruptedException, KeeperException {
        String msg = zkClient.create("/gaogzhen", "java".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);

        for (String child : children) {
            System.out.println(child);
        }

        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void exists() throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/gaogzhen1", false);
        System.out.println(stat == null ? "no exist" : "exist");
    }
}
