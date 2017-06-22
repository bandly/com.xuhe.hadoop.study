package com.xuhe.hadoop.study.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class PhoenixHelper {
    private static final Logger LOG               = Logger.getLogger(PhoenixHelper.class);
    private static final String JDBC_DRIVER_CLASS = "org.apache.phoenix.jdbc.PhoenixDriver";
    // jdbc:phoenix:192.168.1.19:2181:/hbase中的/hbase为HBase注册到zooKeeper的根目录, 如使用HBase自带的zooKeeper,默认为"hbase"
    private static final String JDBC_URL          = "jdbc:phoenix:10.3.51.1,10.3.51.2,10.3.51.3";

    public static Connection getConn() {
        try {
            // 注册Driver
            Class.forName(JDBC_DRIVER_CLASS);
            // 返回Connection对象
            return DriverManager.getConnection(JDBC_URL);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            LOG.error("获得连接失败!");
            return null;
        }
    }

    public static void closeConn(Connection conn) {
        try {
            if (!conn.isClosed())
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("关闭连接失败!");
        }
    }

}
