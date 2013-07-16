package pt.it.av.atnog.funNet.cdb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CDB implements ICDB {
	private Map<String, VolatileCoordinate> map;
	private final long ttl;

	public CDB() {
		this(60000);
	}

	public CDB(long ttl) {
		map = new HashMap<String, VolatileCoordinate>();
		this.ttl = ttl;
	}

	public void set(String u, VolatileCoordinate c) {
		map.put(u, c);
	}

	public void delete(String u) {
		map.remove(u);
	}

	public String dump2JSON() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();

		try {
			json.put("success", true);
			Iterator<Entry<String, VolatileCoordinate>> it = map.entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<String, VolatileCoordinate> val = it.next();
				if (System.currentTimeMillis() - val.getValue().timestamp() < ttl) {
					JSONObject jcoor = new JSONObject();
					jcoor.put("usr", val.getKey());
					jcoor.put("lat", val.getValue().latitude());
					jcoor.put("lon", val.getValue().longitude());
					array.put(jcoor);
				} else {
					it.remove();
				}
			}
			json.put("cdb", array);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return json.toString();
	}
}
