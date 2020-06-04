/**
 * 抛出异常后中断标示会被清除
 */
public class T02_InterruptDemo {

    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
//                    Thread.currentThread().interrupt();
                } catch (InterruptedException e) {
                    // 抛出异常后中断标示会被清除
                    System.out.println("after catch " + Thread.currentThread().isInterrupted());
                    // 重新设置线程的中断标志
                    Thread.currentThread().interrupt();
                    // 子线程自己的中断标志又变为true了
                    System.out.println("after catch and interrupt " + Thread.currentThread().isInterrupted());
                }
            }
        });
        t.start();
        t.interrupt();
        System.out.println("1:" + t.isInterrupted());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 在子线程catch块中重新设置了子线程的中断标志后，主线程调用子线程的是否中断判断居然还是为false ......
        System.out.println("2:" + t.isInterrupted());
    }
}
