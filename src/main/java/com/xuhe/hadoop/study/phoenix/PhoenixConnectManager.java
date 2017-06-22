package com.xuhe.hadoop.study.phoenix;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.phoenix.jdbc.PhoenixDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PhoenixConnectManager {
    private static Logger logger = LoggerFactory.getLogger(PhoenixConnectManager.class);

    static {
        try {
            Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }

    }

    public static Connection getConnecton() {
        Connection c = null;
        try {
            logger.info("----------start getgetConnection------------");
            /*  conn = DriverManager
                .getConnection("jdbc:phoenix:10.3.51.1,10.3.51.2,10.3.51.3:2181/hbase");
            QueryServices aa = PhoenixDriver.INSTANCE.getQueryServices();*/
            Properties info = new Properties();
            info.setProperty("user", "");
            info.setProperty("password", "");
            c = PhoenixDriver.INSTANCE.connect("jdbc:phoenix:hslave01,hslave02,hslave03:2181/hbase",
                info);
            System.out.println(c);
            logger.info("----------end getgetConnection------------");
        } catch (SQLException e) {
            logger.info("get jdbc Connection error");
            e.printStackTrace();
        }
        return c;
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnecton();
            PreparedStatement pstmt = null;
            pstmt = conn.prepareStatement("select * from DM_STORAGE_GOOD_SALE_DM limit 1");
            ResultSet rs = pstmt.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            JSONArray array = new JSONArray();
            while (rs.next()) {
                JSONObject obj = new JSONObject();
                for (int i = 1; i <= md.getColumnCount(); i++) {
                    obj.put(md.getColumnName(i), rs.getString(i));
                }
                System.out.println(JSON.toJSON(obj));
                array.add(obj);
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
