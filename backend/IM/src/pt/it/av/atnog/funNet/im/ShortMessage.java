package pt.it.av.atnog.funNet.im;

import org.json.JSONException;
import org.json.JSONObject;

public class ShortMessage {
	private String usr, txt;
	private long timestamp;

	public ShortMessage(String usr, String txt) {
		this.usr = usr;
		this.txt = txt;
		this.timestamp = System.currentTimeMillis();
	}

	public ShortMessage(JSONObject json) {
		try {
			usr = json.getString("usr");
			txt = json.getString("txt");
			timestamp = json.getLong("timestamp");
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public long timestamp() {
		return timestamp;
	}
	
	public String usr() {
		return usr;
	}
	
	public String txt() {
		return txt;
	}

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		toJSON(json);
		return json;
	}

	public void toJSON(JSONObject json) {
		try {
			json.put("usr", usr);
			json.put("txt", txt);
			json.put("timestamp", timestamp);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
