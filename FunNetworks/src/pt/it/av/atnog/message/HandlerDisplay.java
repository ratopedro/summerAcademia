package pt.it.av.atnog.message;

import java.util.List;

import pt.it.av.atnog.funNet.im.client.IM;
import pt.it.av.atnog.funNet.im.client.ShortMessage;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;

public class HandlerDisplay extends Handler {
	private MessageDisplay display;
	private IM im;
	
	public HandlerDisplay(MessageDisplay display, IM im) {
		this.display = display;
		this.im = im;
	}

	public void handleMessage(Message msg) {
		List<ShortMessage> shortMsg = im.recv();
		
		for(int i=0; i<shortMsg.size(); i++)
		{
			display.print(shortMsg.get(i));
		}
    }
	
	public void receiveMessage(EditText text)
	{
		if (text.getText().toString().length() != 0) {
			im.send(text.getText().toString());
			text.setText("");
		}
	}
}
