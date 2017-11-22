package com.dong.tcp;

import org.json.JSONObject;
import org.json.JSONString;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DONGSHILEI on 2017/11/1
 */
public class TcpClient {
    public static void main(String[] args) {
        byte a[] = {127,0,0,1};
        Socket socket = null;
        try {
            InetAddress address = InetAddress.getByAddress(a);
            socket = new Socket(address, 8888);
            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            Map<String,String> map = new HashMap<>();
            map.put("aa","aaa");
            map.put("bb","bbb");
            map.put("cc","ccc");
            String s = JSONObject.valueToString(map);
            writer.println(s);
            String line = reader.readLine();
            System.out.println(" client get from server:"+line);
            writer.close();
            reader.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
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
        }
    }
}
