package com.xuhe.hadoop.study.zookeeper;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZkClientTest {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(
            "192.168.5.151:2181,192.168.5.151:2182,192.168.5.151:2183");

        String node = "/myapp";

        //订阅监听事件
        childChangesListener(zkClient, node);
        dataChangesListener(zkClient, node);
        stateChangesListener(zkClient);
    }

    /**
     * 订阅children 变化
     * @param zkClient
     * @param node
     */
    public static void childChangesListener(ZkClient zkClient, String path) {
        zkClient.subscribeChildChanges(path, new IZkChildListener() {

            @Override
            public void handleChildChange(String parentPath,
                                          List<String> currentChilds) throws Exception {
                System.out.println("clidren of path " + parentPath + " : " + currentChilds);
            }
        });

    }

    /**
     * 订阅节点数据变化
     * @param zkClient
     * @param path
     */
    public static void dataChangesListener(ZkClient zkClient, String path) {
        zkClient.subscribeDataChanges(path, new IZkDataListener() {

            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Data of " + dataPath + " has changed.");
            }
        });
    }

    /**
     * 订阅状态变化
     * @param zkClient
     */
    public static void stateChangesListener(ZkClient zkClient) {
        zkClient.subscribeStateChanges(new IZkStateListener() {

            @Override
            public void handleStateChanged(KeeperState state) throws Exception {
                System.out.println("handleStateChanged");
            }

            @Override
            public void handleSessionEstablishmentError(Throwable error) throws Exception {
                System.out.println("handleSessionEstablishmentError");
            }

            @Override
            public void handleNewSession() throws Exception {
                System.out.println("handleNewSession");
            }
        });
    }

}
