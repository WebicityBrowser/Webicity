package everyos.engine.ribbon.event;

public class MouseEvent {
	public static final int LEFT_BUTTON = 1;
	
	private int button;

	public MouseEvent(int x, int y, int button) {
		this.button = button;
	}
	
	public int getButton() {
		return button;
	}
	
}
