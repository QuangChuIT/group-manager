package org.wso2.carbon.group.mgt.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.wso2.carbon.group.mgt.PropertyFileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URLDecoder;
import java.sql.Connection;

public class ConfigUtil {
    private static volatile ConfigUtil instance;
    private static final Log log = LogFactory.getLog(ConfigUtil.class);
    private static final BasicDataSource dataSource = new BasicDataSource();

    private ConfigUtil() {
        this.initDatabaseConfig();
    }

    public static ConfigUtil getInstance() {
        if (instance == null) {
            synchronized (ConfigUtil.class) {
                if (instance == null) {
                    instance = new ConfigUtil();
                }
            }
        }
        return instance;
    }

//    public static void main(String[] args) throws Exception {
//        ConfigUtil.getInstance().getConnectionString();
////        getConnectionString();
//    }
    public Connection getConnectionString() throws Exception{
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    private void initDatabaseConfig() {
        try {
            String dbName = PropertyFileReader.readPropertyFile().getProperty("DbName");
            String userName = PropertyFileReader.readPropertyFile().getProperty("UserName");
            String password = PropertyFileReader.readPropertyFile().getProperty("Password");
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(dbName);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);
        } catch (Exception e) {
            log.error("Error when looking up the Identity Data Source.", e);
        }
    }
}
