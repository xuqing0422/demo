/**
 * 线程被打上中断标记并捕获中断异常后，不会释放已经获取的锁，直到线程任务执行完毕才释放锁！
 * 所以可以推断出，处在死锁状态的线程，也无法被中断！
 */
public class T02 {

    public synchronized void fun()  {
        System.out.println(Thread.currentThread().getName() + "已拿到锁，进入了fun方法...");
        System.out.println(Thread.currentThread().isInterrupted());
        try {
            Thread.sleep(10000); // 此处执行sleep方法时才会捕获到中断异常
            System.out.println(Thread.currentThread().getName() + " 如果在睡眠时被打上中断标记，那么这句代码不走，直接进catch块执行");
        } catch (InterruptedException e) {
            System.out.println("中断了 ...");
            // 中断后不会释放已经获取的锁，下面这个无限循环会一直使t1线程运行下去，t2一直拿不到锁
            // 除非catch异常后代码没有死循环，正常执行完毕当前的任务，才能释放锁。

        }
        while (true){
            // 没有执行完任务，不会释放锁给t2线程
        }
    }

    public static void main(String[] args) throws InterruptedException {
        T02 t02 = new T02();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                t02.fun();
            }
        },"t1");

        t1.start();
        Thread.sleep(1000);
        t1.interrupt();

        Thread.sleep(1000);

        Thread t2 = new Thread(() -> t02.fun(),"t2");

        t2.start();
    }
}
