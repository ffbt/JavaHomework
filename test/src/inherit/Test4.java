package inherit;

public class Test4 implements Interface4, Interface5
{
    public static void main(String[] args)
    {
    }

    @Override
    public void run()
    {
        System.out.println("run");
    }

    @Override
    public int run(int a)
    {
        System.out.println("run(a)");
        return a;
    }
}

interface Interface4
{
    void run();
}

interface Interface5
{
    int run(int a);
}
