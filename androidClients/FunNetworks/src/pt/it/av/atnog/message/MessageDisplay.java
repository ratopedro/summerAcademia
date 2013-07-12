package pt.it.av.atnog.message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.it.av.atnog.funNet.im.client.ShortMessage;

public class MessageDisplay {
	private MessageAdapter adapter;
	private List<Map<String,String>> messages;
	
	public MessageDisplay(MessageAdapter adapter, List<Map<String,String>> messages)
	{
		this.adapter = adapter;
		this.messages = messages;
	}
	
	public void print(ShortMessage msg)
	{
		int lastMessageIndex = messages.size()-1;
		if(lastMessageIndex >= 0 && messages.get(lastMessageIndex).get("name").equals(msg.usr()))
		{
			// Caso se queira meter as mensagens da mesma pessoa no mesmo item
			messages.get(lastMessageIndex).put("message",messages.get(lastMessageIndex).get("message") + "\n" + msg.txt());
		}
		else
		{
			// Caso contrário
			Map<String, String> map = new HashMap<String, String>();
			map.put("message", msg.txt());
			map.put("name", msg.usr());
			messages.add(map);
		}
		

		adapter.notifyDataSetChanged();
	}
}
