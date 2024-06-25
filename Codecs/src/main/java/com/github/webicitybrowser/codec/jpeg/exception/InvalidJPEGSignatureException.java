package com.github.webicitybrowser.codec.jpeg.exception;

public class InvalidJPEGSignatureException extends MalformedJPEGException {
	
	public InvalidJPEGSignatureException() {
		super("Invalid JPEG signature");
	}

	public InvalidJPEGSignatureException(String string) {
		super("Invalid JPEG signature: " + string);
	}

}
