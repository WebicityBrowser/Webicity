package everyos.browser.webicitybrowser.gui.window;

import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.renderer.skija.SkijaWindow;

public class SkijaGUIWindow implements GUIWindow {

	private SkijaWindow wrappedWindow;

	public SkijaGUIWindow(SkijaWindow wrappedWindow) {
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
