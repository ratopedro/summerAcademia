package pt.it.av.atnog.map;

import java.util.ArrayList;
import pt.it.av.atnog.funNet.remoteCDB.RCDB;

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
	private Marker marcador;
	
	private GoogleMap map;
	private RCDB db;
	private String nome;
	private int icon;
	
	public HandlerMapDisplay(GoogleMap map, RCDB db, String nome, int icon)
	{
		this.map = map;
		this.nome = nome;
		this.icon = icon;
		this.db = db;
	}
	
	public void handleMessage(Message msg) {		
		if(firstTime)
		{
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(db.get(nome).latitude(), db.get(nome).longitude()), 18.0f));
			firstTime = false;
		}
		
		if(marcador != null)
		{
			marcador.remove();
		}
		
		marcador = map.addMarker(new MarkerOptions()
			.position(new LatLng(db.get(nome).latitude(), db.get(nome).longitude()))
			.title(nome)
			.snippet("O Paulo gosta de bananas!")
			.icon(BitmapDescriptorFactory.fromResource(icon)));			
    }
}
