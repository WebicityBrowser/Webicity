package everyos.api.getopts;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public final class TestUtils {
	
	private TestUtils() {}
	
	public static final class StringPrintStream extends PrintStream {
		private ByteArrayOutputStream outputStream;
		private String encoding;

		private StringPrintStream(ByteArrayOutputStream stream, String encoding) throws UnsupportedEncodingException {
			super(stream, true, encoding);
			
			this.outputStream = stream;
			this.encoding = encoding;
		}
		
		@Override
		public String toString() {
			try {
				return outputStream.toString(encoding);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		
		public static StringPrintStream create() {
			try {
				return new StringPrintStream(new ByteArrayOutputStream(), StandardCharsets.UTF_16.name());
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
