package pt.it.av.atnog.funNet.cdb;

import org.zeromq.ZMQ;

import pt.it.av.atnog.funNet.cdb.messages.*;

public class CDBServer {

	private static Message delete(CDB cdb, MessageDeleteRequest msg) {
		cdb.delete(msg.key());
		return new MessageDeleteReply();
	}

	private static Message set(CDB cdb, MessageSetCoordinatesRequest msg) {
		cdb.set(msg.key(), msg.coordinate());
		return new MessageSetCoordinatesReply();
	}

	private static Message dump(CDB cdb, MessageDumpRequest msg) {
		String dump = cdb.dump2JSON();
		return new MessageDumpReply(dump);
	}

	public static void main(String[] args) {
		boolean done = false;
		CDB cdb = new CDB();
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:33665");
		while (!done) {
			Message msg = MessageFactory.parse(socket.recv(0)), reply = null;
			if (msg.type().equals(Message.typeMessageSetCoordinateRequest)) {
				reply = set(cdb, (MessageSetCoordinatesRequest) msg);
			} else if (msg.type().equals(Message.typeMessageDeleteRequest)) {
				reply = delete(cdb, (MessageDeleteRequest) msg);
			} else {
				reply = dump(cdb, (MessageDumpRequest) msg);
			}

			byte[] replyBytes = reply.toJSON().toString().getBytes();
			socket.send(replyBytes, 0);
		}

		socket.close();
	}
}
