package everyos.web.spec.http.http11;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.http.protocol.HTTPHeader;
import everyos.web.spec.http.protocol.http11.HTTP11Request;
import everyos.web.spec.http.protocol.http11.HTTP11RequestEncoder;
import everyos.web.spec.http.protocol.http11.HTTP11RequestMethod;
import everyos.web.spec.http.protocol.http11.encoder.HTTP11RequestEncoderImp;

public class HTTP11RequestEncoderTest {

	private HTTP11RequestEncoder encoder;
	
	@BeforeEach
	public void beforeEach() {
		this.encoder = new HTTP11RequestEncoderImp();
	}
	
	@Test
	@DisplayName("Can encode simple get request")
	public void canEncodeSimpleGetRequest() {
		HTTP11Request getRequest = Mockito.mock(HTTP11Request.class);
		Mockito.when(getRequest.getMethod()).thenReturn(HTTP11RequestMethod.GET);
		Mockito.when(getRequest.getRequestURI()).thenReturn("/index.html");
		Mockito.when(getRequest.getHeaders()).thenReturn(new HTTPHeader[] {});
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		Assertions.assertDoesNotThrow(() -> encoder.encodeRequest(getRequest, outputStream));
		String finalHeader = outputStream.toString(StandardCharsets.UTF_8);
		Assertions.assertEquals("GET /index.html HTTP/1.1\r\n\r\n", finalHeader);
	}
	
}
