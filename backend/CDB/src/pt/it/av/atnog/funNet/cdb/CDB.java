package pt.it.av.atnog.funNet.cdb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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

			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();

			while (it.hasNext()) {
				String usr = it.next();
				VolatileCoordinate c = map.get(usr);
				if (c != null
						&& (System.currentTimeMillis() - c.timestamp() < ttl))
					array.put(new JSONObject("{\"usr\":\"" + usr
							+ "\",\"lat\":" + c.latitude() + ",\"lon\":"
							+ c.longitude() + "}"));
				else if(c != null)
					map.remove(usr);
			}
			json.put("cdb", array);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}

		return json.toString();
	}
}