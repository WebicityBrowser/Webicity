package everyos.web.spec.http.protocol;

public interface HTTPHeaderSet {

	void getHeaderFieldValue(String headerName);
	
	HTTPHeader[] getHeaders();
	
	String[] getHeaderNames();
	
}
