package dong.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * Created by DONGSHILEI on 2017/7/4.
 */
public class NioClient {
    private Selector selector;
    public void init(InetAddress ip, int port) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        this.selector = SelectorProvider.provider().openSelector();
        channel.connect(new InetSocketAddress(ip,port));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void working() throws IOException {
        while (true){
            if (!selector.isOpen())
                break;
            selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                iterator.remove();
                if (key.isConnectable()){
                    connet(key);
                } else if (key.isReadable()){
                    read(key);
                }
            }
        }
    }

    public void connet(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //如果正在连接，则完成连接
        if (channel.isConnectionPending()){
            channel.finishConnect();
        }
        channel.configureBlocking(false);
        channel.write(ByteBuffer.wrap(new String("hello server!\r\n").getBytes()));
        channel.register(selector,SelectionKey.OP_READ);
    }

    public void read(SelectionKey sk) throws IOException {
        SocketChannel channel = (SocketChannel) sk.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byte[] array = byteBuffer.array();
        String msg = new String(array).trim();
        System.out.println(" from server: "+msg);
        channel.close();
        sk.selector().close();
    }

    public static void main(String[] args) throws IOException {
        NioClient nioClient = new NioClient();
        nioClient.init(InetAddress.getLocalHost(),8000);
        nioClient.working();
    }
}
