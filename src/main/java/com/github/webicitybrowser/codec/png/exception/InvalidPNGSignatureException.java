package com.github.webicitybrowser.codec.png.exception;

public class InvalidPNGSignatureException extends MalformedPNGException {
	
	public InvalidPNGSignatureException() {
		super("Invalid PNG signature");
	}

}
