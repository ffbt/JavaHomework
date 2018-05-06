package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFile
{
    public static void main(String[] args) throws Exception
    {
        String infile = "d://garen.lol";
        String outfile = "d://garen_cp.lol";
        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);
        // 1. 从FileInputStream获取Channel
        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();
        // 2. 创建Buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true)
        {
            buffer.clear();
            // 3. 将数据从Channel读到Buffer中
            int r = fcin.read(buffer);
            if(r == -1)
                break;
            buffer.flip();
            fcout.write(buffer);
        }
    }
}
