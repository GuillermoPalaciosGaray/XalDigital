package edu.XalDigital.com.test;

import static org.junit.Assert.assertTrue;

import edu.XalDigital.com.resources.RestClient;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;


public class RestClientTest extends TestBase
{
    TestBase testBase;
    RestClient restClient;

    @Before
    public void setUp(){
        testBase = new TestBase();
        String URL = prop.getProperty("URL");

        restClient =  new RestClient();
        
        }


}
