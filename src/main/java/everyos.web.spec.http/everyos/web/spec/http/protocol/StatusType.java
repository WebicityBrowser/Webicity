package everyos.web.spec.http.protocol;

public enum StatusType {

	UNKNOWN(0), INFORMATIONAL(1), SUCCESSFUL(2),
	REDIRECTION(3), CLIENT_ERROR(4), SERVER_ERROR(5);

	private final int code;
	
	private StatusType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
}
