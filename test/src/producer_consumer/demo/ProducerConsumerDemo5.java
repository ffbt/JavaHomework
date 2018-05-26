package producer_consumer.demo;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDemo5
{
    private final static int TOTAL_NUM = 30;
    private final static int MAX_SLOT = 2;
    private volatile int productNum = 0;
    private volatile int comsumedNum = 0;
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();
    private LinkedList<Goods> queue = new LinkedList<>();
    private int id = 1;
    private Random rnd = new Random();

    public class ComsumeThread implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                lock.lock();
                try
                {
                    Thread.sleep(rnd.nextInt(250));
                    if (comsumedNum < TOTAL_NUM)
                        comsumedNum++;
                    else
                        break;
                    while (queue.isEmpty())
                    {
                        System.out.println("queue is empty");
                        empty.await();
                    }
                    Thread.sleep(rnd.nextInt(250));
                    Goods goods = queue.remove();
                    System.out.println(goods);
                    Thread.sleep(250);
                    full.signal();
                }
                catch (InterruptedException e)
                {
//                    e.printStackTrace();
                }
                finally
                {
                    lock.unlock();
                }
                try
                {
                    Thread.sleep(250);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("customer " + Thread.currentThread().getName() + " is over");
        }
    }

    public class ProductThread implements Runnable
    {
        @Override
        public void run()
        {
            while (true)
            {
                lock.lock();
                try
                {
                    Thread.sleep(rnd.nextInt(250));
                    if (productNum < TOTAL_NUM)
                        productNum++;
                    else
                        break;
                    Thread.sleep(rnd.nextInt(250));
                    while (queue.size() == MAX_SLOT)
                    {
                        System.out.println("queeu is full");
                        full.await();
                    }
                    Thread.sleep(rnd.nextInt(250));
                    Goods goods = null;
                    switch (id % 3)
                    {
                        case 0:
                            goods = new Goods("A", 1, id);
                            break;
                        case 1:
                            goods = new Goods("B", 2, id);
                            break;
                        case 2:
                            goods = new Goods("C", 3, id);
                            break;
                    }
                    queue.add(goods);
                    id++;
                    empty.signal();
                }
                catch (InterruptedException e)
                {

                }
                finally
                {
                    lock.unlock();
                }
                try
                {
                    Thread.sleep(rnd.nextInt(250));
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            System.out.println("customer " + Thread.currentThread().getName() + " is over");
        }
    }

    public static void main(String[] args)
    {
        ProducerConsumerDemo5 pcd = new ProducerConsumerDemo5();
        Runnable c = pcd.new ComsumeThread();
        Runnable p = pcd.new ProductThread();

        new Thread(c).start();
        new Thread(c).start();
        new Thread(c).start();
        new Thread(p).start();
        new Thread(p).start();
        new Thread(p).start();

        System.out.println("main Thread is over");
    }
}
