public class CloneDemo {

    public static void main(String[] args) throws CloneNotSupportedException {
        T t = new T();
        T t2 = (T) t.clone();
        System.out.println(t.hashCode() );
        System.out.println(t2.hashCode());
        System.out.println("github updated");
    }
}


class T implements Cloneable {

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
