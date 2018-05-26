package producer_consumer.demo;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDemo3
{
    private static final int MAX_SLOT = 2;
    private LinkedList<Goods> queue = new LinkedList<>();
    private Lock lock = new ReentrantLock();
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();
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
                    while (queue.isEmpty())
                    {
                        System.out.println("queue is empty");
                        empty.await();
                    }
                    Thread.sleep(rnd.nextInt(200));
                    Goods goods = queue.remove();
                    System.out.println(goods);
                    Thread.sleep(rnd.nextInt(200));
                    full.signal();
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
                    Thread.sleep(rnd.nextInt(200));
                }
                catch (InterruptedException e)
                {
//                    e.printStackTrace();
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
                lock.lock();
                try
                {
                    while (queue.size() == MAX_SLOT)
                    {
                        System.out.println("queue is full");
                        full.await();
                    }
                    Thread.sleep(rnd.nextInt(200));
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
                    Thread.sleep(rnd.nextInt(200));
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
                    Thread.sleep(rnd.nextInt(100));
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args)
    {
        ProducerConsumerDemo3 pcd = new ProducerConsumerDemo3();
        Runnable c = pcd.new ComsumeThread();
        Runnable p = pcd.new ProductThread();

        new Thread(c).start();
        new Thread(c).start();
        new Thread(c).start();

        new Thread(p).start();
        new Thread(p).start();
        new Thread(p).start();
    }
}
