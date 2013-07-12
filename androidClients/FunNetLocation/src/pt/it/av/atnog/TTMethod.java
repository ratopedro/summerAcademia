package pt.it.av.atnog;

import java.util.TimerTask;

import android.os.Handler;

public class TTMethod extends TimerTask {
	private Handler markerHandler;
	
	public TTMethod(Handler markerHandler)
	{
		this.markerHandler = markerHandler;
	}
	
	@Override
	public void run() {
		markerHandler.obtainMessage(1).sendToTarget();
	}
}