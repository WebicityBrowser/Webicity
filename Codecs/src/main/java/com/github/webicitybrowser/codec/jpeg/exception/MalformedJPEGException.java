package com.github.webicitybrowser.codec.jpeg.exception;

public class MalformedJPEGException extends Exception {
	
	public MalformedJPEGException(String message) {
		super(message);
	}
	
	public MalformedJPEGException() {
		super("Attempted to decode malformed JPEG datastream!");
	}
	
}
