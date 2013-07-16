package pt.it.av.atnog.map;

import pt.it.av.atnog.funNet.cdb.android.*;

//import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationBehavior {
	private static final int TIME = 1000 * 15;
	private Location lastLocation = null;
	private CDB db;
	/*private Criteria criteria;*/

	LocationBehavior(LocationManager locationManager, CDB db) {
		this.db = db;

		/*criteria = new Criteria();
		criteria.setAltitudeRequired(false);
		criteria.setCostAllowed(false);
		criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);
		criteria.setAccuracy(Criteria.NO_REQUIREMENT);*/

		// Register the listener with the Location Manager to receive location
		// updates
		locationManager.requestLocationUpdates(
				LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
	}

	private boolean isSameProvider(String provider1, String provider2) {
		if (provider1 == null) {
			return provider2 == null;
		}
		return provider1.equals(provider2);
	}

	private boolean isBetterLocation(Location location) {

		if (lastLocation == null) {
			return true;
		}
		
		long timeDelta = location.getTime() - lastLocation.getTime();
		boolean isSignificantlyNewer = timeDelta > TIME;
		boolean isSignificantlyOlder = timeDelta < -TIME;
		boolean isNewer = timeDelta > 0;

		if (isSignificantlyNewer) {
			return true;
		} else if (isSignificantlyOlder) {
			return false;
		}

		int accuracyDelta = (int) (location.getAccuracy() - lastLocation
				.getAccuracy());
		boolean isLessAccurate = accuracyDelta > 0;
		boolean isMoreAccurate = accuracyDelta < 0;
		boolean isSignificantlyLessAccurate = accuracyDelta > 200;
		boolean isFromSameProvider = isSameProvider(location.getProvider(),
				lastLocation.getProvider());

		if (isMoreAccurate) {
			return true;
		} else if (isNewer && !isLessAccurate) {
			return true;
		} else if (isNewer && !isSignificantlyLessAccurate
				&& isFromSameProvider) {
			return true;
		}
		
		return false;
	}

	private void changeLocation(Location location) {
		if (isBetterLocation(location)) {
			this.db.set(location.getLatitude(), location.getLongitude());
			lastLocation = location;
		}
	}

	// Define a listener that responds to location updates
	LocationListener locationListener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location) {
			changeLocation(location);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}
	};
}
