package pt.it.av.atnog.funNet.cdb.messages;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageFactory {

	private static final Message messages[] = { new MessageDumpRequest(),
			new MessageDumpReply(), new MessageSetCoordinatesRequest(),
			new MessageSetCoordinatesReply(), new MessageDeleteRequest(),
			new MessageDeleteReply() };

	public static Message parse(byte[] bytes) {

		Message message = null;
		try {
			JSONObject json = new JSONObject(new String(bytes));
			String type = json.getString("type");

			for (int i = 0; i < messages.length; i++) {
				if (messages[i].type().equals(type)) {
					message = messages[i].clone(json);
					break;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return message;
	}
}
