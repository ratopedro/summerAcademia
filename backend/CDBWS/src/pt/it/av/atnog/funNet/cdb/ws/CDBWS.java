package pt.it.av.atnog.funNet.cdb.ws;

import java.net.InetSocketAddress;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

import pt.it.av.atnog.funNet.cdb.*;

@Path("/")
public class CDBWS {
	private static CDBClient cdb = null;
	private static int counter = 0;

	public CDBWS() {
		synchronized (this) {
			counter++;
			if (cdb == null) {
				InetSocketAddress address = new InetSocketAddress(33665);
				cdb = new CDBClient(address);
			}
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessage() {
		String txt = "{\"application\":\"Coordinate DataBase\"}";
		return txt;
	}

	@POST
	@Path("set/{usr}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public String set(@PathParam("usr") String usr, String txt) {
		boolean rv = true;
		try {
			JSONObject json = new JSONObject(txt);
			cdb.set(usr, new VolatileCoordinate(json.getDouble("lat"), json.getDouble("lon")));
		} catch (JSONException e) {
			e.printStackTrace();
			rv = false;
		}
		
		return "{\"success\":"+Boolean.toString(rv)+"}";
	}

	@DELETE
	@Path("delete/{usr}")
	@Produces("application/json")
	public String delete(@PathParam("usr") String usr) {
		cdb.delete(usr);
		return "{\"success\":\"true\"}";
	}

	@GET
	@Path("dump")
	@Produces("application/json")
	public String get() {
		return cdb.dump2JSON();
	}
	
	public void finalize() {
		synchronized (this) {
			counter--;
			if (counter == 0) {
				cdb.close();
				cdb = null;
			}
		}
	}
}
