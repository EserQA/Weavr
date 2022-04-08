package io.weavr.utils;

import java.io.FileInputStream;
import java.util.Properties;
/**
 * reads the properties file application.properties
 */
public class PropertyReader {
    private static Properties properties;

    static {

        try {
            // what file to read
            String path = "application.properties";
            // read the file into java, finds the file using the string path
            FileInputStream input = new FileInputStream(path);
            // properties --> class that store properties in key / value format
            properties = new Properties();
            // the values from the file input is loaded / fed in to the properties object
            properties.load(input);

            input.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String get(String keyName) {

        return properties.getProperty(keyName);
    }

}
