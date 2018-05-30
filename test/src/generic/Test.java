package generic;

import exam.Leaf;

public class Test
{
    public static void main(String[] args)
    {
//        Gen<F> gf = new Gen<F>();
//        Gen<B> gb = new Gen<B>();
        Gen<C> gc = new Gen<>();
//        gc.set(new B());
        Gen<D> gd = new Gen<>();
        gd.set(new D());
        D d = gd.get();
        gd.set(new E());
        D d1 = gd.get();
//        E e = gd.get();

        System.out.println(gc instanceof Gen<?>);

        Gen1<? extends B> gec = new Gen1<D>();
//        gec.set(new A());
//        gec.set(new B());
//        gec.set(new C());
//        gec.set(new D());
//        gec.set(new E());
        B b = gec.get();

        Gen1<? super D> supd = new Gen1<B>();
//        supd.set(new A());
//        A a = supd.get();
//        supd.set(new C());
//        C c = supd.get();
        supd.set(new D());
//        D d2 = supd.get();
        d = (D)supd.get();
        supd.set(new E());
        Object o = supd.get();
    }
}

class A
{
}

class B extends A
{
}

class C extends B
{
}

class D extends C
{
}

class E extends D
{
}

class F
{
}

class Gen<T extends C>
{
    private T t;

    public T get()
    {
        return t;
    }

    public void set(T t)
    {
        this.t = t;
    }
}

class Gen1<T>
{
    private T t;

    public T get()
    {
        return t;
    }

    public void set(T t)
    {
        this.t = t;
    }

    /*public void set(Object t) // compile error
    {

    }*/

    public static <T> T makeTobj(Class<T> c)
    {
        try
        {
            return c.newInstance();
        }
        catch (InstantiationException e)
        {
            e.printStackTrace();
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}

class GenFun
{
    public <T> T mid(T[] a)
    {
        return a[a.length / 2];
    }
}

class Pair<K, V>
{
    private K k;
    private V v;
//    ...
}
