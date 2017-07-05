package dong.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by DONGSHILEI on 2017/6/30.
 */
public class HeavySocketClient {

    private static ExecutorService threadPool = Executors.newCachedThreadPool();
    private static final int sleepTime = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable {

        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader reader = null;
            try {
                client = new Socket();
                client.connect(new InetSocketAddress(InetAddress.getLocalHost(), 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                //模拟客户端在连接到服务器后，长时间占用服务器的连接线程
                writer.print("H");
                LockSupport.parkNanos(sleepTime);
                writer.print("e");
                LockSupport.parkNanos(sleepTime);
                writer.print("l");
                LockSupport.parkNanos(sleepTime);
                writer.print("l");
                LockSupport.parkNanos(sleepTime);
                writer.print("o");
                LockSupport.parkNanos(sleepTime);
                writer.print("!");
                LockSupport.parkNanos(sleepTime);
                writer.println();
                writer.flush();
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String readLine = reader.readLine();
                System.out.println("from server:" + readLine);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) reader.close();
                    if (writer != null) writer.close();
                    if (client != null) client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        EchoClient client = new EchoClient();
        for (int i = 0; i < 3; i++) {
            threadPool.execute(client);
        }
    }
}
