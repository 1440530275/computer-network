package com.haolong.chapter2.WebProxyServer.tunnel.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

public class TunnelProxy {

    private static final String CONNECT = "CONNECT";

    private static final String CONNECT_OK = "HTTP/1.1 200 Connection Established\n";

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private SelectionKey listenerKey;

    private Thread dispatcherThread;

    private volatile Boolean finished;

//    private Set<TunnelSession> sessionSet;

    //会话概念，如果会话存在，那么表示后续直接转发流量
    //如果不存在，解析是否是connect连接, 否则直接转发

    static Map<SocketAddress, TunnelSession> sessionMap;

    static {
        sessionMap = new HashMap<>(16);
    }

    class Dispatcher implements Runnable {
        @Override
        public void run() {
            //进行处理数据
            while (!finished) {
                try {
                    selector.select();
                    Set<SelectionKey> selected = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selected.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (key.equals(listenerKey)) {
                            SocketChannel chan = serverSocketChannel.accept();
                            if (chan != null) {
                                chan.socket().setTcpNoDelay(true);
                                chan.configureBlocking(false);

                                //进行读取数据操作，判定是否是connect连接
                                String readLine = readLine(chan);
                                if (!readLine.contains(CONNECT)) {
                                    continue; //拒绝连接
                                }
                                //拆分
                                String[] slice = readLine.split(" ");
                                String[] address = slice[1].split(":");
                                //解析数据是否是connect连接, 如果是http connect 连接, 那么就直接进行开启会话操作
                                SocketChannel remoteChannel = SocketChannel.open();
                                remoteChannel.socket().setTcpNoDelay(true);
                                String hostname = address[0];
                                int port = Integer.parseInt(address[1]);
                                boolean connect = remoteChannel.connect(new InetSocketAddress(hostname, port)); //连接远程端口
                                if (!connect) {
                                    System.out.println("远程连接无法响应, 进行关闭处理...");
                                    continue;
                                }
                                remoteChannel.configureBlocking(false);
                                TunnelSession tunnelSession = new TunnelSession(chan, remoteChannel, selector);
                                tunnelSession.setState(HandlerState.WRITING);
                                SelectionKey proxyKey = chan.register(selector, SelectionKey.OP_WRITE);
                                proxyKey.attach(tunnelSession);
                                System.out.println("远程连接建立完毕!");
                            }
                        } else {
                            dispatch(key);
                        }
                        iter.remove();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Callable<?> attachment = (Callable<?>) selectionKey.attachment();
        try {
            attachment.call();
        } catch (Exception e) {
            System.out.println("exception handling event on " + e);
        }
    }


    public void tunnel(int port) throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port), 0);
        selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        listenerKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务已经启动, 端口为:" + serverSocket.getLocalPort());
        Dispatcher dispatcher = new Dispatcher();
        dispatcherThread = new Thread(dispatcher);
    }

    public void start(int port) throws IOException {
        if (dispatcherThread == null) {
            tunnel(port);
        }
        finished = false;
//        sessionSet = new HashSet<>();
        dispatcherThread.start();
    }

    /**
     * 将数据全部读取完毕，但是只返回第一行数据
     *
     * 需要进行拆包，草，这也太烦了吧
     */
    public String readLine(SocketChannel chan) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        int index;
        StringBuilder line = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        boolean end = false;
        while ((index = chan.read(byteBuffer)) > 0) {
            byte[] bytes = new byte[index];
            byte b;
            for (int i = 0; i < index; i++) {
                if((b = byteBuffer.get(i)) == 0 || b == '\n' && !end){
                    line.append(new String(bytes, 0, i));
                    end = true;
                }
                bytes[i] = b;
            }
            if(!end){
                line.append(new String(bytes));
            }
            sb.append(new String(bytes));
            byteBuffer.clear();
        }
        System.out.println("查看数据connect:" + sb.toString());
        //查看是否读取完毕
        return line.toString();
    }

    /**
     * 将数据由A流向B
     * @return 返回结果标识是否流需要进行关闭操作, 如果需要进行关闭, 那么返回true, 否则返回false。
     */
    public Boolean transferAtoB(SocketChannel channelA, SocketChannel channelB) throws IOException {
        System.out.println("从" + channelA.getRemoteAddress() + "--->" + channelB.getRemoteAddress());
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read;
        while((read = channelA.read(byteBuffer)) > 0){
            System.out.println("转发数据中...");
            byte[] bytes = new byte[byteBuffer.position()];
            for(int j = 0; j < byteBuffer.position(); j++){
                bytes[j] = byteBuffer.get(j);
            }
            channelB.write(ByteBuffer.wrap(bytes));
            byteBuffer.clear();
        }
        System.out.println("转发结束...");
        return read == -1;
    }

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        TunnelProxy tunnelProxy = new TunnelProxy();
        tunnelProxy.start(8888);
    }
}
