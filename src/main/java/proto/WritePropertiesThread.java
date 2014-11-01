package proto;

import java.util.concurrent.Callable;


public class WritePropertiesThread implements Callable<String> {
    @Override
    public String call() {
        return WriteProperties.writeProperties();
    }
}