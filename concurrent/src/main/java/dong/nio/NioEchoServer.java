package dong.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Created by DONGSHILEI on 2017/7/1.
 */
public class NioEchoServer {
    //用于处理所有的网络连接
    private Selector selector;
    //处理每个客户端，每个请求都会委托给线程池中的线程进行实际处理
    private ExecutorService threadPool = Executors.newCachedThreadPool();
    //统计某一个Socket上花费的时间，time_stat的key为Socket，value为时间戳
    private static Map<Socket, Long> time_stat = new HashMap<>(10240);

    public static void main(String[] args) throws IOException {
        NioEchoServer nioEchoServer = new NioEchoServer();
        nioEchoServer.startServer();
    }

    //启动Nio Server
    private void startServer() throws IOException {
        //通过通常方法获得Selector对象实例
        selector = SelectorProvider.provider().openSelector();
        //获得表示服务端的SocketChannel实例
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //设置为非阻塞
        ssc.configureBlocking(false);

        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        ssc.socket().bind(isa);
        //将ServerSocketChannel绑定到Selector上，这样Selector就能为这个Channel服务了。
        //当Selector发现ServerSocketChannel有新的客户端连接时，就会通知ServerSocketChannel进行处理。
        //register()返回值为SelectionKey，表示一对Selector和Channel的关系，当Selector或者Channel被关闭时，对应的SelectionKey就失效
        SelectionKey acceptKey = ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            //获取准备好的SelectionKey
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            long e = 0;
            while (iterator.hasNext()) {
                SelectionKey sk = iterator.next();
                //当处理完一个SelectionKey，务必将其从集合中删除，否则会重复处理相同的SelectionKey
                iterator.remove();
                //如果当前SelectorKey所代表的Channel状态为acceptable，接收客户端
                if (sk.isAcceptable()) {
                    doAccept(sk);
                } else if (sk.isValid() && sk.isReadable()) {//判断当前Channel是否可读
                    if (!time_stat.containsKey(((SocketChannel) sk.channel()).socket())) {
                        time_stat.put(((SocketChannel) sk.channel()).socket(), System.currentTimeMillis());
                        doRead(sk);
                    }
                } else if (sk.isValid() && sk.isWritable()) {//判断当前Channel是否可写
                    doWrite(sk);
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(((SocketChannel) sk.channel()).socket());
                    System.out.println("speed: " + (e - b) + " ms");
                }
            }
        }

    }


    private void doAccept(SelectionKey sk) {
        ServerSocketChannel server = (ServerSocketChannel) sk.channel();
        SocketChannel clientChannl;

        try {
            clientChannl = server.accept();
            clientChannl.configureBlocking(false);
            //将新生成的Channel注册到selector选择器上，并告诉Selector，我现在对读（OP_READ）感兴趣
            //这样当Selector发现这个Channel已经准备好读时，就能给线程一个通知
            SelectionKey clientKey = clientChannl.register(selector, SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannl.socket().getInetAddress();
            System.out.println(" Accepted connection from " + clientAddress.getHostAddress() + " .");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doRead(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        //准备8K的缓冲区读取数据
        ByteBuffer bb = ByteBuffer.allocate(8192);
        int len;
        try {
            len = channel.read(bb);
            if (len < 0) {
                disconnect(sk);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect(sk);
            return;
        }
        //重置缓冲区
        bb.flip();
        threadPool.execute(new HandleMsg(sk, bb));
    }

    private void doWrite(SelectionKey sk) {
        SocketChannel channel = (SocketChannel) sk.channel();
        EchoClient echoClient = (EchoClient) sk.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutputQueue();
        ByteBuffer bb = outq.getLast();
        try {
            int len = channel.write(bb);
            if (len == -1) {
                disconnect(sk);
                return;
            }
            if (bb.remaining() == 0) {
                outq.removeLast();
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect(sk);
        }
        if (outq.size() == 0) {
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void disconnect(SelectionKey sk) {
        sk.cancel();
    }

    class EchoClient {
        private LinkedList<ByteBuffer> outq;

        public EchoClient() {
            this.outq = new LinkedList<ByteBuffer>();
        }

        public LinkedList<ByteBuffer> getOutputQueue() {
            return outq;
        }

        public void enqueue(ByteBuffer bb) {
            outq.addFirst(bb);
        }
    }

    class HandleMsg implements Runnable {
        SelectionKey sk;
        ByteBuffer bb;

        public HandleMsg(SelectionKey sk, ByteBuffer bb) {
            this.sk = sk;
            this.bb = bb;
        }

        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) sk.attachment();
            echoClient.enqueue(bb);
            sk.interestOps(SelectionKey.OP_WRITE);
            //唤醒selector
            selector.wakeup();
        }
    }
}
