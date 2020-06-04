package org.wso2.carbon.group.mgt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {
    private static final Log logger = LogFactory.getLog(PropertyFileReader.class);
    private static Properties prop = new Properties();
    public static Properties readPropertyFile() throws Exception {
        if (prop.isEmpty()) {
            InputStream input = PropertyFileReader.class.getClassLoader().getResourceAsStream("datasource.properties");
            try {
                prop.load(input);
            } catch (IOException ex) {
                logger.error(ex);
                throw ex;
            } finally {
                if (input != null) {
                    input.close();
                }
            }
        }
        return prop;
    }

    public static void main(String[] args) throws Exception {
        String dbname = PropertyFileReader.readPropertyFile().getProperty("dbName");
        System.out.println(dbname);
    }
}
