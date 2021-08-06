package everyos.browser.webicity.webribbon.ui.webui.psuedo;

import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.shape.Position;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Rectangle;

public class ScrollBar {
	private WebComponentUI ui;
	
	private int curScrollY = 0;
	private int startScrollY;
	private int startPosY;
	
	private Dimension outerSize;
	private Dimension pageSize;
	private Position position;
	private Mode mode;
	
	public ScrollBar(WebComponentUI ui) {
		this.ui = ui;
	}
	
	public void render(Position position, Dimension outerSize, Dimension pageSize) {
		this.outerSize = outerSize;
		this.pageSize = pageSize;
		this.position = position;
	}

	public void processEvent(UIEvent event) {
		int maxScrollY = getMaxScrollY();
		
		if (event instanceof MouseEvent) {
			MouseEvent ev = (MouseEvent) event;
			
			if (ev.getAction() == MouseEvent.MOVE) {
				Mode oldMode = mode;
				if (ev.getAction()==MouseEvent.MOVE && !ev.isExternal()) {
					mode = Mode.HOVER;
				} else if (ev.getAction()==MouseEvent.MOVE) {
					mode = Mode.NORMAL;
				}
				if (oldMode != mode) {
					ui.invalidate(InvalidationLevel.PAINT);
				}
				
				return;
			}
			
			if (ev.getAction()==MouseEvent.PRESS && ev.getButton()==MouseEvent.LEFT_BUTTON && !ev.isExternal()) {
				mode = Mode.DRAG;
				this.startScrollY = this.curScrollY;
				this.startPosY = ev.getAbsoluteY();
			} else if (ev.getAction()==MouseEvent.RELEASE && ev.getButton()==MouseEvent.LEFT_BUTTON) {
				mode = Mode.NORMAL;
			} else if (ev.getAction()==MouseEvent.DRAG && mode == Mode.DRAG) {
				this.curScrollY = this.startScrollY + (int) (
					(float) (ev.getAbsoluteY()-this.startPosY)/
					(float) (outerSize.getHeight()) *
					pageSize.getHeight()
					);
				if (this.curScrollY<0) {
					this.curScrollY = 0;
				}
				if (this.curScrollY>maxScrollY) {
					this.curScrollY = maxScrollY;
				}
			} else {
				return;
			}
			
			ui.invalidate(InvalidationLevel.PAINT);
		}
	}
	
	private int getMaxScrollY() {
		return pageSize.getHeight()-outerSize.getHeight();
	}

	public int getCurrentScrollY() {
		return this.curScrollY;
	}

	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		if (getMaxScrollY()>0) {
			Pallete pallete = context.getPallete();
			
			Color scrollColor =
				mode == Mode.DRAG ? pallete.getAccentSelect() :
				mode == Mode.HOVER ? pallete.getAccentHover() :
				pallete.getAccent();
			
			GUIState state = rd.getState();
			rd.restoreState(state.clone());
			
			rd.getState().setForeground(scrollColor);
			rd.useForeground();
			
			int width = 8;
			int height = (int) (((double)outerSize.getHeight()/(double) pageSize.getHeight())*outerSize.getHeight()-width);
			int posY = (int) (((double)this.curScrollY/(double) pageSize.getHeight())*(outerSize.getHeight()-width));
			
			Renderer r = context.getRenderer();
			
			r.drawEllipse(
				rd, position.getX()+outerSize.getWidth()+1, posY,
				width, width);
			r.drawFilledRect(
				rd, position.getX()+outerSize.getWidth()+1, posY+width/2,
				width, height+width/2);
			r.drawEllipse(
				rd, position.getX()+outerSize.getWidth()+1, posY+width/2+height,
				width, width);
			
			rd.restoreState(state);
		}
	}
	
	private enum Mode {
		NORMAL, HOVER, DRAG
	}
}
