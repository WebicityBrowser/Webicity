package everyos.desktop.thready.core.gui.laf;

public class UnsupportedLookAndFeelException extends RuntimeException {
	
	private static final long serialVersionUID = 673723023054667620L;

	public UnsupportedLookAndFeelException() {
		super("Unsupported look and feel");
	}
	
	public UnsupportedLookAndFeelException(String message) {
		super(message);
	}
	
}
