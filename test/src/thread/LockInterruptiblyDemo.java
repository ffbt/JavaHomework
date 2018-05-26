package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockInterruptiblyDemo
{
    private Lock lock = new ReentrantLock();

    public static void main(String[] args)
    {
        LockInterruptiblyDemo lockInterruptiblyDemo = new LockInterruptiblyDemo();
        MyThread thread1 = new MyThread(lockInterruptiblyDemo);
        MyThread thread2 = new MyThread(lockInterruptiblyDemo);
        thread1.start();
        try
        {
            Thread.sleep(10);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        thread2.start();
        try
        {
            Thread.sleep(2000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        thread2.interrupt();
    }

    public void insert(Thread thread) throws InterruptedException
    {
        lock.lockInterruptibly();
        try
        {
            System.out.println(thread.getName() + " get lock");
            long startTime = System.currentTimeMillis();
            while (true)
                if (System.currentTimeMillis() - startTime >= 5000)
                    break;
        }
        finally
        {
            System.out.println(Thread.currentThread().getName() + " run finally");
            lock.unlock();
            System.out.println(thread.getName() + " unlock");
        }
    }
}

class MyThread extends Thread
{
    private LockInterruptiblyDemo lockInterruptiblyDeme;

    public MyThread(LockInterruptiblyDemo lockInterruptiblyDeme)
    {
        this.lockInterruptiblyDeme = lockInterruptiblyDeme;
    }

    @Override
    public void run()
    {
        try
        {
            lockInterruptiblyDeme.insert(Thread.currentThread());
        }
        catch (InterruptedException e)
        {
            System.out.println(Thread.currentThread().getName() + " interrupted");
        }
    }
}
