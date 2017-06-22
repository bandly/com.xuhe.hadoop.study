package com.xuhe.hadoop.study.zookeeper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;

/**
 * Zookeeper 测试
 * TODO 类实现描述 
 * @Company 杭州木瓜科技有限公司
 * @className: Test.java
 * @author xuhe@amugua.com 
 * @date 2017年6月22日 下午3:54:18
 */
public class Test {

    //会话超时时间，设置为与系统默认时间一致
    private static final int SESSION_TIMEOUT = 60 * 1000;

    //创建ZooKeeper实例
    private ZooKeeper        zk;

    //创建　Watcher 实例
    private Watcher          wh              = new Watcher() {

                                                 /**
                                                  * Watcher 事件
                                                  */
                                                 @Override
                                                 public void process(WatchedEvent event) {
                                                     System.out.println(
                                                         "WatcherEvent >>>" + event.toString());

                                                 }

                                             };

    private void createZKInstance() {
        try {
            zk = new ZooKeeper("192.168.5.151:2181,192.168.5.151:2182,192.168.5.151:2183",
                Test.SESSION_TIMEOUT, this.wh);
            if (!zk.getState().equals(States.CONNECTED)) {
                while (true) {
                    if (zk.getState().equals(States.CONNECTED)) {
                        break;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            //zkclient 开源地址：https://github.com/sgroschupf/zkclient 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ZKOperations() {
        System.out.println(
            "\n 1.创建ZooKeeper 节点(znode:zoo2,数据：myData2,权限： OPEN_ACL_UNSAFE,节点类型：Persistent)");

        try {
            zk.create("/zoo2", "myData2".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            System.out.println("\n2.查看是否创建成功");
            System.out.println(new String(zk.getData("/zoo2", this.wh, null)));//添加watch

            //前面一行我们已经添加了对/zoo2节点的监视，所以这里对/zoo2进行修改的时候的，会触发Watch事件
            System.out.println("\n3.修改节点数据 ");
            zk.setData("/zoo2", "bandly2016221641".getBytes(), -1);

            //这里再次进行修改，则不会触发Watch事件，这就是我们验证的ZK的一个特性"一次性触发",也就是说设置一次监视，只会对下次操作起一次作用
            System.out.println("\n3-1 再次修改节点数据");
            zk.setData("/zoo2", "xuhe201606221644".getBytes(), -1);

            System.out.println("\4. 查看是否修改成功");
            System.out.println(new String(zk.getData("/zoo2", false, null)));

            System.out.println("\n5.删除节点");
            zk.delete("/zoo2", -1);

            System.out.println("\n6. 查看节点是否被删除");
            System.out.println("节点状态：[" + zk.exists("/zoo2", false) + "]");

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void ZKClose() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Test dm = new Test();
        dm.createZKInstance();
        dm.ZKOperations();
        dm.ZKClose();
    }

}
