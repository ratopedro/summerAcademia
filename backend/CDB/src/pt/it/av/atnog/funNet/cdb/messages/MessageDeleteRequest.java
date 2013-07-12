package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageDeleteRequest extends Message {
	private String key;

	public MessageDeleteRequest() {
		super(Message.typeMessageDeleteRequest);
	}

	public MessageDeleteRequest(String key) {
		super(Message.typeMessageDeleteRequest);
		this.key = key;
	}

	public MessageDeleteRequest(JSONObject json) {
		super(json);
		try {
			key = json.getString("key");
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public String key() {
		return key;
	}
	
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		try {
			json.put("key", key);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return json;
	}
	
	public Message clone(JSONObject json) {
		return new MessageDeleteRequest(json);
	}
}
