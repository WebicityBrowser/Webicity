package everyos.engine.ribbon.renderer.guirenderer.event;

import everyos.engine.ribbon.core.component.Component;

public class MouseEvent {
	public static final int LEFT_BUTTON = 1;
	public static final int RIGHT_BUTTON = 2;
	
	public static final int PRESS = 1;
	public static final int RELEASE = 2;
	public static final int DRAG = 3;
	public static final int MOVE = 4;
	
	private int button;
	private int action;
	private Component component;
	private boolean isExternal;

	public MouseEvent(Component component, int x, int y, int button, int action, boolean isInternal) {
		this.button = button;
		this.action = action;
		this.component = component;
		this.isExternal = !isInternal;
	}
	
	public int getButton() {
		return button;
	}
	public int getAction() {
		return this.action;
	}
	public Component getComponent() {
		return this.component;
	}
	public boolean isExternal() {
		return this.isExternal;
	}
}
