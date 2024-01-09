package com.github.webicitybrowser.codec.png.exception;

public class UnsupportedPNGException extends MalformedPNGException {
	
	public UnsupportedPNGException() {
		super("This PNG file uses unsupported features!");
	}

}
