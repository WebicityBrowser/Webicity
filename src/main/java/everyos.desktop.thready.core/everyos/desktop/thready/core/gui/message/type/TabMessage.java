package everyos.desktop.thready.core.gui.message.type;

public interface TabMessage {

	TabDirection getDirection();
	
	boolean hasFoundCurrentComponent();
	
	boolean hasFoundNextComponent();
	
	public static enum TabDirection {
		BACKWARD, FORWARD
	}
	
}
