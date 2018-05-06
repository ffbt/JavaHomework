package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class UseScatterGather
{
    static private final int firstHeaderLength = 2;
    static private final int secondHeaderLength = 4;
    static private final int bodyLength = 6;

    public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.out.println("Usage: java UseScatterGather port");
            System.exit(1);
        }
        int port = Integer.parseInt(args[0]);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(port);
        ssc.socket().bind(address);
        int messageLength = firstHeaderLength + secondHeaderLength + bodyLength;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(firstHeaderLength);
        buffers[1] = ByteBuffer.allocate(secondHeaderLength);
        buffers[2] = ByteBuffer.allocate(bodyLength);
        SocketChannel sc = ssc.accept();
        while (true)
        {
            int bytesRead = 0;
            while (bytesRead < messageLength)
            {
                long r = sc.read(buffers);
                bytesRead += r;
                System.out.println("r " + r);
                for (int i = 0; i < buffers.length; i++)
                {
                    ByteBuffer buffer = buffers[i];
                    System.out.println("b " + i + " " + buffer.position() + " " + buffer.limit());
                }
            }
            for (int i = 0; i < buffers.length; i++)
            {
                ByteBuffer buffer = buffers[i];
                buffer.flip();
            }
            long byteWritten = 0;
            while (byteWritten < messageLength)
            {
                long r = sc.write(buffers);
                byteWritten += r;
            }
            for (int i = 0; i < buffers.length; i++)
            {
                ByteBuffer buffer = buffers[i];
                buffer.clear();
            }
            System.out.println(bytesRead + " " + byteWritten + " " + messageLength);
        }
    }
}
