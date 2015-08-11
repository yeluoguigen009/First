package evan.com;





import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.Channel;

import io.netty.channel.ChannelFuture;

import io.netty.channel.ChannelInitializer;

import io.netty.channel.ChannelOption;

import io.netty.channel.DefaultEventLoopGroup;

import io.netty.channel.EventLoopGroup;

import io.netty.channel.local.LocalAddress;

import io.netty.channel.local.LocalChannel;

import io.netty.channel.local.LocalServerChannel;

import io.netty.channel.nio.NioEventLoopGroup;

import io.netty.channel.socket.SocketChannel;

import io.netty.channel.socket.nio.NioServerSocketChannel;



import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.InetSocketAddress;

import java.util.concurrent.TimeUnit;



public final class LocalEchoServer {



    static final String PORT = System.getProperty("port", "10086");



    public static void main(String[] args) throws Exception {

    	

    /*	EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap(); // (2)

            b.group(bossGroup, workerGroup)

             .channel(NioServerSocketChannel.class) // (3)

             .childHandler(new ChannelInitializer<SocketChannel>() { // (4)

                 @Override

                 public void initChannel(SocketChannel ch) throws Exception {

                     ch.pipeline().addLast(new LocalEchoServerHandler());

                 }

             })

             .option(ChannelOption.SO_BACKLOG, 128)          // (5)

             .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)



            // Bind and start to accept incoming connections.

            ChannelFuture f = b.bind(Integer.parseInt(PORT)).sync(); // (7)



            // Wait until the server socket is closed.

            // In this example, this does not happen, but you can do that to gracefully

            // shut down your server.

            f.channel().closeFuture().sync();

        } finally {

            workerGroup.shutdownGracefully();

            bossGroup.shutdownGracefully();

        } */





    	System.out.println("Server start");



    	

        NioEventLoopGroup serverGroup = new NioEventLoopGroup();

        try {

        	

            ServerBootstrap sb = new ServerBootstrap();

            sb.group(serverGroup)

              .channel(NioServerSocketChannel.class)

              .childHandler(new ChannelInitializer<SocketChannel>() {

                  @Override

                  public void initChannel(SocketChannel ch) throws Exception {

                      ch.pipeline().addLast(

                            //  new LoggingHandler(LogLevel.INFO),

                              new LocalEchoServerHandler());

                  }

              });





            // Start the server.

            sb.bind(Integer.parseInt(PORT)).sync();



            



            serverGroup.awaitTermination(30000, TimeUnit.MILLISECONDS);

            

        } finally {

            serverGroup.shutdownGracefully();

            System.out.println("Server closed");



        }

    }

}