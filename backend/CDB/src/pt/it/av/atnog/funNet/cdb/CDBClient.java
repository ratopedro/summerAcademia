package pt.it.av.atnog.funNet.cdb;

import java.net.InetSocketAddress;

import org.zeromq.ZMQ;

import pt.it.av.atnog.funNet.cdb.messages.*;

public class CDBClient implements ICDB{
	private ZMQ.Socket socket;

	public CDBClient(InetSocketAddress address) {
		ZMQ.Context context = ZMQ.context(1);
		socket = context.socket(ZMQ.REQ);
		socket.connect("tcp://" + address.getAddress().getHostAddress() + ":"
				+ address.getPort());
	}

	public synchronized void set(String u, VolatileCoordinate c) {
		Message msg = new MessageSetCoordinatesRequest(u, c);
		socket.send(msg.toJSON().toString().getBytes(), 0);
		msg = MessageFactory.parse(socket.recv(0));
	}

	public synchronized void delete(String u) {
		Message msg = new MessageDeleteRequest(u);
		socket.send(msg.toJSON().toString().getBytes(), 0);
		msg = MessageFactory.parse(socket.recv(0));
	}
	
	public synchronized String dump2JSON() {
		Message msg = new MessageDumpRequest();
		socket.send(msg.toJSON().toString().getBytes(), 0);
		msg = MessageFactory.parse(socket.recv());
		return (((MessageDumpReply) msg).dump());
	}
	
	public void close() {
		socket.close();
		socket = null;
	}
	
	public void finalize() {
		close();
	}
}
