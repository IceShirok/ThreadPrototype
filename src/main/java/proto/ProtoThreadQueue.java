package proto;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoThreadQueue {

    public final static ProtoThreadQueue instance = new ProtoThreadQueue();

    private volatile LinkedBlockingQueue<NamedCallable<?>> queue;
    private ExecutorService executor;
    private NamedCallable<?> callee;
    private Future<?> future;

    public static ProtoThreadQueue getInstance() {
        return instance;
    }

    private ProtoThreadQueue() {
        queue = new LinkedBlockingQueue<>();
        executor = Executors.newSingleThreadExecutor();
    }

    public Future<?> execute() {
        while(!queue.isEmpty()) {
            checkToInterrupt();
            if(future == null || future.isCancelled() || future.isDone()) {
                callee = queue.poll();
                if(callee != null) {
                    future = executor.submit(callee);
                    return future;
                }
            }
        }
        return future;
    }

    public <T> Future<?> addToQueue(Callable<T> run, String name) {
        queue.add(new NamedCallable<T>(run, name));
        return execute();
    }

    private void checkToInterrupt() {
        System.out.println(queue.peek().getName());
        if(callee != null
                && callee.getName() != null
                && !queue.isEmpty()
                && queue.peek().getName().contains("write")
                && callee.getName().contains("read")
                && future != null
                && !future.isDone()) {
            future.cancel(true);
            System.out.println("Cancelling... " + callee.getName());
            future = null;
        }
    }

    private class NamedCallable<T> implements Callable<T> {
        private Callable<T> callee;
        private String name;

        public NamedCallable(Callable<T> callee, String name) {
            this.callee = callee;
            this.name = name;
        }

        @Override
        public T call() throws Exception {
            return callee.call();
        }
    
        private String getName() {
            return name;
        }
        
    }

    /*
    private class LockThread extends Thread {
        private Thread thread;
        private Lock lock;
        public LockThread(Thread thread, Lock lock) {
            this.thread = thread;
            this.lock = lock;
            this.setName(thread.getName());
        }
        public void run() {
            lock.lock();
            thread.run();
            System.out.println("Unlocked");
            lock.unlock();
        }
    }
    */

}
