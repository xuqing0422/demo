import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 请参考本包中的T1类，代码逻辑一样，只不过这个小程序用了AtomicInteger代替volatile，实现了原子性
 * @author xq
 * @date 2020.05.17
 */
public class T1_AtomicInteger {

    // 原子性的Integer类使用
    AtomicInteger count = new AtomicInteger(0);

    void m(){
        for (int i = 0;i < 10000; i++){

            count.incrementAndGet(); // 类似于count ++ 这种，此处是原子性的，所以不需要加锁也不需要加volatile
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1_AtomicInteger t = new T1_AtomicInteger();

        // 这个集合是为了存放这个小程序启动的所有非main线程，保证后面能够循环各自调用join方法使main线程等他们全部执行完毕再输出count值
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    t.m();
                }
            });

            threads.add(thread);

            thread.start();
        }

        for (Thread thread1 :
                threads) {
            // 主线程暂停，等待里面的线程循环执行完毕才执行第46行的输出count值，如果这里不用集合存放之前启动的子线程，那么无法让main线程等待所有子线程执行完毕输出
            thread1.join();
        }
        System.out.println(t.count);

    }
}
