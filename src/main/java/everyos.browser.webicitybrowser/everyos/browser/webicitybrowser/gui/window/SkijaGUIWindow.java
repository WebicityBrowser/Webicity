package everyos.browser.webicitybrowser.gui.window;

import com.github.webicity.lace.backend.skija.SkijaWindow;
import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.laf.LookAndFeel;
import com.github.webicity.lace.core.laf.UnsupportedLookAndFeelException;

public class SkijaGUIWindow implements GUIWindow {
	
	private SkijaWindow wrappedWindow;

	public SkijaGUIWindow(SkijaWindow wrappedWindow) {
		this.wrappedWindow = wrappedWindow;
	}

	@Override
	public void setRootComponent(Component component) {
		wrappedWindow.setRootComponent(component);
	}
	
	@Override
	public void setLookAndFeel(LookAndFeel laf) {
		try {
			wrappedWindow.setLookAndFeel(laf);
		} catch (UnsupportedLookAndFeelException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addCloseListener(Runnable handler) {
		wrappedWindow.addShutdownListener(handler);
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

}
