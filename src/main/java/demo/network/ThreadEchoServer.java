package demo.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ThreadEchoServer {

    public static void main(String[] args) {
        try (var s = new ServerSocket(8189)) {
            int i = 1;
            while (true) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                var t = new Thread(r);
                t.start();
                i ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     static class ThreadedEchoHandler implements Runnable {

        private Socket incoming;
        public ThreadedEchoHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try (InputStream inputStream = incoming.getInputStream();
                 OutputStream outputStream = incoming.getOutputStream();
                var in = new Scanner(inputStream, StandardCharsets.UTF_8);
                var out = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true)) {
                out.println("Hello !");

                var done = false;
                while (!done && in.hasNextLine()) {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if (line.trim().equals("a")) {
                        done = true;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
