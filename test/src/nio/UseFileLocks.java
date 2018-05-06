// bug
package nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class UseFileLocks
{
    static private final int start = 10;
    static private final int end = 20;

    public static void main(String[] args) throws Exception
    {
        RandomAccessFile raf = new RandomAccessFile("d://garen.lol", "rw");
        FileChannel fc = raf.getChannel();
//        System.out.println(fc.isOpen());
        Thread[] threads = new Thread[8];
        for (int i = 0; i < threads.length; i++)
            threads[i] = new Thread(() ->
            {
                try
                {
                    // close
                    if (!fc.isOpen())
                        System.out.println(Thread.currentThread().getName());
                    System.out.println(Thread.currentThread().getName() + "trying to get lock");
                    FileLock lock = fc.lock(start, end, false);
                    System.out.println(Thread.currentThread().getName() + "got lock");
                    System.out.println(Thread.currentThread().getName() + "pausing");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName() + "going to release lock");
                    lock.release();
                    System.out.println(Thread.currentThread().getName() + "released lock");
                }
                catch (Exception e)
                {
//                    e.printStackTrace();
                }
            });
        for (int i = 0; i < threads.length; i++)
            threads[i].start();
        raf.close();
    }
}
