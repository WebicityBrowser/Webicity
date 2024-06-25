package com.github.webicitybrowser.webicity.core.renderer;

public class RendererCrashException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final RendererCrashReason reason;
	
	public RendererCrashException(RendererCrashReason reason) {
		this.reason = reason;
	}

	public RendererCrashReason getReason() {
		return this.reason;
	};
	
}
