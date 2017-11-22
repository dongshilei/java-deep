package com.dong.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by DONGSHILEI on 2017/11/1
 */
public class TcpServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(9999);
            socket  = serverSocket.accept();
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);
            String line ;
            while ((line=reader.readLine())!=null){
                System.out.println("server get:"+line);
                line = "A line sent by the Server";
                writer.println(line);
            }
            writer.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //System.exit(1);
        }
    }
}
