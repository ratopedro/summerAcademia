package pt.it.av.atnog.funNet.cdb;

public class VolatileCoordinate {
	private double latitude, longitude;
	private long timestamp;

	public VolatileCoordinate(double latitude, double longitude) {
		this.timestamp = System.currentTimeMillis();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double latitude() {
		return latitude;
	}
	
	public double longitude() {
		return longitude;
	}
	
	public long timestamp() {
		return timestamp;
	}
}
