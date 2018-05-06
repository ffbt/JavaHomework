package callable_future;

import java.util.concurrent.*;

public class CallableAndFuture1
{
    public static void main(String[] args)
    {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        CompletionService<Integer> cs = new ExecutorCompletionService<>(threadPool);
        for (int i = 0; i < 5; i++)
        {
            final int taskId = i;
            cs.submit(() -> taskId);
        }
        for (int i = 0; i < 5; i++)
        {
            try
            {
                System.out.println(cs.take().get());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
    }
}
