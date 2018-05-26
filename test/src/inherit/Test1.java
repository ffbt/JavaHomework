package inherit;

public class Test1 implements Interface1, Interface2
{
    @Override
    public void run()
    {
        System.out.println("run");
    }

    public static void main(String[] args)
    {
        Interface1 interface1 = new Test1();
        interface1.run();
        Interface2 interface2 = new Test1();
        interface2.run();
    }
}

interface Interface1
{
    void run();
}

interface Interface2
{
    void run();
}
