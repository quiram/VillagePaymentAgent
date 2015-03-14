package com.kittylyst.VillagePaymentAgent.db.sample;

import com.kittylyst.VillagePaymentAgent.db.EmbeddedDB;
import com.kittylyst.VillagePaymentAgent.db.EmbeddedDBConfig;
import org.h2.tools.Server;
import org.haftrust.verifier.dao.DeviceDAO;
import org.haftrust.verifier.model.Device;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class App {

    public static final int noExampleDevices = 5;
    public static final Map<String, Device> exampleDevices = new HashMap<String, Device>();
    private static final String deviceNamePrefix = "d";
    private static final String allocation = "allocation";
    private static final String modelNo = "modelNo";
    private static final String productNo = "productNo";

    public static void main( String[] args ) throws SQLException {
        App.generateExampleDevices();

        ApplicationContext appContext = new ClassPathXmlApplicationContext("config/ApplicationContext.xml");

        EmbeddedDB db = (EmbeddedDB) appContext.getBean("db");
        db.createConnection(EmbeddedDB.DEFAULT_CONNECTION_NAME);

        EmbeddedDBConfig dbConfig =(EmbeddedDBConfig) appContext.getBean("dbConfig");

        //DBBrowser browser = new DBBrowser();
        try {
            //browser.start();
            DeviceDAO deviceDao = (DeviceDAO) appContext.getBean("deviceDao");
            String dName = deviceNamePrefix + 0;
            deviceDao.saveDevice(exampleDevices.get(dName));

            db.createConnection(EmbeddedDB.DEFAULT_CONNECTION_NAME);
            String exampleQuery = "SELECT * FROM " + dbConfig.getSchemaName() +".HT_DEVICE";
            ResultSet result = db.executeQuery(EmbeddedDB.DEFAULT_CONNECTION_NAME, exampleQuery);

            while( result.next() )
            {
                String name = result.getString("imei");
                System.out.println( name );
            }

            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //browser.stop();
            db.close();
        }
    }

    public static void generateExampleDevices() {
        final String suffixAllocation = "AL";

        for (long i = 0; i < noExampleDevices; i++) {
            String deviceName = deviceNamePrefix + i;
            Device d = new Device();
            d.setImei(i);
            d.setAllocation(deviceName + suffixAllocation);
            d.setModelNumber(deviceName + modelNo);
            d.setProductNumber(deviceName + productNo);
            exampleDevices.put(deviceName, d);
        }
    }
}

class DBBrowser {
    //http://www.h2database.com/javadoc/index.html >> Server
    private Server webServer;

    public void start() throws Exception {
        try {
            webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
            webServer.openBrowser("http://localhost:8082");
        } catch(Exception ex) {
            System.out.println("Failed to start DBBrowser");
            stop();
        }
    }


    public void stop() {
        if(webServer != null) {
            webServer.shutdown();
        }
            /*
        if(dbServer != null) {
            dbServer.shutdown();
        }
        */
    }

}
