package akwei.app;

public class Demo {

    private static Singleton singleton = null;

    public static Singleton getSingleton() {
        if (singleton == null) {
            singleton = createSingleton();
        }
        return singleton;
    }

    private static Singleton createSingleton() {
        System.out.println("begin create");
        return new Singleton();
    }

    public static void test0(Singleton singleton) {
        singleton = new Singleton();
        singleton.setName("akweiwei2");
        System.out.println(singleton);
    }

    public static void main(String[] args) {
        Singleton singleton = new Singleton();
        singleton.setName("akwei");
        System.out.println(singleton);
        Demo.test0(singleton);
        System.out.println(singleton);
    }

}
