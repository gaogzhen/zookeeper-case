package com.gaogzhen.zk.cases;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * @author: Administrator
 * @createTime: 2024/02/20 09:24
 */
public class DistributeClient {

    private String connectStr = "node1:2181,node2:2181,node3:2181";
    private int sessionTimeout = 2000;

    private ZooKeeper zkClient;

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        DistributeClient server = new DistributeClient();
        // 1连接zk
        server.getConnect();
        // 2监听/servers下面的节点变化
        server.monitor();
        // 3启动业务逻辑(睡觉)
        server.business();
    }

    private void business() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    private void monitor() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/servers", true);

        HashMap<String, String> serverMap = new HashMap<>();
        for (String child : children) {
            byte[] data = zkClient.getData("/servers/" + child, false, null);
            serverMap.put(child, new String(data));
        }

        System.out.println(serverMap);
    }

    private void getConnect() throws IOException {
         zkClient = new ZooKeeper(connectStr, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    monitor();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (KeeperException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
