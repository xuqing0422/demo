import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class T1_ReentrantLock {

    public static void main(String[] args) throws InterruptedException {
        final Lock lock = new ReentrantLock();
        lock.lock();
        Thread.sleep(1000);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
//                lock.lock();

                try {
                    // 如果没有获得锁，阻塞休眠等待，但是能响应中断，一旦被中断会立即响应并进入catch块，不会走下面的输出代码
                    // 阻塞休眠等待直到 获取到锁或者有中断事件
                    lock.lockInterruptibly();
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                } catch (InterruptedException e) {
                    // do something ...
                    e.printStackTrace();
                }

            }
        });
        t1.start();
        Thread.sleep(1000);
        t1.interrupt();
//        lock.unlock();
        Thread.sleep(1000);
        System.out.println("main continue!");
    }


    /*public static void main(String[] args) {
        Thread t2 = new Thread()
    }*/
}
