package com.github.webicitybrowser.codec.png.exception;

public class MalformedPNGException extends Exception {

	public MalformedPNGException(String message) {
		super(message);
	}
	
	public MalformedPNGException() {
		super("Attempted to decode malformed PNG datastream!");
	}

}
