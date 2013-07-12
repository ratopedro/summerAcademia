package pt.it.av.atnog.funNet.cdb;

public interface ICDB {

	public void set(String u, VolatileCoordinate c);
	
	public void delete(String u);
	
	public String dump2JSON();
}
