package evan.com;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.local.LocalAddress;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.net.ssl.HostnameVerifier;
import javax.xml.stream.util.StreamReaderDelegate;

public final class LocalEchoClient {

    static final int PORT = 10086;

    public static void main(String[] args) throws Exception {
        // Address to bind on / connect to.
        //final LocalAddress addr = new LocalAddress(PORT);
    	final InetAddress HOST = InetAddress.getByName("192.168.220.128");
    	//final InetSocketAddress addr = InetSocketAddress.createUnresolved("192.168.220.128", Integer.parseInt(PORT));
        NioEventLoopGroup clientGroup = new NioEventLoopGroup(); // NIO event loops are also OK
        
        final SslContext sslCtx = null;
        
        try {

        	 Bootstrap b = new Bootstrap();
             b.group(clientGroup)
              .channel(NioSocketChannel.class)
              .option(ChannelOption.TCP_NODELAY, true)
              .handler(new ChannelInitializer<SocketChannel>() {
                  @Override
                  public void initChannel(SocketChannel ch) throws Exception {
                      ChannelPipeline p = ch.pipeline();
                      //p.addLast(new LoggingHandler(LogLevel.INFO));
                      p.addLast(new LocalEchoClientHandler());
                  }
              });

             // Start the client.
             ChannelFuture f = b.connect(HOST,PORT).sync();
             
             ByteBuf subsequentMessage = null;
             byte[] bytes = null;
             Channel ch = b.connect(HOST,PORT).channel();
             String line = null;
             System.out.println("Feel free to chat here");
			while (true) {
				line = new BufferedReader(new InputStreamReader(System.in)).readLine();
				if (line.equalsIgnoreCase("quit") || !ch.isActive())
				{
					System.out.println("Prepare to close the connection.");
					break;
				}
				subsequentMessage = Unpooled.buffer();
				bytes = line.getBytes();
				subsequentMessage.writeBytes(bytes);

				ch.writeAndFlush(subsequentMessage);

			}
		

             // Wait until the connection is closed.
             f.channel().closeFuture().sync();
            
            
        } finally {
            clientGroup.shutdownGracefully();
        	System.out.println("Client closed");
        }
    }
}