package br.org.ccb.sgh.exception;

public class InvalidParamException extends RuntimeException {
	private static final long serialVersionUID = -1677274827136849661L;

	private String paramName;
	private String paramValue;

	public InvalidParamException(String paramName, String paramValue, String message) {
		super(message);
		this.setParamName(paramName);
		this.setParamValue(paramValue);
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

}
