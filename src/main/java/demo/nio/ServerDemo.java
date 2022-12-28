package demo.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            Socket socket = serverSocket.accept();//会阻塞，一直等待别人跟他建立连接。
            new Worker(socket).start();
        }
        //serverSocket.close();
    }

    static class Worker extends Thread {
        Socket socket;

        Worker(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try {
                char[] buf = new char[1024 * 1024];

                InputStream inputStream = socket.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                OutputStream outputStream = socket.getOutputStream();
                int len = reader.read(buf);

                while (len != -1) {
               // if (len != -1) {
                    String data = new String(buf, 0, len);
                    System.out.println("[" + Thread.currentThread().getName() + "] receive data: " + data);
                    outputStream.write("ok ok".getBytes(StandardCharsets.UTF_8));
                    len = reader.read(buf); //会阻塞等待读取数据
                }

                reader.close();
                inputStream.close();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
