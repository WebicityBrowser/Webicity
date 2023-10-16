package com.github.webicitybrowser.webicity.core.renderer;

import java.util.Optional;

public interface RendererHandle {

	Optional<RendererBackend> getRenderer();
	
	Optional<RendererCrashReason> getCrashReason();

	void crash(RendererCrashReason crashReason);

	void tick();
	
}
