package pt.it.av.atnog.funNet.im;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.zeromq.ZMQ;

import pt.it.av.atnog.funNet.im.message.*;

public class IMClient implements IIM {
	private ZMQ.Socket socket;

	public IMClient(InetSocketAddress address) {
		ZMQ.Context context = ZMQ.context(1);
		socket = context.socket(ZMQ.REQ);
		socket.connect("tcp://" + address.getAddress().getHostAddress() + ":"
				+ address.getPort());
	}
	
	public void send(String u, String m) {
		List<String> txt = new ArrayList<String>(1);
		txt.add(m);
		send(u, txt);
	}

	public synchronized void send(String u, List<String> m) {
		Message msg = new MessageSendRequest(u, m);
		socket.send(msg.toJSON().toString().getBytes(), 0);
		msg = MessageFactory.parse(socket.recv(0));
	}

	public synchronized List<ShortMessage> recv(long t) {
		Message msg = new MessageRecvRequest(t);
		socket.send(msg.toJSON().toString().getBytes(), 0);
		msg = MessageFactory.parse(socket.recv(0));
		return (((MessageRecvReply) msg).shortMsgs());
	}
	
	public void close() {
		socket.close();
		socket = null;
	}
	
	public void finalize() {
		close();
	}
}
