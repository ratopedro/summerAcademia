package pt.it.av.atnog.funNet.im;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.zeromq.ZMQ;
import pt.it.av.atnog.funNet.im.message.*;

public class IMServer {
	private final static Logger logger = Logger.getLogger(IMServer.class
			.getName());;

	private static void setUpLogger() {
		logger.setLevel(Level.INFO);

		try {
			FileHandler file = new FileHandler("log.out");
			SimpleFormatter formatterTxt = new SimpleFormatter();
			file.setFormatter(formatterTxt);
			logger.addHandler(file);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

	public static Message send(IMDB im, MessageSendRequest msg) {
		for (int i = 0; i < msg.txt().size(); i++)
			logger.info(msg.usr() + ": " + msg.txt().get(i));
		im.send(msg.usr(), msg.txt());
		return new MessageSendReply();
	}

	public static Message recv(IMDB im, MessageRecvRequest msg) {
		return new MessageRecvReply(im.recv(msg.timestamp()));
	}

	public static void main(String[] args) {
		setUpLogger();

		boolean done = false;
		IMDB im = new IMDB(30);
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:33664");

		while (!done) {
			Message msg = MessageFactory.parse(socket.recv(0)), reply = null;
			if (msg.type().equals(Message.typeMessageSendRequest)) {
				reply = send(im, (MessageSendRequest) msg);
			} else {
				reply = recv(im, (MessageRecvRequest) msg);
			}

			byte[] replyBytes = reply.toJSON().toString().getBytes();
			socket.send(replyBytes, 0);
		}

		socket.close();
	}
}
