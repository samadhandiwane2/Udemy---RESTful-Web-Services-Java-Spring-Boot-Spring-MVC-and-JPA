package com.mobile.ws.error;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELDS("Missing required field. Please Check documentation for required fields"),
	RECORD_ALREADYEXISTS("Record already exists"), 
	INTERNAL_SERVER_ERROR("Internal Server Error"),
	NO_RECORDS_FOUND("No Records Found"), 
	AUTHENTICATION_FAILED("Authentication Failed"),
	COULD_NOT_UPDATE_RECORD("Could not update Record"), 
	COULD_NOT_DELETE_RECORD("Could not delete Record"),
	EMAIL_ADDRESS_NOT_VERIIED("Email address could not be verified");

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
