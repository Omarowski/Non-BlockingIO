package FirstApp;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class Write {
    static int[] data = new int[2];
    Random rd = new Random();
    String fileName = "test.txt";//giving file name

    public Write() throws IOException, InterruptedException {


        //iterating through the list
        for (int i = 0; i < data.length; i++) data[i] = rd.nextInt(20);


        //Random access to file with read and write mode
        RandomAccessFile file = new RandomAccessFile(fileName, "rw");
        FileChannel channel = file.getChannel();

        // mapping file
        MappedByteBuffer buf = channel.map(
                FileChannel.MapMode.READ_WRITE,  // read-write mode
                0,  // starting from the beginning of the file
                6 * data.length   // map the whole file
        );

        // getting a view of the buffer
        IntBuffer intBuf = buf.asIntBuffer();
        int maxAttempt = 20;
        //iterating throw row until we reach number of attempts
        while (maxAttempt > 0) {


            if (intBuf.get(0) == 0) {

                for (int i = 1; i < intBuf.capacity(); i++) {
                    int random = rd.nextInt(10);
                    intBuf.put(i, random);
                    System.out.println("getting Elements " + random);
                }
                intBuf.put(0, 1);
                maxAttempt = 20;
            } else {
                maxAttempt--;

                Thread.sleep(2000);
                System.out.println("waiting for the reader ");
            }
            // reflect changes to the file
            buf.force();
        }
        channel.close();


    }

    public static void main(String[] arg) throws IOException, InterruptedException {
        new Write();

    }


}
