package nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MappedByteBuffer01
{
    public static void main(String[] args) throws Exception
    {
        File reader = new File("d://garen.lol");
        FileInputStream in = new FileInputStream(reader);
        FileChannel fin = in.getChannel();
        MappedByteBuffer map = fin.map(FileChannel.MapMode.READ_ONLY, 0, reader.length());
        byte[] data = new byte[(int)reader.length()];
        int foot = 0;
        map.get(data);
        System.out.println(new String(data, "GB2312"));
    }
}
