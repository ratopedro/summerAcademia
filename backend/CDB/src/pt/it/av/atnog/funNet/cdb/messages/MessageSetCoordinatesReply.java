package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONObject;

public class MessageSetCoordinatesReply extends Message {

	public MessageSetCoordinatesReply() {
		super(Message.typeMessageSetCoordinateReply);
	}
	
	public MessageSetCoordinatesReply(JSONObject json) {
		super(json);
	}
	
	public Message clone(JSONObject json) {
		return new MessageSetCoordinatesReply(json);
	}
}
