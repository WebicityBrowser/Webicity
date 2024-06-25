package com.github.webicitybrowser.webicity.core.renderer;

public class ExceptionRendererCrashReason implements RendererCrashReason {

	private final Exception exception;

	public ExceptionRendererCrashReason(Exception exception) {
		this.exception = exception;
	}

	@Override
	public String getTitle() {
		return "EXCEPTION_OCCURED";
	}
	
	public Exception getException() {
		return this.exception;
	}

}
