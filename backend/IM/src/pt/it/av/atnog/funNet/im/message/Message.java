package pt.it.av.atnog.funNet.im.message;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Message {
	private final String type;

	public Message(String type) {
		this.type = type;
	}

	public Message(JSONObject json) {
		String temp = null;
		try {
			temp = json.getString("type");
		} catch (JSONException e) {

			e.printStackTrace();
			System.exit(-1);
		}
		type = temp;
	}

	public String type() {
		return type;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("type", type);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		return json;
	}

	public abstract Message clone(JSONObject json);

	public boolean equals(Object obj) {
		boolean rv = false;

		if (obj != null && obj instanceof Message) {
			Message tempMessage = (Message) obj;
			if (tempMessage.type() == this.type()) {
				rv = true;
			}
		}

		return rv;
	}

	public String toString() {
		return toJSON().toString();
	}

	public static final String typeMessageSendRequest = "send request";
	public static final String typeMessageSendReply = "send reply";
	public static final String typeMessageRecvRequest = "recv request";
	public static final String typeMessageRecvReply = "recv reply";
}
