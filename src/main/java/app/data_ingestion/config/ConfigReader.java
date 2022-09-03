package app.data_ingestion.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import app.data_ingestion.helpers.LiteralConstants;

public class ConfigReader {

    final static String appConfigPath = LiteralConstants.APP_CONFIG_PATH;

    //singleton design pattern
    private static ConfigReader configReader = new ConfigReader();
    Properties property;

    private ConfigReader() {
    }


    /**
     * @return ConfigReader
     */
    public static ConfigReader getInstance() {
        return configReader;
    }

    /**
     * read app_config.properties file
     * and return prop value
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        if (property == null) {
            try (InputStream input = new FileInputStream(appConfigPath)) {
                property = new Properties();
                property.load(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return property.getProperty(key);
    }

}
