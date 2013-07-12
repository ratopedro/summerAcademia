package pt.it.av.atnog.funNet.im;

import java.net.InetSocketAddress;
import java.util.List;

public class Test {

	public static void print(List<ShortMessage> msg) {
		for(int i = 0; i < msg.size(); i++) {
			System.out.println(msg.get(i).usr()+":"+msg.get(i).txt());
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		IMClient im = new IMClient(new InetSocketAddress(33664));
		
		im.send("mantunes", "Hello");
		print(im.recv(0));
		
		
		im.send("mantunes", "Hello World");
		print(im.recv(0));
		
		im.send("mantunes", "Hello Bigger World");
		print(im.recv(0));
	}
}
