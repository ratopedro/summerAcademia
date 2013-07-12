package pt.it.av.atnog.map;

import java.util.ArrayList;
import java.util.List;

import pt.it.av.atnog.funNet.cdb.android.CDB;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Handler;
import android.os.Message;

public class HandlerMapDisplay extends Handler {
	private boolean firstTime = true;

	private ArrayList<Marker> marcadores;
	
	private GoogleMap map;
	private CDB db;
	private String nome;
	private int icon;
	
	public HandlerMapDisplay(GoogleMap map, CDB db, String nome, int icon)
	{
		this.map = map;
		this.nome = nome;
		this.icon = icon;
		this.db = db;
		this.marcadores = new ArrayList<Marker>();
	}
	
	public void handleMessage(Message msg) {
		if(firstTime)
		{
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(db.get(nome).latitude(), db.get(nome).longitude()), 18.0f));
			firstTime = false;
		}
		
		for(int i=0; i<marcadores.size(); i++)
		{
			marcadores.get(i).remove();
		}
		
		List<String> grupos = db.getGroups();
		
		for(int i=0; i<grupos.size(); i++)
		{
			marcadores.add(map.addMarker(new MarkerOptions()
				.position(new LatLng(db.get(grupos.get(i)).latitude(), db.get(grupos.get(i)).longitude()))
				.title(grupos.get(i))
				.snippet("Texto com algo")
				.icon(BitmapDescriptorFactory.fromResource(icon))));			
		}
    }
}
