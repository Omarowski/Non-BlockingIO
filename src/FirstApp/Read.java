package FirstApp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Read {

    public void Read() throws IOException, InterruptedException {

        String fileName = "test.txt";
        RandomAccessFile file = new RandomAccessFile(fileName, "rw");

        // getting a channel
        FileChannel channel = file.getChannel();

        MappedByteBuffer buf = channel.map(
                FileChannel.MapMode.READ_WRITE,  // read-write mode
                0,  // starting from the beginning of the file
                6 * Write.data.length   // map the whole file
        );

        IntBuffer intBuf = buf.asIntBuffer();
        while (true) {
            if (intBuf.get(0) == 1) {


                int sum = 0;
                int num = 0;
                for (int i = 1; i < intBuf.capacity(); i++) {
                    num = intBuf.get(i);
                    Thread.sleep(500);
                    sum += num;

                }

                System.out.print("The sum of the two number is => " + sum + "\n");


                intBuf.put(0, 0);
            } else {
                Thread.sleep(500);
            }
            channel.close();
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Read read = new Read();
        read.Read();
    }
}
