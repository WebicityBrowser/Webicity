package everyos.browser.webicitybrowser.gui.window;

public class WindowCreationFailureException extends RuntimeException {
	private static final long serialVersionUID = 4696089557623650343L;
	
	public WindowCreationFailureException(Exception e) {
		super(e);
	}
	
	public WindowCreationFailureException(String e) {
		super(e);
	}
}
