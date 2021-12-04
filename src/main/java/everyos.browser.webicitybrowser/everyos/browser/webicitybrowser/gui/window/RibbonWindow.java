package everyos.browser.webicitybrowser.gui.window;

import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.shape.Position;

public interface RibbonWindow {
	
	Component getDisplayPane();
	void close();
	void minimize();
	void restore();
	Position getPosition();
	void setPosition(int x, int y);
	
}
