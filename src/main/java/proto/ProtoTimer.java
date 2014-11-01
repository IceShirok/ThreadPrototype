package proto;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ProtoTimer {

    private static int THREAD_NAME_COUNT = 0;
    private Timer timer;
    private Timer counting;

    public ProtoTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ReadTask(), 0, 10000);
        timer.scheduleAtFixedRate(new WriteTask(), 0, 15000);
        counting = new Timer();
        counting.scheduleAtFixedRate(new CountTask(), 0, 1000);
    }
    
    private class CountTask extends TimerTask {
        private int COUNT = 0;
        @Override
        public void run() {
            System.out.println(COUNT++);
            //ProtoThreadQueue.getInstance().execute();
        }
    }
    
    private class WriteTask extends TimerTask {        
        @Override
        public void run() {
            insertThread("Write");
        }
    }

    private class ReadTask extends TimerTask {
        @Override
        public void run() {
            insertThread("Read");
        }
        
    }

    public synchronized void insertThread(String type) {
        if(type.equals("Read")) {
            insertReadThread();
        } else if(type.equals("Write")) {
            insertWriteThread();
        }
    }

    public void insertReadThread() {
        System.out.println("Inserting read thread");
        ReadPropertiesThread readThread = new ReadPropertiesThread();
        ProtoThreadQueue.getInstance().addToQueue(readThread, "read"+THREAD_NAME_COUNT++);
    }

    public void insertWriteThread() {
        System.out.println("Inserting write thread");
        WritePropertiesThread writeThread = new WritePropertiesThread();
        Future<?> future = ProtoThreadQueue.getInstance().addToQueue(writeThread, "write"+THREAD_NAME_COUNT++);
        try {
            System.out.println(future.get().toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
