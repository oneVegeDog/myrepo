package com.fileSystemManage.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class ConnectionUtil {
    static DataSource dataSource = null;
    static Properties prop = null;
    static{
        //数据源配置
        prop = new Properties();
        //读取配置文件
        InputStream is = JdbcUtils.class.getResourceAsStream("/druid.properties");
        try {
            prop.load(is);
            //返回的是DataSource
            dataSource = DruidDataSourceFactory.createDataSource(prop);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
