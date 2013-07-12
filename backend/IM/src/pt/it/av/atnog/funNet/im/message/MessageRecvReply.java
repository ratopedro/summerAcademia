package pt.it.av.atnog.funNet.im.message;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.it.av.atnog.funNet.im.ShortMessage;

public class MessageRecvReply extends Message {
	private List<ShortMessage> shortMsgs;

	public MessageRecvReply() {
		super(Message.typeMessageRecvReply);
	}

	public MessageRecvReply(List<ShortMessage> shortMsgs) {
		super(Message.typeMessageRecvReply);
		this.shortMsgs = shortMsgs;
	}

	public MessageRecvReply(JSONObject json) {
		super(json);
		try {
			JSONArray array = json.getJSONArray("shortMsgs");
			shortMsgs = new ArrayList<ShortMessage>(array.length());
			for (int i = 0; i < array.length(); i++)
				shortMsgs.add(new ShortMessage(array.getJSONObject(i)));
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public List<ShortMessage> shortMsgs() {
		return shortMsgs;
	}

	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		JSONArray array = new JSONArray();
		try {
			for (int i = 0; i < shortMsgs.size(); i++)
				array.put(shortMsgs.get(i).toJSON());
			json.put("shortMsgs", array);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return json;
	}

	public Message clone(JSONObject json) {
		return new MessageRecvReply(json);
	}
}
