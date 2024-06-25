package com.github.webicitybrowser.webicitybrowser.gui.window;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.windowing.core.DesktopWindow;

public class DesktopGUIWindow implements GUIWindow {

	private DesktopWindow wrappedWindow;

	public DesktopGUIWindow(DesktopWindow wrappedWindow) {
		this.wrappedWindow = wrappedWindow;
	}

	@Override
	public void addCloseListener(Runnable handler) {
		// TODO
	}

	@Override
	public void minimize() {
		wrappedWindow.minimize();
	}
	
	@Override
	public void restore() {
		wrappedWindow.restore();
	}
	
	@Override
	public void close() {
		wrappedWindow.close();
	}

	@Override
	public void setPosition(AbsolutePosition newWindowPosition) {
		wrappedWindow.setPosition(newWindowPosition);
	}

	@Override
	public AbsolutePosition getPosition() {
		return wrappedWindow.getPosition();
	}

}
