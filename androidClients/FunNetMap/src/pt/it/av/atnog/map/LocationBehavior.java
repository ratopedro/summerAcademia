package pt.it.av.atnog.map;


import pt.it.av.atnog.funNet.cdb.android.CDB;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationBehavior {
	private Location lastLocation;
	private CDB db;
	private Criteria criteria;

	LocationBehavior(LocationManager locationManager, CDB db) {
		this.db = db;

		criteria = new Criteria();
		criteria.setAltitudeRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		
		lastLocation = new Location(locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
		db.set(lastLocation.getLatitude(), lastLocation.getLongitude());
		
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 1000, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);
	}
	
	private void changeLocation(Location location)
	{
		if(lastLocation.getAccuracy() < location.getAccuracy() || location.getTime() - lastLocation.getTime() > 10000)
		{
			this.db.set(location.getLatitude(), location.getLongitude());
			lastLocation = location;
		}
	}
	
	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
		public void onLocationChanged(Location location) {
			changeLocation(location);
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}
	};
}
