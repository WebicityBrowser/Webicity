package everyos.browser.webicitybrowser.gui.window;

import com.github.anythingide.lace.backend.skija.SkijaWindow;
import com.github.anythingide.lace.core.component.Component;
import com.github.anythingide.lace.core.laf.LookAndFeel;
import com.github.anythingide.lace.core.laf.UnsupportedLookAndFeelException;

public class SkijaGUIWindow implements GUIWindow {
	
	private SkijaWindow wrappedWindow;

	public SkijaGUIWindow(SkijaWindow wrappedWindow) {
		this.wrappedWindow = wrappedWindow;
	}

	@Override
	public void setRootComponent(Component component) {
		wrappedWindow.setComponent(component);
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

}
