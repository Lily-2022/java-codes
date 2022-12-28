package demo.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo {

    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("");//写数据到文件
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.wrap("hello java".getBytes());
        fileChannel.write(buffer);
        fileChannel.close();
        fileOutputStream.close();

        FileInputStream inputStream = new FileInputStream(""); //读数据，in/out 都是针对内存而言，从内存输出数据就是out.
        FileChannel channel = inputStream.getChannel();
    }
}
