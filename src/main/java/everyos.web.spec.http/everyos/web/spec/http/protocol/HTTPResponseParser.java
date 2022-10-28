package everyos.web.spec.http.protocol;

import java.io.InputStream;

public interface HTTPResponseParser<T extends HTTPResponse> {
	
	T parse(InputStream inputStream);
	
}
