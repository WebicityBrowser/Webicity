package everyos.web.spec.http.protocol;

public enum HTTPStatusCode {

	CONTINUE(100, "Continue"),
	SWITCHING_PROTOCOLS(101, "Switching Protocols"),
	OK(200, "Ok"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
	NO_CONTENT(204, "No Content"),
	RESET_CONTENT(205, "Reset Content"),
	PARTIAL_CONTENT(206, "Partial Content"),
	MULTIPLE_CHOICES(300, "Multiple Choices"),
	MOVED_PERMANENTLY(301, "Moved Permanently"),
	FOUND(302, "Found"),
	SEE_OTHER(303, "See Other"),
	NOT_MODIFIED(304, "Not Modified"),
	USE_PROXY(305, "Use Proxy"),
	SWITCH_PROXY(306, "Switch Proxy"), // Obsolete
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	PERMANENT_REDIRECT(308, "Permanent Redirect"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	PAYMENT_REQUIRED(402, "Payment Required"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	REQUEST_TIMEOUT(408, "Request Timeout"),
	CONFLICT(409, "Conflict"),
	GONE(410, "Gone"),
	LENGTH_REQUIRED(411, "Length Required"),
	PRECONDITION_FAILED(412, "Precondition Failed"),
	CONTENT_TOO_LARGE(413, "Content Too Large"),
	URI_TOO_LONG(414, "URI Too Long"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	RANGE_NOT_SATISFIABLE(416, "Range Not Satisfiable"),
	EXPECTATION_FAILED(417, "Expectation Failed"),
	SERVER_IS_A_TEAPOT(418, "I'm a teapot"),
	MISDIRECTED_REQUEST(421, "Misdirected Request"),
	UNPROCESSABLE_CONTENT(422, "Unprocessable Content"),
	UPGRADE_REQUIRED(426, "Upgrade Required"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NOT_IMPLEMENTED(501, "Not Implemented"),
	BAD_GATEWAY(502, "Bad Gateway"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

	private final int code;
	private final String message;

	private HTTPStatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	
	public StatusType getType() {
		int typeCode = code/100;
		for (StatusType type: StatusType.values()) {
			if (type.getCode() == typeCode) {
				return type;
			}
		}
		
		return StatusType.UNKNOWN;
	}
	
	public String getMessage() {
		return message;
	}
	
}
