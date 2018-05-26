package producer_consumer.demo;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class ProducerConsumerDemo6
{
    private static final int TOTAL_NUM = 20;
    private static final int MAX_SLOT = 5;
    private volatile AtomicInteger productNum = new AtomicInteger(0);
    private volatile AtomicInteger consumedNum = new AtomicInteger(0);
    private LinkedBlockingQueue<Goods> queue = new LinkedBlockingQueue<>(MAX_SLOT);
    private Random rnd = new Random();
    private volatile AtomicInteger pn = new AtomicInteger(1);

    public class ConsumerThread implements Runnable
    {
        @Override
        public void run()
        {
            while (consumedNum.getAndIncrement() < TOTAL_NUM)
            {
                try
                {
                    Thread.sleep(rnd.nextInt(500));
                    Goods goods = queue.take();
                    Thread.sleep(rnd.nextInt(500));
                    System.out.println(goods);
                    Thread.sleep(rnd.nextInt(500));
                }
                catch (InterruptedException e)
                {
//                    e.printStackTrace();
                }
            }
            System.out.println("customer " + Thread.currentThread().getName() + " is over");
        }
    }

    public class ProducerThread implements Runnable
    {
        @Override
        public void run()
        {
            while (productNum.getAndIncrement() < TOTAL_NUM)
            {
                try
                {
                    int x = pn.getAndIncrement();
                    Goods goods = null;
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
                    Thread.sleep(rnd.nextInt(500));
                    queue.put(goods);
                    Thread.sleep(rnd.nextInt(500));
                }
                catch (InterruptedException e)
                {

                }
            }
            System.out.println("customer " + Thread.currentThread().getName() + " is over");
        }
    }

    public static void main(String[] args)
    {
        ProducerConsumerDemo6 pcd = new ProducerConsumerDemo6();

        Runnable c = pcd.new ConsumerThread();
        Runnable p = pcd.new ProducerThread();

        ExecutorService es = Executors.newCachedThreadPool();
        es.execute(c);
        es.execute(c);
        es.execute(c);
        es.execute(p);
        es.execute(p);
        es.execute(p);
        es.shutdown();
        System.out.println("main Thread is over");
    }
}
