package com.github.webicitybrowser.thready.windowing.core;

import java.util.function.Consumer;

import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;

/**
 * A graphics system allows access to windowing and
 * graphics.
 */
public interface GraphicsSystem {

	/**
	 * Create a new window, which will be passed to the callback
	 * on the graphics thread. The window is not visible by default.
	 * @param callback The function to invoke upon creation of the window.
	 */
	void createWindow(Consumer<Window> callback);
	
	/**
	 * Get this graphic system's resource loader.
	 * The resource loader allows for loading of heavy-weight
	 * UI elements.
	 * @return This graphic system's resource loader.
	 */
	ResourceLoader getResourceLoader();

	/**
	 * Start this graphics system's render loop.
	 * This method may block until all windows are closed.
	 */
	void startRenderLoop();
	
}
