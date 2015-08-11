package evan.com;


import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LocalEchoClientHandler extends ChannelHandlerAdapter  {

	//private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public LocalEchoClientHandler() {
/*    	firstMessage = Unpooled.buffer();
    	byte[] bytes = "Hello, netty".getBytes();
    	firstMessage.writeBytes(bytes);
    	System.out.print("The byte[] is ");
    	for(byte b : bytes)
    	{
    		System.out.print(b);
    	}
    	System.out.println();*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //ctx.writeAndFlush(firstMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	ByteBuf byteBuf = (ByteBuf)msg;
    	byte b;
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
			bw.write("Server said :");

			while (byteBuf.isReadable()) {
				b = byteBuf.readByte();
				bw.write((char) b);
			}
			bw.newLine();
			bw.flush();
		} catch (Exception e) {
		}
        //ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}