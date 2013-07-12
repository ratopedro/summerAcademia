package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONObject;

public class MessageDumpRequest extends Message {

	public MessageDumpRequest() {
		super(Message.typeMessageDumpRequest);
	}

	public MessageDumpRequest(JSONObject json) {
		super(json);
	}

	public Message clone(JSONObject json) {
		return new MessageDumpRequest(json);
	}
}
