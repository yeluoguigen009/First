package evan.com;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;

import com.rabbitmq.client.Channel;

public class Send {
	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("192.168.220.128");
		factory.setUsername("test");
		factory.setPassword("test");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println("Enter text (quit to end)");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		for (;;) {
			String line = in.readLine();
			if (line == null || "quit".equalsIgnoreCase(line)) {
				break;
			}

			channel.basicPublish("", QUEUE_NAME, null, line.getBytes("UTF-8"));
		}

		channel.close();
		connection.close();
	}

}
