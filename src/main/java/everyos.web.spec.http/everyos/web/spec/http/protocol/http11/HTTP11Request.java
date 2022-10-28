package everyos.web.spec.http.protocol.http11;

import everyos.web.spec.http.protocol.HTTPHeader;
import everyos.web.spec.http.protocol.HTTPRequest;

public interface HTTP11Request extends HTTPRequest {

	HTTP11RequestMethod getMethod();
	
	String getRequestURI();
	
	HTTPHeader[] getHeaders();
	
}
