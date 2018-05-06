package callable_future;

import java.util.Random;
import java.util.concurrent.*;

public class CallableAndFuture
{
    public static void main(String[] args)
    {
        Callable<Integer> callable = () -> new Random().nextInt(100);
        /*FutureTask<Integer> futureTask = new FutureTask<>(callable);
        new Thread(futureTask).start();*/

        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future<Integer> future = threadPool.submit(callable);

        try
        {
            Thread.sleep(5000);
//            System.out.println(futureTask.get());
            System.out.println(future.get());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
        }
        threadPool.shutdown();
    }
}