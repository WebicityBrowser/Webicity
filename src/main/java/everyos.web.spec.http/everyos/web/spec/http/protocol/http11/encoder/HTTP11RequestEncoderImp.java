package everyos.web.spec.http.protocol.http11.encoder;

import java.io.IOException;
import java.io.OutputStream;

import everyos.web.spec.http.protocol.http11.HTTP11Request;
import everyos.web.spec.http.protocol.http11.HTTP11RequestEncoder;

public class HTTP11RequestEncoderImp implements HTTP11RequestEncoder {

	@Override
	public void encodeRequest(HTTP11Request request, OutputStream outputStream) throws IOException {
		writeHTTPHeader(request, outputStream);
		outputStream.write("\r\n".getBytes());
	}

	private void writeHTTPHeader(HTTP11Request request, OutputStream outputStream) throws IOException {
		outputStream.write(request.getMethod().name().getBytes());
		outputStream.write(' ');
		outputStream.write(request.getRequestURI().getBytes());
		outputStream.write(" HTTP/1.1\r\n".getBytes());
	}

}
