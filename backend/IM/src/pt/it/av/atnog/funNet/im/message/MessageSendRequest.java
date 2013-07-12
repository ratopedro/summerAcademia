package pt.it.av.atnog.funNet.im.message;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MessageSendRequest extends Message {
	private String usr;
	private List<String> txt;

	public MessageSendRequest() {
		this(null, null);
	}

	public MessageSendRequest(String usr, List<String> txt) {
		super(Message.typeMessageSendRequest);
		this.usr = usr;
		this.txt = txt;
	}

	public MessageSendRequest(JSONObject json) {
		super(json);
		try {
			usr = json.getString("usr");
			JSONArray array = json.getJSONArray("txt");
			txt = new ArrayList<String>();
			for(int i = 0; i < array.length(); i++)
				txt.add(array.getString(i));
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public String usr() {
		return this.usr;
	}

	public List<String> txt() {
		return this.txt;
	}
	
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		try {
			json.put("usr", usr);
			json.put("txt", txt);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return json;
	}

	public Message clone(JSONObject json) {
		return new MessageSendRequest(json);
	}
}
