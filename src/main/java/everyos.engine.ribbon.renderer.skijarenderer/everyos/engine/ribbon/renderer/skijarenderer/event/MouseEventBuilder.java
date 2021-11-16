package everyos.engine.ribbon.renderer.skijarenderer.event;

import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;

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
		final int button = this.button;
		final int action = this.action;
		final UIEventTarget target = this.target;
		final boolean isExternal = this.isExternal;
		final int absX = this.absX;
		final int absY = this.absY;
		final int relX = this.relX;
		final int relY = this.relY;
		final int scrX = this.scrX;
		final int scrY = this.scrY;
		
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
