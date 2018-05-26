package inherit;

public class Test3 extends Class2 implements Interface3
{
    @Override
    public void run()
    {
        System.out.println("run");
    }

    public static void main(String[] args)
    {
        Interface3 interface3 = new Test3();
        interface3.run();
    }
}

abstract class Class2
{
    public abstract void run();
}

interface Interface3
{
    void run();
}
