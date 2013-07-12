package pt.it.av.atnog.funNet.im;

import java.util.ArrayList;
import java.util.List;

public class IMDB implements IIM {
	private int nMaxMsgs;
	private List<ShortMessage> msgs;

	public IMDB(int nMaxMsgs) {
		this.nMaxMsgs = nMaxMsgs;
		msgs = new ArrayList<ShortMessage>();
	}

	public void send(String usr, List<String> txt) {

		for (int i = 0; i < txt.size(); i++)
			msgs.add(new ShortMessage(usr, txt.get(i)));

		int diff = msgs.size() + 1 - nMaxMsgs;

		for (int i = 0; i < diff; i++)
			msgs.remove(0);
	}

	public List<ShortMessage> recv(long timestamp) {
		List<ShortMessage> replies = new ArrayList<ShortMessage>();

		for (int i = 0; i < msgs.size(); i++)
			if (msgs.get(i).timestamp() > timestamp)
				replies.add(msgs.get(i));

		return replies;
	}
}
