package pt.it.av.atnog.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTP {

	public static String inputStream2String(InputStream is) {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		try {
			String read = br.readLine();
			while (read != null) {
				sb.append(read);
				read = br.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		return sb.toString();
	}
	
	public static String delete(String url) {
		String reply = null;
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(url))
					.openConnection();
			con.setRequestMethod("DELETE");
			reply = inputStream2String(con.getInputStream());
			con.disconnect();
		} catch (Exception e) {
		}
		return reply;
	}

	public static String get(String url) {
		String reply = null;
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(url))
					.openConnection();
			reply = inputStream2String(con.getInputStream());
			con.disconnect();
		} catch (Exception e) {
		}
		return reply;
	}

	public static String post(String url, String data) {
		String reply = null;
		try {
			HttpURLConnection con = (HttpURLConnection) (new URL(url))
					.openConnection();
			con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);

			DataOutputStream wr = new DataOutputStream(
					con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();

			reply = inputStream2String(con.getInputStream());
			con.disconnect();
		} catch (Exception e) {
		}
		return reply;
	}
}
