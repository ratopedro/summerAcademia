package pt.it.av.atnog.funNet.im.ws;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pt.it.av.atnog.funNet.im.IMClient;
import pt.it.av.atnog.funNet.im.ShortMessage;

@Path("/")
public class IMWS {
	private static IMClient im = null;
	private static int counter = 0;

	public IMWS() {
		synchronized (this) {
			counter++;
			if (im == null) {
				InetSocketAddress address = new InetSocketAddress(33664);
				im = new IMClient(address);
			}
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		return "{\"application\":\"Instant Messaging\"}";
	}

	@POST
	@Path("send/{usr}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String set(@PathParam("usr") String usr, String txt) {
		boolean rv = true;
		try {
			JSONObject json = new JSONObject(txt);
			JSONArray array = json.getJSONArray("txt");
			List<String> m = new ArrayList<String>();
			for(int i = 0; i < array.length(); i++)
				m.add(array.getString(i));
			im.send(usr, m);
		} catch (JSONException e) {
			e.printStackTrace();
			rv = false;
		}
		
		return "{\"success\":"+Boolean.toString(rv)+"}";
	}

	@GET
	@Path("recv/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public String get(@PathParam("timestamp") long timestamp) {
		List<ShortMessage> shortMsgs= im.recv(timestamp);
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		
		for(int i = 0; i < shortMsgs.size(); i++) {
			array.put(shortMsgs.get(i).toJSON());
		}
		try {
			json.put("shortMsgs", array);
			json.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json.toString();
	}

	public void finalize() {
		synchronized (this) {
			counter--;
			if (counter == 0) {
				im.close();
				im = null;
			}
		}
	}
}
