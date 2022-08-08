package everyos.browser.webicitybrowser.gui.window;

import com.github.webicity.lace.core.component.Component;
import com.github.webicity.lace.core.laf.LookAndFeel;

public interface GUIWindow {

	void setRootComponent(Component component);
	
	void setLookAndFeel(LookAndFeel laf);

	void addCloseListener(Runnable handler);

	void minimize();

	void restore();
	
	void close();
	
}
