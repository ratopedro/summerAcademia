package pt.it.av.atnog.funNet.im;

import java.util.List;

public interface IIM {

	public void send(String usr, List<String> txt);

	public List<ShortMessage> recv(long timestamp);
}
