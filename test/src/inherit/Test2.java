package inherit;

public class Test2 extends Class1 implements Interface
{
    public static void main(String[] args)
    {
        Interface inter = new Test2();
        inter.run();
    }
}

class Class1
{
    public void run()
    {
        System.out.println("run");
    }
}

interface Interface
{
    void run();
}
