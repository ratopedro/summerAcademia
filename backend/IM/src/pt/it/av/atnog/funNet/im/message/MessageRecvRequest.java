package pt.it.av.atnog.funNet.im.message;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageRecvRequest extends Message {
	private long timestamp;
	
	public MessageRecvRequest() {
		this(0);
	}
	
	public MessageRecvRequest(long timestamp) {
		super(Message.typeMessageRecvRequest);
		this.timestamp = timestamp;
	}

	public MessageRecvRequest(JSONObject json) {
		super(json);
		try {
			timestamp = json.getLong("timestamp");
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public long timestamp() {
		return timestamp;
	}
	
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		try {
			json.put("timestamp", timestamp);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return json;
	}

	public Message clone(JSONObject json) {
		return new MessageRecvRequest(json);
	}
}
