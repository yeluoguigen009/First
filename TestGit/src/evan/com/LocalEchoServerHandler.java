package evan.com;

import java.io.BufferedWriter;

import java.io.OutputStreamWriter;

import java.util.Random;

import javax.swing.text.ComponentView;

import io.netty.buffer.ByteBuf;

import io.netty.buffer.Unpooled;

import io.netty.channel.ChannelHandlerAdapter;

import io.netty.channel.ChannelHandlerContext;

public class LocalEchoServerHandler extends ChannelHandlerAdapter {

	@Override

	public void channelRead(ChannelHandlerContext ctx, Object msg) {

		// Write back as received

		ByteBuf byteBuf = (ByteBuf) msg;

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		try {

			bw.write("Client said :");

			byte b;

			while (byteBuf.isReadable())

			{

				b = byteBuf.readByte();

				bw.write((char) b);

			}

			bw.newLine();

			bw.flush();

		}

		catch (Exception e) {
		}

		// ctx.write(msg);

	}

	String[] answerList = new String[] {

			"Roger that.",

			"Good idea",

			"Appreciate your effort",

			"Wonderful proposal",

			"Nice to meet you",

			"Why not?",

			"It's very cool",

			"Sure, no problem",

			"I heard that's a good place",

			"Maybe it works",

			"Who knows",

			"Huh?",

			"Join the club",

			"Never mind",

			"Pursue your dream"

	};

	Random r = new Random();

	@Override

	public void channelReadComplete(ChannelHandlerContext ctx) {

		ByteBuf byteBuf = Unpooled.buffer().writeBytes(answerList[r.nextInt(answerList.length)].getBytes());

		ctx.writeAndFlush(byteBuf);

	}

	@Override

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

		cause.printStackTrace();

		ctx.close();

	}

}