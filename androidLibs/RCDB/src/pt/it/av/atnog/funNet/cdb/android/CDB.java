package pt.it.av.atnog.funNet.cdb.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.it.av.atnog.http.HTTP;

public class CDB implements Runnable {
	private final ScheduledFuture<?> sync;
	private static final long DEFAULT_RATE = 1;
	private String address;
	private final String usr;
	private Map<String, Coordinate> map;
	private Object mutex;

	public CDB(String usr) {
		this(usr, "funnetworks.aws.atnog.av.it.pt", 80, DEFAULT_RATE);
	}

	public CDB(String usr, long rate) {
		this(usr, "funnetworks.aws.atnog.av.it.pt", 80, rate);
	}

	public CDB(String usr, String address, long port, long rate) {
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(1);
		sync = s.scheduleAtFixedRate(this, rate, rate, TimeUnit.SECONDS);
		this.address = address + ":" + port;
		this.mutex = new Object();
		this.map = new HashMap<String, Coordinate>();
		this.usr = usr;
		this.sync();
	}

	public void set(double lat, double lon) {
		Coordinate c = new Coordinate(lat, lon);
		set(c);
	}

	public void set(Coordinate c) {
		if (c != null) {
			synchronized (mutex) {
				map.put(usr, c);
			}
		}
	}

	public Coordinate get(String key) {
		Coordinate c;

		synchronized (mutex) {
			c = map.get(key);
		}

		return c;
	}

	public List<String> getGroups() {
		Set<Entry<String, Coordinate>> entries = null;
		synchronized (mutex) {
			entries = map.entrySet();
		}

		List<String> groups = new ArrayList<String>(entries.size());
		Iterator<Entry<String, Coordinate>> it = entries.iterator();

		while (it.hasNext()) {
			Entry<String, Coordinate> entry = it.next();
			groups.add(entry.getKey());
		}

		return groups;
	}

	public void run() {
		sync();
	}

	private void sync() {
		Coordinate c;
		synchronized (mutex) {
			c = map.get(usr);
		}

		if (c != null) {
			try {
				JSONObject json = new JSONObject();
				json.put("lat", c.latitude());
				json.put("lon", c.longitude());
				HTTP.post("http://" + address + "/cdbws/set/" + usr,
						json.toString());
			} catch (JSONException e) {

			}
		}

		try {
			String reply = HTTP.get("http://" + address + "/cdbws/dump");
			if (reply != null) {
				JSONObject json = new JSONObject(reply);
				if (json.has("success") && json.getBoolean("success")) {
					JSONArray array = json.getJSONArray("cdb");
					synchronized (mutex) {
						map.clear();

						for (int i = 0; i < array.length(); i++) {
							JSONObject j = array.getJSONObject(i);
							map.put(j.getString("usr"),
									new Coordinate(j.getDouble("lat"), j
											.getDouble("lon")));
						}

						if (c != null)
							map.put(usr, c);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	public void close() {
		HTTP.delete("http://" + address + "/cdbws/CDB/delete/" + this.usr);
		sync.cancel(true);
	}

	public void finalize() {
		this.close();
	}
}
