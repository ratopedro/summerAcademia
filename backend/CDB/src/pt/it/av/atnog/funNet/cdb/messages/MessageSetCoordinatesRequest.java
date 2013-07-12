package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONException;
import org.json.JSONObject;

import pt.it.av.atnog.funNet.cdb.VolatileCoordinate;

public class MessageSetCoordinatesRequest extends Message {
	private String key;
	private double lat, lon;
	
	public MessageSetCoordinatesRequest() {
		super(Message.typeMessageSetCoordinateRequest);
		this.key = null;
		this.lat = 0.0;
		this.lon = 0.0;
	}
	
	public MessageSetCoordinatesRequest(String key, VolatileCoordinate coordinate) {
		super(Message.typeMessageSetCoordinateRequest);
		this.key = key;
		lat = coordinate.latitude();
		lon = coordinate.longitude();
	}
	
	public MessageSetCoordinatesRequest(JSONObject json) {
		super(json);
		try {
			key = json.getString("key");
			lat = json.getDouble("lat");
			lon = json.getDouble("lon");
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public String key() {
		return key;
	}
	
	public VolatileCoordinate coordinate() {
		return new VolatileCoordinate(lat, lon);
	}
	
	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		
		try {
			json.put("key", key);
			json.put("lat", lat);
			json.put("lon", lon);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return json;
	}
	
	public Message clone(JSONObject json) {
		return new MessageSetCoordinatesRequest(json);
	}
}
