/**
 * <p>验证synchronized锁的是对象，还是代码</p>
 * <li>可见锁的是对象：因为代码相同，而锁不同对象，无法达到多线程同步效果，</li>
 * <li>锁相同对象，才能达到同步。</li>
 */
class Sync {

    // 对于非static的方法，锁的就是对象本身也就是this
    /*public synchronized void test(){
        System.out.println("test 开始...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test 结束...");
    }*/

    // 对于这种写法，方法内加synchronized块，传入this，锁的也是对象本身
    /*public  void test(){
        synchronized (this){
            System.out.println("test 开始...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test 结束...");
        }
    }*/

    // 这种锁Sync.class的就可以达到预期的多线程同步效果，因为Sync.class返回的是类的Class对象，只有一个；或者将test方法改为static锁的也是类的Class对象
    public void test(){
        synchronized (Sync.class){
            System.out.println("test 开始...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test 结束...");
        }
    }

}

class MyThread extends Thread {

    @Override
    public void run() {
        // 如果此处new 对象，都是线程本地内存中的对象，也就是多个线程启动后，synchronized锁的都是各自不同对象
        // 这样相当于没起到同步这个test方法的作用，除非这个sync是单例对象。
        Sync sync = new Sync();
        sync.test();
    }
}



public class WhatIsBeSynchronized {

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            Thread t = new MyThread();
            t.start();
        }
    }

}
