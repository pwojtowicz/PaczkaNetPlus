package pl.netplus.basepackage.httpproviders;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import pl.netplus.basepackage.enums.ExceptionErrorCodes;
import pl.netplus.basepackage.exceptions.CommunicationException;

public class Provider<T> {

	private String errorMessage = null;
	private final Class<T> classObject;

	public Provider(Class<T> classObject) {
		super();
		this.classObject = classObject;
	}

	public T getObjects(String url,
			IHttpRequestToAsyncTaskCommunication listener)
			throws CommunicationException {
		HTTPRequestBundle bundle = HTTPRequestProvider.sendRequest(url,
				listener);
		try {
			if (bundle.getStatusCode() == HttpsURLConnection.HTTP_OK
					|| bundle.getStatusCode() == 2000) {

				String value = bundle.getResponse();

				String p = url.substring(url.length() - 1, url.length());

				// value = value.replace("\"", "[=]");
				// value = value.replace('\'', '\"');

				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(
						JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);

				T items;
				items = mapper.readValue(value, classObject);
				return items;
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_NOT_FOUND) {
				throw new CommunicationException("",
						ExceptionErrorCodes.HTTP_NOT_FOUND);
			} else if (bundle.getStatusCode() == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
				throw new CommunicationException("",
						ExceptionErrorCodes.HTTP_INTERNAL_ERROR);
			} else {
				throw new CommunicationException("",
						ExceptionErrorCodes.UnexpectedResponseStatusCode);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			errorMessage = e.getMessage();
		}
		return null;
	}
}
