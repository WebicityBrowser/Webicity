package everyos.browser.webicitybrowser.gui.window;

import com.github.anythingide.lace.core.component.Component;
import com.github.anythingide.lace.core.laf.LookAndFeel;

public interface GUIWindow {

	void setRootComponent(Component component);
	
	void setLookAndFeel(LookAndFeel laf);

	void addCloseListener(Runnable handler);
	
}
