package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONObject;

public class MessageDeleteReply extends Message {

	public MessageDeleteReply() {
		super(Message.typeMessageDeleteReply);
	}
	
	public MessageDeleteReply(JSONObject json) {
		super(json);
	}
	
	public Message clone(JSONObject json) {
		return new MessageDeleteReply(json);
	}
}
