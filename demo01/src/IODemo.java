import java.io.*;

/**
 * 测试 IO流 读写
 */
public class IODemo {


    public static void main(String[] args) throws UnsupportedEncodingException {

        byte[] bytes = "Hello World!".getBytes();
        FileOutputStream fos = null;
        FileInputStream fis = null;
        // 可以指定字符编码的流
//        InputStreamReader isr = new InputStreamReader(fis,"utf-8");
        // 为了提高效率，将字符串进行缓冲区技术高效操作，使用BufferedReader
//        BufferedReader br = new BufferedReader(isr);
        try {
            File file = new File("C:\\Users\\Admin\\Desktop\\test.txt");
            if (!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            fos.write(bytes);
            byte[] b = new byte[1024];
            fis = new FileInputStream(file);
            int len = fis.read(b);
            String s = new String(b,0,len);
            System.out.println(len);
            System.out.println(s);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null){
                    fis.close();
                }
                if (fos != null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
