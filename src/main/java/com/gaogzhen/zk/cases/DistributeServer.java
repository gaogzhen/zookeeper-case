package com.gaogzhen.zk.cases;

import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @author: Administrator
 * @createTime: 2024/02/20 09:24
 */
public class DistributeServer {

    private String connectStr = "node1:2181,node2:2181,node3:2181";
    private int sessionTimeout = 2000;

    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeServer server = new DistributeServer();
        // 1连接zk
        server.getConnect();
        // 2注册服务器到zk集群
        server.register(args[0]);
        // 3启动业务逻辑(睡觉)
        server.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void register(String hostname) throws InterruptedException, KeeperException {
        String msg = zkClient.create("/servers/" + hostname, hostname.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println(msg);
        System.out.println(hostname + " is online");
    }

    private void getConnect() throws IOException {
         zkClient = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
