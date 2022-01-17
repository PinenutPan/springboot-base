package com.sinosoft.cms.base.utils.db;

import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class OracleDBUtils {

    private static String url = "jdbc:oracle:thin:@//10.0.4.49:1521/mis";
    private static String username = "mis";
    private static String password = "mis";


    public static Connection getConnection(String url, String username, String password) {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<Map<String, Object>> querySql(String querySql, List parameters, String url, String username, String password) {
        Connection connection = null;
        try {
            connection = getConnection(url, username, password);
            List<Map<String, Object>> result = JdbcUtils.executeQuery(connection, querySql, parameters);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        } finally {
            JdbcUtils.close(connection);
        }
    }

    public static void main(String[] args) {

        List params = new ArrayList();
        params.add("201903");
        params.add(7);
        params.add("2017-08-12 00:00:00");
        System.out.println(JSON.toJSONString(
                querySql("select * from AGENTMESSAGE where  INDEXCALNO=? AND INITGRADEID= ? and OUTWORKDATE = to_date( ? , 'YYYY-MM-DD HH24:MI:SS' )", params, url, username, password)
        ));


        System.out.println(getCount("select * from AGENTMESSAGE where  INDEXCALNO=? AND INITGRADEID= ? and OUTWORKDATE = to_date( ? , 'YYYY-MM-DD HH24:MI:SS' )","?"));
    }

    public static int getCount(String str, String key) {
        if (str == null || key == null || "".equals(str.trim()) || "".equals(key.trim())) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while ((index = str.indexOf(key, index)) != -1) {
            index = index + key.length();
            count++;
        }
        return count;
    }
}
