package edu.XalDigital.com.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase {

    public Properties prop;

    public TestBase() {

        String configFilePath = "src/main/java/edu/XalDigital/com/config/config.properties";

        try {
            prop = new Properties();
            FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + configFilePath);
            prop.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
