package pt.it.av.atnog.map;

import java.util.Timer;

import pt.it.av.atnog.R;
import pt.it.av.atnog.TTMethod;
import pt.it.av.atnog.funNet.cdb.android.CDB;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapsFragment extends Fragment {
	private GoogleMap map;
	private CDB db;
	private LocationBehavior locationBehavior;
	private Handler markerHandler;
	
	private Timer timer;
	
	private String nome;
	private int icon;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Obter argumentos
		Bundle bun = getArguments();
		
		// Inicializar vari�veis com os argumentos
		this.nome = bun.getString("name");
		this.icon = bun.getInt("icon");
		
		return inflater.inflate(R.layout.activity_map, container, false);
	}
	
	@Override    
	public void onStart() {
        super.onStart();

        if(map == null)
        {
        	// Obter o objecto referente ao mapa;
        	map = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        	
        	// Colocar a vista do mapa sobre Portugal
        	map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40, -8), 6.0f));
        	
        	// Retirar os but�es de zoom da interface
			map.getUiSettings().setZoomControlsEnabled(false);
			
			/**
			 * Define o tipo de mapa, poss�veis valores:
			 * 
			 * Normal - Typical road map. Roads, some man-made features, and important natural features such as rivers are shown. Road and feature labels are also visible.
			 * Hybrid - Satellite photograph data with road maps added. Road and feature labels are also visible.
			 * Satellite - Satellite photograph data. Road and feature labels are not visible.
			 * Terrain - Topographic data. The map includes colors, contour lines and labels, and perspective shading. Some roads and labels are also visible.
			 * None - No tiles. The map will be rendered as an empty grid with no tiles loaded.
			 */
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			
        	Options.EEInicialization(map);
			
			// Location database
			db = new CDB(nome);
			locationBehavior = new LocationBehavior((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), db);

			markerHandler = new HandlerMapDisplay(map, db, nome, icon);
			
			timer = new Timer();
			TTMethod method = new TTMethod(markerHandler); 
			timer.schedule(method, 5000, 5000);
        }
	}
}