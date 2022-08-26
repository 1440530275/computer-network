package com.haolong.chapter2.WebProxyServer.tunnel.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

public class TunnelSession implements Callable<HandlerState> {

    private SocketChannel channel;
    private SocketChannel remoteChannel;
    private Selector selector;
    private final ByteBuffer buffer = ByteBuffer.allocate(1000000);
    private HandlerState state = HandlerState.READING;

    private static final String CONNECT_OK = "HTTP/1.1 200 OK\n\n";

    public TunnelSession(){}

    public TunnelSession(SocketChannel channel, SocketChannel remoteChannel, Selector selector){
        this.selector = selector;
        this.channel = channel;
        this.remoteChannel = remoteChannel;
        buffer.put(CONNECT_OK.getBytes());
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public SocketChannel getRemoteChannel() {
        return remoteChannel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public void setRemoteChannel(SocketChannel remoteChannel) {
        this.remoteChannel = remoteChannel;
    }

    public HandlerState getState() {
        return state;
    }

    public void setState(HandlerState state) {
        this.state = state;
    }

    public void closeSession() throws IOException {
        if(channel != null && channel.isConnected()){
            channel.close();
        }
        if(remoteChannel != null && remoteChannel.isConnected()){
            remoteChannel.close();
        }
    }

    @Override
    public HandlerState call()
            throws Exception {
        try {
            switch (this.state) {
                case READING:
                    read();
                    break;
                case WRITING:
                    write();
                    break;
                default:
                    throw new IllegalStateException("ReadWriteHandler should never be in state " + this.state);
            }
        } catch (CancelledKeyException e) {
            System.out.println("Encountered canceled key while " + this.state + e);
        } catch (IOException ioe) {
            closeSession();
            throw new IOException("Could not read/write between", ioe);
        } catch (Exception e) {
            System.out.println("Unexpected exception" + e);
            try {
                closeSession();
            } finally {
                throw e;
            }
        }
        return this.state;
    }

    private void write()
            throws IOException {
        SelectionKey proxyKey = this.channel.keyFor(this.selector);
        SelectionKey clientKey = this.remoteChannel.keyFor(this.selector);

        SocketChannel writeChannel = null;
        SocketChannel readChannel = null;
        SelectionKey writeKey = null;

        if (this.selector.selectedKeys().contains(proxyKey) && proxyKey.isValid() && proxyKey.isWritable()) {
            writeChannel = this.channel;
            readChannel = this.remoteChannel;
            writeKey = proxyKey;
        } else if (this.selector.selectedKeys().contains(clientKey) && clientKey.isValid() && clientKey.isWritable()) {
            writeChannel = this.remoteChannel;
            readChannel = this.channel;
            writeKey = clientKey;
        }

        if (writeKey != null) {
            int lastWrite, totalWrite = 0;

            this.buffer.flip();

            int available = this.buffer.remaining();

            while ((lastWrite = writeChannel.write(this.buffer)) > 0) {
                totalWrite += lastWrite;
            }

            System.out.println((writeChannel == this.channel ? "proxy" : "client") + " bytes written to " + totalWrite);

            if (totalWrite == available) {
                this.buffer.clear();
                if(readChannel.isOpen()) {
                    readChannel.register(this.selector, SelectionKey.OP_READ, this);
                    writeChannel.register(this.selector, SelectionKey.OP_READ, this);
                }
                else{
                    writeChannel.close();
                }
                this.state = HandlerState.READING;
            } else {
                this.buffer.compact();
            }
            if (lastWrite == -1) {
                closeSession();
            }
        }
    }

    private void read()
            throws IOException {
        SelectionKey proxyKey = this.channel.keyFor(this.selector);
        SelectionKey clientKey = this.remoteChannel.keyFor(this.selector);

        SocketChannel readChannel = null;
        SocketChannel writeChannel = null;
        SelectionKey readKey = null;

        if (this.selector.selectedKeys().contains(proxyKey) && proxyKey.isReadable()) {
            readChannel = this.channel;
            writeChannel = this.remoteChannel;
            readKey = proxyKey;
        } else if (this.selector.selectedKeys().contains(clientKey) && clientKey.isReadable()) {
            readChannel = this.remoteChannel;
            writeChannel = this.channel;
            readKey = clientKey;
        }

        if (readKey != null) {

            int lastRead, totalRead = 0;

            while ((lastRead = readChannel.read(this.buffer)) > 0) {
                totalRead += lastRead;
            }

            System.out.println((readChannel == this.channel ? "proxy" : "client") + " bytes read from " + totalRead);

            if (totalRead > 0) {
                readKey.cancel();
                writeChannel.register(this.selector, SelectionKey.OP_WRITE, this);
                this.state = HandlerState.WRITING;
            }
            if (lastRead == -1) {
                readChannel.close();
            }
        }
    }
}
