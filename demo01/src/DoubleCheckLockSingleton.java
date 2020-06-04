/**
 * 线程安全的 双重检测 单例模式 xq 2020.05.17
 */
public class DoubleCheckLockSingleton {

    /*需要volatile关键字的原因是，在并发情况下，如果没有volatile关键字，在第25行会出现问题，创建对象也会遇到指令重排序的。Java 里只有 volatile 变量是能实现禁止指令重排的
        instance = new DoubleCheckLockSingleton();可以分解为3行伪代码
        a.memory = allocate() //分配内存
        b. ctorInstanc(memory) //初始化对象
        c. instance = memory //设置instance指向刚分配的地址
        上面的代码在编译运行时，可能会出现重排序从a-b-c排序为a-c-b，这就是JAVA创建对象时可能遇到的对象半初始化。在多线程的情况下会出现以下问题：
        当线程A在执行第25行代码时，B线程进来执行到第22行代码。假设此时A执行的过程中发生了指令重排序，即先执行了a和c，没有执行b。
        那么由于A线程执行了c导致instance指向了一段地址，所以B线程判断instance不为null，会直接跳到第29行并返回一个未初始化的对象。*/
    private volatile static DoubleCheckLockSingleton instance;

    // 私有构造方法
    private DoubleCheckLockSingleton(){

    }

    public static DoubleCheckLockSingleton getInstance(){
        if (instance == null){
            synchronized (DoubleCheckLockSingleton.class){
                if (instance == null){
                    instance = new DoubleCheckLockSingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(DoubleCheckLockSingleton.getInstance().hashCode());
                }
            },"线程"+i);
            t.start();
        }

    }
}
