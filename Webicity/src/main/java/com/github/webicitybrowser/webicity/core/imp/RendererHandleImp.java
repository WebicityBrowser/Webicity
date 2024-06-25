package com.github.webicitybrowser.webicity.core.imp;

import java.util.Optional;

import com.github.webicitybrowser.webicity.core.renderer.RendererBackend;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;

public class RendererHandleImp implements RendererHandle {
	
	private Optional<RendererBackend> renderer;
	private Optional<RendererCrashReason> crashReason;

	public RendererHandleImp(RendererBackend renderer) {
		this.renderer = Optional.of(renderer);
		this.crashReason = Optional.empty();
	}
	
	public RendererHandleImp(RendererCrashReason crashReason) {
		crash(crashReason);
	}

	@Override
	public Optional<RendererBackend> getRenderer() {
		return this.renderer;
	}

	@Override
	public Optional<RendererCrashReason> getCrashReason() {
		return this.crashReason;
	}
	
	@Override
	public void crash(RendererCrashReason crashReason) {
		this.renderer = Optional.empty();
		this.crashReason = Optional.of(crashReason);
	}

	@Override
	public void tick() {
		this.renderer.ifPresent(RendererBackend::tick);
	}

	public static RendererHandle of(RendererBackend renderer) {
		return new RendererHandleImp(renderer);
	}
	
	public static RendererHandle fail(RendererCrashReason crashReason) {
		return new RendererHandleImp(crashReason);
	}

}
