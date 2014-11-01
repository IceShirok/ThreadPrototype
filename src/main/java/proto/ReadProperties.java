package proto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ReadProperties {
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");
    private static int COUNT = 0;

    public static String readPropeties() {
        System.out.println("Entering readProperties() in "+DATE_FORMAT.format(new Date()));
        Thread.currentThread().setName("ReadProperties-"+COUNT++);
        Properties protoProp = new Properties();
        String propLoc = "message.properties";
        String root = "C:\\test\\";
        InputStream is = null;
        if(new File(root + "myfolder").exists()) {
            propLoc = root + "myfolder\\" + propLoc;
        } else {
            propLoc = root + "otherFolder\\" + propLoc;
        }
        try {
            System.out.println("Instatiating input stream from "+propLoc);
            is = new FileInputStream(propLoc);
            protoProp.load(is);
            String message = protoProp.getProperty("message");
            System.out.println(message);
            Thread.sleep(2500);
        } catch (InterruptedException | IOException e) {
            System.out.println("ReadProperties was interrupted.");
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Exiting readProperties() in "+DATE_FORMAT.format(new Date()));
        return propLoc;
    }
}
