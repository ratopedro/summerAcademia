package pt.it.av.atnog.funNet.cdb.android;

public class Coordinate {
	private double lat, lon;

	public Coordinate(double lat, double lon) {
		this.lat = lat;
		this.lon = lon;
	}

	public double longitude() {
		return lon;
	}

	public double latitude() {
		return lat;
	}

	public String toString() {
		return lat + ";" + lon;
	}
}
