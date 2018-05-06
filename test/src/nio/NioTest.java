package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioTest
{
    public static void main(String[] args) throws Exception
    {
        ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("localhost", 8080));
        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_CONNECT);
        int num = selector.select();
        Set selectedKeys = selector.selectedKeys();
        Iterator iterator = selectedKeys.iterator();
        while (iterator.hasNext())
        {
            SelectionKey key = (SelectionKey) iterator.next();
            iterator.remove();
            if (key.isConnectable())
            {
                if (channel.isConnectionPending())
                {
                    if (channel.finishConnect())
                    {
                        key.interestOps(SelectionKey.OP_READ);
                        echoBuffer.put("123456789abcdefghijklmnopq".getBytes());
                        echoBuffer.flip();
                        System.out.println("##" + new String(echoBuffer.array()));
                        channel.write(echoBuffer);
                        System.out.println("写入完毕");
                    }
                    else
                        key.cancel();
                }
            }
        }
    }
}
