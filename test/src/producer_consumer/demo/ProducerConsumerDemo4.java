package producer_consumer.demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerDemo4
{
    private static final int MAX_SLOT = 3;
    private LinkedBlockingQueue<Goods> queue = new LinkedBlockingQueue<>(MAX_SLOT);
    private AtomicInteger id = new AtomicInteger(1);
    private Random rnd = new Random();

    public class ConsumeThread implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    Thread.sleep(rnd.nextInt(200));
                    Goods goods = queue.take();
                    System.out.println(goods);
                    Thread.sleep(rnd.nextInt(200));
                }
                catch (InterruptedException e)
                {

                }
            }
        }
    }

    public class ProductThread implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    int x = id.getAndIncrement();
                    Goods goods = null;
                    Thread.sleep(rnd.nextInt(200));
                    switch (x % 3)
                    {
                        case 0:
                            goods = new Goods("A", 1, x);
                            break;
                        case 1:
                            goods = new Goods("B", 2, x);
                            break;
                        case 2:
                            goods = new Goods("C", 3, x);
                            break;
                    }
                    Thread.sleep(rnd.nextInt(200));
                    queue.put(goods);
                    Thread.sleep(rnd.nextInt(100));
                }
                catch (InterruptedException e)
                {

                }
            }
        }
    }

    public static void main(String[] args)
    {
        ProducerConsumerDemo4 pcd = new ProducerConsumerDemo4();

        Runnable c = pcd.new ConsumeThread();
        Runnable p = pcd.new ProductThread();

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(p);
        es.execute(p);
        es.execute(p);
        es.execute(c);
        es.execute(c);
        es.execute(c);
        es.shutdown();
    }
}
