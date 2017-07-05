package dong.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 读取客户端的输入并将这个输入原封不动返回给客户端
 * Created by DONGSHILEI on 2017/6/30.
 */
public class MultiThreadEchoServer {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    //定义一个线程，封装了客户端Socket,用于读取连接到服务器的Socket的内容并将其进行返回
    static class HandleMsg implements Runnable {
        Socket clientSocket;

        public HandleMsg(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;
            try {
                is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                os = new PrintWriter(clientSocket.getOutputStream(), true);
                //从InputStream当中读取客户端发送的数据
                String inputLine = null;
                long b = System.currentTimeMillis();
                while ((inputLine = is.readLine()) != null) {
                    os.println(inputLine);
                }
                long e = System.currentTimeMillis();
                System.out.println("spend:" + (e - b) + " ms");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) is.close();
                    if (os != null) os.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        ServerSocket echoSocket = null;
        Socket clientSocket = null;
        try {
            //定义服务器端口为8000
            echoSocket = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //一直等待新客户端连接，
        while (true) {
            try {
                //获取连接来的客户端
                clientSocket = echoSocket.accept();
                System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
                //创建HandleMsg线程进行处理
                threadPool.execute(new HandleMsg(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
