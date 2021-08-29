package everyos.engine.ribbon.renderer.skijarenderer;

import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;

public class MouseEventBuilder {
	
	private int button;
	private int action;
	private UIEventTarget target;
	private boolean isExternal;
	private int absX, absY;
	private int relX, relY;
	private int scrX, scrY;

	public void setAbsoluteCords(int x, int y) {
		this.absX = x;
		this.absY = y;
	}
	
	public void setRelativeCords(int x, int y) {
		this.relX = x;
		this.relY = y;
	}
	
	public void setScreenCords(int x, int y) {
		this.scrX = x;
		this.scrY = y;
	}

	public void setButton(int button) {
		this.button = button;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setEventTarget(UIEventTarget eventTarget) {
		this.target = eventTarget;
	}

	public void setInternal(boolean b) {
		this.isExternal = !b;
	}

	public MouseEvent build() {
		int button = this.button;
		int action = this.action;
		UIEventTarget target = this.target;
		boolean isExternal = this.isExternal;
		int absX = this.absX;
		int absY = this.absY;
		int relX = this.relX;
		int relY = this.relY;
		int scrX = this.scrX;
		int scrY = this.scrY;
		
		return new MouseEvent() {
			@Override
			public int getButton() {
				return button;
			}
			
			@Override
			public int getAction() {
				return action;
			}
			
			@Override
			public UIEventTarget getEventTarget() {
				return target;
			}
			
			@Override
			public boolean isExternal() {
				return isExternal;
			}
			
			@Override
			public int getAbsoluteX() {
				return absX;
			}
			
			@Override
			public int getAbsoluteY() {
				return absY;
			}

			@Override
			public int getRelativeX() {
				return relX;
			}

			@Override
			public int getRelativeY() {
				return relY;
			}

			@Override
			public int getScreenX() {
				return scrX;
			}

			@Override
			public int getScreenY() {
				return scrY;
			}
		};
	}
}
