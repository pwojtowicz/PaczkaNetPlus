package pl.netplus.basepackage.exceptions;

import pl.netplus.basepackage.enums.ExceptionErrorCodes;

public class CommunicationException extends Exception {

	private ExceptionErrorCodes errorCode;

	public CommunicationException(String string, ExceptionErrorCodes errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode.toString();
	}

}
