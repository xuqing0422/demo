import java.util.ArrayList;
import java.util.List;

/**
 * 小程序证明volatile无法保证原子性（扩展：volatile可以保证可见性，只有volatile才能阻止JVM指令重排序）
 * @author xq
 * @date 2020.05.17
 */
public class T1 {

    volatile int count;

    void m(){
        for (int i = 0;i < 10000; i++){
            // count++，会拆分为好几步操作，volatile不能保证这样的操作的原子性
            // （就是说volatile不能替代synchronized此处如果使用synchronized可以保证原子性）
            count ++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T1 t = new T1();

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
//        for (Thread thread1 :
//                threads) {
//            thread1.start();
//        }
//
        for (Thread thread1 :
                threads) {
            // 主线程暂停，等待里面的线程循环执行完毕才输出第49行的count值，如果这里不用集合存放之前启动的子线程，那么无法让main线程等待所有子线程执行完毕输出
            thread1.join();
        }
        System.out.println(t.count);

    }

}
