package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class ReadByteBuffer
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("d://233.txt");
        FileInputStream fStream = new FileInputStream(file);
        FileChannel fChannel = fStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        CharBuffer charBuffer = CharBuffer.allocate((int) file.length());
        String encoding = System.getProperty("file.encoding");
        System.out.println(encoding);
        Charset charset = Charset.forName(encoding);
        CharsetDecoder decoder = charset.newDecoder();
        try
        {
            int len = fChannel.read(byteBuffer);
            while (len != -1)
            {
                byteBuffer.flip();
                decoder.decode(byteBuffer, charBuffer, false);
                charBuffer.flip();
                System.out.println(charBuffer);
                byteBuffer.clear();
                charBuffer.clear();
                len = fChannel.read(byteBuffer);
            }
        }
        catch (Exception e)
        {

        }
        finally
        {
            fChannel.close();
            fStream.close();
        }
    }

    // 编码
    private static byte[] getBytes(char[] chars)
    {
        Charset cs = Charset.forName("UTF-8");
        CharBuffer cb = CharBuffer.allocate(chars.length);
        cb.put(chars);
        cb.flip();
        ByteBuffer bb = cs.encode(cb);
        return bb.array();
    }

    // 解码
    private static char[] getChars(byte[] bytes)
    {
        Charset cs = Charset.forName("UTF-8");
        ByteBuffer bb = ByteBuffer.allocate(bytes.length);
        bb.put(bytes);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        return cb.array();
    }
}
