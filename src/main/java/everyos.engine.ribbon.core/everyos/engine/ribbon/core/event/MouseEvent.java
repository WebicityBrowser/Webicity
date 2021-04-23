package everyos.engine.ribbon.core.event;

public class MouseEvent implements UIEvent {
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;
	
	public static final int PRESS = 1;
	public static final int RELEASE = 2;
	public static final int DRAG = 3;
	public static final int MOVE = 4;
	
	private int button;
	private int action;
	private UIEventTarget target;
	private boolean isExternal;

	public MouseEvent(UIEventTarget target, int x, int y, int button, int action, boolean isInternal) {
		this.button = button;
		this.action = action;
		this.target = target;
		this.isExternal = !isInternal;
	}
	
	public int getButton() {
		return button;
	}
	
	public int getAction() {
		return this.action;
	}
	
	public UIEventTarget getEventTarget() {
		return this.target;
	}
	
	public boolean isExternal() {
		return this.isExternal;
	}
}
