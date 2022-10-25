package everyos.web.spec.http;

import java.io.OutputStream;

public interface HTTPRequestEncoder<T extends HTTPRequest> {

	OutputStream encodeRequest(T request);
	
}
