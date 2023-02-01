package everyos.browser.webicitybrowser.gui.window;

public interface GUIWindow {

	void addCloseListener(Runnable handler);

	void minimize();

	void restore();
	
	void close();
	
}
