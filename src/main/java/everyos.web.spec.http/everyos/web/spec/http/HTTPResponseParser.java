package everyos.web.spec.http;

import java.io.InputStream;

public interface HTTPResponseParser<T extends HTTPResponse> {
	
	T parse(InputStream inputStream);
	
}
