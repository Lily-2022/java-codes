package demo.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientDemo {
    public static void main(String[] args) throws IOException {

        for (int i = 0; i < 2; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("localhost", 9000);
                        char[] buf = new char[1024 * 1024];

                        while (true) {
                            OutputStream outputStream = socket.getOutputStream();
                            outputStream.write("hello".getBytes());
                            InputStream inputStream = socket.getInputStream();
                            InputStreamReader reader = new InputStreamReader(inputStream);
                            int len = reader.read(buf);

                            try {
                                if (len != -1) {
                                    String data = new String(buf, 0, len);
                                    System.out.println(" [" + Thread.currentThread().getName() + "] receive response: " + data);
                                    //len = reader.read(buf);
                                }
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                reader.close();
                                inputStream.close();
                                outputStream.close();
                                socket.close();
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }
    }
}
