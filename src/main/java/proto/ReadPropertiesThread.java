package proto;

import java.util.concurrent.Callable;


public class ReadPropertiesThread implements Callable<String> {
    @Override
    public String call() {
        return ReadProperties.readPropeties();
    }
}