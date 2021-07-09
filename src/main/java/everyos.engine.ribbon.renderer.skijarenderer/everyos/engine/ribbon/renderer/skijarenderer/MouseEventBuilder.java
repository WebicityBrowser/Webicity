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

	public void setAbsoluteCords(int x, int y) {
		this.absX = x;
		this.absY = y;
	}
	
	public void setRelativeCords(int x, int y) {
		this.relX = x;
		this.relY = y;
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
		return new MouseEvent() {
			public int getButton() {
				return button;
			}
			
			public int getAction() {
				return action;
			}
			
			public UIEventTarget getEventTarget() {
				return target;
			}
			
			public boolean isExternal() {
				return isExternal;
			}
			
			public int getAbsoluteX() {
				return absX;
			}
			
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
		};
	}
}
