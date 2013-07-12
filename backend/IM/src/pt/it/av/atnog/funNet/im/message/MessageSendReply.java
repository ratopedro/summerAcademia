package pt.it.av.atnog.funNet.im.message;

import org.json.JSONObject;

public class MessageSendReply extends Message {

	public MessageSendReply() {
		super(Message.typeMessageSendReply);
	}

	public MessageSendReply(JSONObject json) {
		super(json);
	}

	public Message clone(JSONObject json) {
		return new MessageSendReply(json);
	}
}
