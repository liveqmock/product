package com.cots.junit;

import junit.framework.TestCase;

import com.cots.blc.BLContext;
import com.cots.blc.BLCPool;
import com.cots.util.FileUtil;
import com.cots.bean.BeanFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;

/**
 * Test case for BLC object based on junit framework.
 *
 * User: chugh
 * Date: 2005-5-27
 * Time: 22:22:10
 */
public class TestBase extends TestCase{
    protected BLContext blContext;
    protected String configRoot;
    protected BLCPool blcPool;

    public void setUp(){
        String log4j = configRoot + "/log4j.properties";
        File log4jFile = new File(log4j);
        if (log4jFile.exists() && log4jFile.isFile()) {
            Properties p = null;
            try {
                p = FileUtil.readPropFile(log4jFile, "${", "}", System.getProperties());
            } catch (IOException e) {
                System.out.println("IOException when reading /WEB-INF/config/log4j.properties");
            }
            PropertyConfigurator.configure(p);
        }


        //initialize cots managed beans;
        BeanFactory beanFactory = new BeanFactory();
        if (!beanFactory.init(configRoot + "/beans")) {
            System.out.println("WARNING: not all BEAN definition files were initialized successfully," +
                    " see log for detailed message");
        }

        //initialize BLContext object;
        try{
            blContext = new BLContext(configRoot + "/cots-blcontext.xml", beanFactory);
        }catch(IOException e){
            e.printStackTrace();
        }

        blcPool = blContext.getBLCPool();
    }

    public void tearDown(){
        blContext.shutdown();
    }
}
