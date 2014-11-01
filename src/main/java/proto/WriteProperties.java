package proto;

import java.io.File;
import java.util.Date;

public class WriteProperties {

    public static String writeProperties() {
        System.out.println("Entering writeProperties() in "+ReadProperties.DATE_FORMAT.format(new Date()));

        String myFolderRoot = "C:\\test\\myfolder";
        String otherFolderRoot = "C:\\test\\otherFolder";
        File root = new File(myFolderRoot);
        File otherRoot = new File(otherFolderRoot);
        String finalRoot = null;
        boolean succeeded = false;
        try {
            succeeded = root.exists() ? root.renameTo(otherRoot) : otherRoot.renameTo(root);
            System.out.println("was rename successful? " + succeeded);
            finalRoot = root.exists() ? myFolderRoot : otherFolderRoot;

            Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("WriteProperties was interrupted.");
        }
        System.out.println("WriteProperties final root is: "+finalRoot);
        System.out.println("Exiting writeProperties() in "+ReadProperties.DATE_FORMAT.format(new Date()));
        return finalRoot;
    }

}
