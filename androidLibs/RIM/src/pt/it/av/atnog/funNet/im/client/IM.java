package pt.it.av.atnog.funNet.im.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.it.av.atnog.http.HTTP;

public class IM implements Runnable {
	private final ScheduledFuture<?> sync;
	private static final long DEFAULT_RATE = 1;
	private final String address, usr;
	private List<String> bufferOut;
	private List<ShortMessage> bufferIn;
	private Object mIn, mOut;
	private long timestamp;

	public IM(String usr) {
		this(usr, "funnetworks.aws.atnog.av.it.pt", 80, DEFAULT_RATE);
	}

	public IM(String usr, long rate) {
		this(usr, "funnetworks.aws.atnog.av.it.pt", 80, rate);
	}

	public IM(String usr, String address, long port, long rate) {
		this.usr = usr;
		this.address = address + ":" + port;
		ScheduledThreadPoolExecutor s = new ScheduledThreadPoolExecutor(1);
		sync = s.scheduleAtFixedRate(this, rate, rate, TimeUnit.SECONDS);
		mIn = new Object();
		mOut = new Object();
		timestamp = 0;
		bufferIn = new ArrayList<ShortMessage>();
		bufferOut = new ArrayList<String>();
		sync();
	}

	public void reset() {
		timestamp = 0;
	}

	public void send(String txt) {
		if (txt.length() > 0) {
			synchronized (mOut) {
				bufferOut.add(txt);
			}
		}
	}

	public List<ShortMessage> recv() {
		List<ShortMessage> rv;
		synchronized (mIn) {
			if (bufferIn.size() > 0) {
				rv = new ArrayList<ShortMessage>(bufferIn);
				bufferIn.clear();
			} else
				rv = new ArrayList<ShortMessage>();
		}
		return rv;
	}

	private String buffer2JSON(List<String> data) {
		JSONObject json = new JSONObject();
		try {
			JSONArray array = new JSONArray();
			for (int i = 0; i < data.size(); i++)
				array.put(data.get(i));
			json.put("txt", array);
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return json.toString();
	}

	private void sync() {
		try {
			synchronized (mOut) {
				if (bufferOut.size() > 0) {
					String data = buffer2JSON(bufferOut), reply = HTTP.post(
							"http://" + address + "/imws/send/" + usr, data);
					if (reply != null) {
						JSONObject json = new JSONObject(reply);
						if (json.has("success") && json.getBoolean("success")) {
							bufferOut.clear();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			String reply = HTTP.get("http://" + address + "/imws/recv/"
					+ timestamp);
			if (reply != null) {
				JSONObject json = new JSONObject(reply);
				if (json.has("success") && json.getBoolean("success")) {
					JSONArray array = json.getJSONArray("shortMsgs");
					synchronized (mIn) {
						for (int i = 0; i < array.length(); i++)
							bufferIn.add(new ShortMessage(array
									.getJSONObject(i)));
						if (bufferIn.size() > 0)
							timestamp = bufferIn.get(bufferIn.size() - 1)
									.timestamp();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		sync();
	}

	public void close() {
		sync.cancel(true);
	}

	public void finalize() {
		close();
	}
}
