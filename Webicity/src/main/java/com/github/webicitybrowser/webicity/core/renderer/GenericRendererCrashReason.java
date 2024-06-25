package com.github.webicitybrowser.webicity.core.renderer;

public class GenericRendererCrashReason implements RendererCrashReason {

	private final String title;

	public GenericRendererCrashReason(String title) {
		this.title = title;
	}
	
	@Override
	public String getTitle() {
		return this.title;
	}

}
