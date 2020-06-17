package org.wso2.carbon.group.mgt.util;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.wso2.carbon.utils.CarbonUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
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

    public Connection getConnectionString() throws Exception{
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    private void initDatabaseConfig() {
        try {
            String dataSourcesDir = CarbonUtils.getCarbonConfigDirPath() + File.separator + "datasources";
            File masterDSFile = new File(dataSourcesDir + File.separator + "master-datasources.xml");
            /* initialize the master data sources first */
            if (masterDSFile.exists()) {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(masterDSFile);
                doc.getDocumentElement().normalize();
                NodeList nList = doc.getElementsByTagName("jndiConfig");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        if("jdbc/WSO2IdentityDB".equals(nNode.getChildNodes().item(1).getTextContent())) {
                            String dbName = doc.getElementsByTagName("url").item(temp).getTextContent();
                            String userName = doc.getElementsByTagName("username").item(temp).getTextContent();
                            String password = doc.getElementsByTagName("password").item(temp).getTextContent();
                            String driverClassName = doc.getElementsByTagName("driverClassName").item(temp).getTextContent();
                            dataSource.setDriverClassName(driverClassName);
                            dataSource.setUrl(dbName);
                            dataSource.setUsername(userName);
                            dataSource.setPassword(password);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error when looking up the Identity Data Source.", e);
        }
    }
}
