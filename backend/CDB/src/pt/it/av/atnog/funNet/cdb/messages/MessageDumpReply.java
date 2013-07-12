package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageDumpReply extends Message {
	private String dump;

	public MessageDumpReply() {
		super(Message.typeMessageDumpReply);
		this.dump = null;
	}

	public MessageDumpReply(String dump) {
		super(Message.typeMessageDumpReply);
		this.dump = dump;
	}

	public MessageDumpReply(JSONObject json) {
		super(json);
		try {
			dump = json.getString("dump");
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public String dump() {
		return dump;
	}

	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		try {
			json.put("dump", dump);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return json;
	}

	public Message clone(JSONObject json) {
		return new MessageDumpReply(json);
	}
}
