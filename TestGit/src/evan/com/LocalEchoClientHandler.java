package evan.com;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LocalEchoClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Object msg) {
        // Print as received
        System.out.println("Server said :" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}