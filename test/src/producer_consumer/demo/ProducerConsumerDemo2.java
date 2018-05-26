package producer_consumer.demo;

import java.util.Random;

public class ProducerConsumerDemo2
{
    private volatile Goods goods;
    private Object obj = new Object();
    private volatile boolean isFull = false;
    private int id = 1;
    private Random rnd = new Random();

    public class ConsumerThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    synchronized (obj)
                    {
                        while (!isFull)
                            obj.wait();
                        Thread.sleep(rnd.nextInt(250));
                        System.out.println(goods);
                        Thread.sleep(rnd.nextInt(250));
                        isFull = false;
                        obj.notifyAll();
                    }
                    Thread.sleep(rnd.nextInt(250));
                }
            }
            catch (InterruptedException e)
            {
//                e.printStackTrace();
            }
        }
    }

    public class ProductThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                while (true)
                {
                    synchronized (obj)
                    {
                        while (isFull)
                            obj.wait();
                        Thread.sleep(rnd.nextInt(500));
                        if (id % 2 == 0)
                            goods = new Goods("A", 2, id);
                        else
                            goods = new Goods("B", 1, id);
                        Thread.sleep(rnd.nextInt(250));
                        id++;
                        isFull = true;
                        obj.notifyAll();
                    }
                }
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    public static void main(String[] args)
    {
        ProducerConsumerDemo2 pcd = new ProducerConsumerDemo2();

        Runnable c = pcd.new ConsumerThread();
        Runnable p = pcd.new ProductThread();

        new Thread(c).start();
        new Thread(c).start();
        new Thread(c).start();
        new Thread(p).start();
        new Thread(c).start();
        new Thread(c).start();
    }
}
