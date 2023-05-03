package com.github.webicitybrowser.thready.windowing.core;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
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
	
	/**
	 * Minimizes the window.
	 */
	void minimize();
	
	/**
	 * Show the window.
	 */
	void restore();
	
	/**
	 * Move the window to the specified position.
	 * @param position The position to move the window to.
	 */
	void setPosition(AbsolutePosition position);
	
	/**
	 * Get the position of the window on the screen.
	 * from the top-left corner.
	 * @return The position of the window.
	 */
	AbsolutePosition getPosition();

	/**
	 * Set the title of this window.
	 * @param title The title of this window.
	 */
	void setTitle(String title);

	/**
	 * Set whether this window uses native decorations.
	 * @param decorated A boolean indicating if this window
	 *  uses native decorations.
	 */
	void setDecorated(boolean decorated);
	
}
