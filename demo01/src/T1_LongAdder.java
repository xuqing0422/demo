import java.util.concurrent.atomic.LongAdder;

/**
 * LongAdder也可以实现线程安全，效率比synchronized和AtomicInteger都要高，效果跟他们一样;
 * LongAdder在线程数非常多，每个线程运行任务耗时也较长的情况下，优势比较大，因为它使用了分段锁。
 * 此小程序逻辑跟T1一样
 * @author xq
 * @date 2020.05.17
 */
public class T1_LongAdder {

    static LongAdder count = new LongAdder();

    public void m(){

    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("count=" + count);
        Thread[] threads = new Thread[1000]; // 准备创建1000个线程放入数组
        for (int i = 0; i < threads.length; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 100000; i++) {
                        count.increment();
                    }
                }
            });
            threads[i] = t;
//            t.start();
        }
        long start = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        System.out.println("LongAdder:" + count + " time:" + (System.currentTimeMillis() - start));

    }
}
