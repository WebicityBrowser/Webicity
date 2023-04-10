package com.github.webicitybrowser.thready.windowing.core;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface DesktopWindow extends Window {

	/**
	 * Set whether this window is visible to the user or not.
	 * @param visible A boolean indicating if the window is
	 *  visible to the user.
	 */
	void setVisible(boolean visible);
	
	/**
	 * Get the current size of this window.
	 * @return The current size of this window.
	 */
	AbsoluteSize getSize();
	
}
