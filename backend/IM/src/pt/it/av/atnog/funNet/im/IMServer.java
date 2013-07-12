package pt.it.av.atnog.funNet.im;

import org.zeromq.ZMQ;
import pt.it.av.atnog.funNet.im.message.*;

public class IMServer {

	public static Message send(IMDB im, MessageSendRequest msg) {
		im.send(msg.usr(), msg.txt());
		return new MessageSendReply();
	}

	public static Message recv(IMDB im, MessageRecvRequest msg) {
		return new MessageRecvReply(im.recv(msg.timestamp()));
	}

	public static void main(String[] args) {
		boolean done = false;
		IMDB im = new IMDB(5);
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:33664");

		while (!done) {
			System.out.println("Wainting for a new Message...");
			Message msg = MessageFactory.parse(socket.recv(0)), reply = null;

			System.out.println("Message arrived...");
			System.out.println(msg);
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
