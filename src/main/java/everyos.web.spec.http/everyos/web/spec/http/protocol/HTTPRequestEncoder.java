package everyos.web.spec.http.protocol;

import java.io.IOException;
import java.io.OutputStream;

public interface HTTPRequestEncoder<T extends HTTPRequest> {

	void encodeRequest(T request, OutputStream outputStream) throws IOException;
	
}
