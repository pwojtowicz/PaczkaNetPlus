package pl.netplus.basepackage.httpproviders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import android.util.Log;

public class HTTPRequestProvider {

	/**
	 * Timeout po��czenia
	 */
	public static final int CONNECTION_TIMEOUT = 30000;
	/**
	 * Timeout odczytu
	 */
	public static final int READ_TIMEOUT = 30000;

	public static HTTPRequestBundle sendRequest(String address,
			IHttpRequestToAsyncTaskCommunication listener) {
		checkIsOnLine();

		return getRequest(address, listener);

	}

	private static HTTPRequestBundle getRequest(String address,
			IHttpRequestToAsyncTaskCommunication listener) {

		Log.i("WishUrlAddress", address);
		HttpURLConnection connection = null;
		String response = null;
		byte[] rawResponse = null;
		address = address.replace(" ", "%20");
		int responseCode = 0;
		try {
			connection = getConnection(address);
			connection.setRequestMethod("GET");

			connection = setConnectionHeader(connection);

			connection.connect();
			responseCode = connection.getResponseCode();

			int fileSize = connection.getContentLength();

			InputStream in = getConnectionInputStream(connection);

			ByteArrayOutputStream byteArray = readInpdutStream(in, fileSize,
					listener);

			System.out.println("Content Size: " + String.valueOf(fileSize)
					+ " byteArray Size: " + String.valueOf(byteArray.size()));

			try {
				response = byteArray.toString();// ("ISO-8859-2");
			} catch (Exception e) {
				response = "ConvertToStringProblem";
			}
			connection.disconnect();
			connection = null;

		} catch (IOException e) {
			response = e.getMessage();
		} finally {
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}

		}

		HTTPRequestBundle bundle = new HTTPRequestBundle();
		bundle.setResponse(response);
		bundle.setRawResponse(rawResponse);
		bundle.setStatusCode(responseCode);

		return bundle;
	}

	public static InputStream getConnectionInputStream(
			HttpURLConnection connection) throws IOException {
		InputStream input = null;
		String encoding = connection.getHeaderField("Content-Encoding");
		int responseCode = connection.getResponseCode();

		if (responseCode == 400)
			input = connection.getErrorStream();
		else
			input = connection.getInputStream();

		if (encoding != null && encoding.equals("gzip"))
			input = new GZIPInputStream(input);

		return input;
	}

	private static ByteArrayOutputStream readInpdutStream(
			InputStream inputStream, int fileSize,
			IHttpRequestToAsyncTaskCommunication listener) throws IOException {
		byte[] buf = new byte[4096];

		int ret = 0;
		long downloadedSize = 0;
		int lastProgressValue = -1;
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		while ((ret = inputStream.read(buf)) > 0) {
			byteArray.write(buf, 0, ret);

			downloadedSize += ret;
			int progressPercent = 0;
			if (fileSize != 0)
				progressPercent = (int) (downloadedSize * 100) / fileSize;

			if ((progressPercent % 5) == 0
					&& lastProgressValue < progressPercent) {

				if (listener != null) {
					listener.onObjectsProgressUpdate(progressPercent);
					if (listener.checkIsTaskCancled()) {
						break;
					}
				}
				lastProgressValue = progressPercent;
			}
		}
		return byteArray;
	}

	private static HttpURLConnection getConnection(String address)
			throws IOException {
		URL url = new URL(address);
		HttpURLConnection connection = null;
		connection = (HttpURLConnection) url.openConnection();
		return connection;
	}

	private static HttpURLConnection setConnectionHeader(
			HttpURLConnection connection) {

		// oczekiwany typ zwracanych danych - JSON
		connection.setRequestProperty("Accept", "application/json");
		connection.setRequestProperty("Accept-Encoding", "gzip");

		connection.setConnectTimeout(CONNECTION_TIMEOUT);
		connection.setReadTimeout(READ_TIMEOUT);

		return connection;
	}

	private static void checkIsOnLine() {
		// TODO Auto-generated method stub

	}
}
