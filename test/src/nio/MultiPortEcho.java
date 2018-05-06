package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MultiPortEcho
{
    private int ports[];
    private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);

    public MultiPortEcho(int[] ports) throws IOException
    {
        this.ports = ports;
        go();
    }

    private void go() throws IOException
    {
        // 异步I/O
        Selector selector = Selector.open();
        // Open a listener on each port, and register each one with the selector
        for (int i = 0; i < ports.length; i++)
        {
            ServerSocketChannel ssc = ServerSocketChannel.open();
            ssc.configureBlocking(false);
            ServerSocket ss = ssc.socket();
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            ss.bind(address);
            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Going to listen on " + ports[i]);
        }
        while (true)
        {
            /*这个方法会阻塞，直到至少有一个已注册的事件发生。当一个或者更多的事件发生时，
            select() 方法将返回所发生的事件的数量。*/
            int num = selector.select();
            Set selectedKeys = selector.selectedKeys();
            Iterator it = selectedKeys.iterator();
            while (it.hasNext())
            {
                SelectionKey key = (SelectionKey) it.next();
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT)
                {
                    // Accept the new connection
                    /*因为我们知道这个服务器套接字上有一个传入连接在等待，所以可以安全地接受它；
                    也就是说，不用担心 accept() 操作会阻塞：*/
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    // Add the new connection to the selector
                    SelectionKey newKey = sc.register(selector, SelectionKey.OP_READ);
                    it.remove();
                    System.out.println("Get connection from " + sc);
                }
                else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ)
                {
                    // Read the data
                    SocketChannel sc = (SocketChannel) key.channel();
                    // Echo data
                    int bytesEchoed = 0;
                    while (true)
                    {
                        echoBuffer.clear();
                        int r = sc.read(echoBuffer);
                        if (r <= 0)
                            break;
                        echoBuffer.flip();
                        sc.write(echoBuffer);
                        bytesEchoed += r;
                    }
                    System.out.println("Echoed " + bytesEchoed + " from " + sc);
                    it.remove();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length <= 0)
        {
            System.err.println("Usage: java MultiportEcho port[port port ...]");
            System.exit(1);
        }
        int[] ports = new int[args.length];
        for (int i = 0; i < args.length; i++)
            ports[i] = Integer.parseInt(args[i]);
        new MultiPortEcho(ports);
    }
}
