package pt.it.av.atnog.map;

import java.util.ArrayList;
import java.util.List;

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
	private ArrayList<Marker> marcadores;
	
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
		this.marcadores = new ArrayList<Marker>();
	}
	
	public void handleMessage(Message msg) {
    }
}
