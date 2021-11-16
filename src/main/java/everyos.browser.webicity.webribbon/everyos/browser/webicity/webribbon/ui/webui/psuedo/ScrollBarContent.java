package everyos.browser.webicity.webribbon.ui.webui.psuedo;

import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class ScrollBarContent implements Content {
	private static final int PADDING_Y = 4;
	private static final int PADDING_X = 2;
	private static final int SCROLLBAR_WIDTH = 8;
	private static final int TRACKBAR_WIDTH = SCROLLBAR_WIDTH + PADDING_X*2;
	
	private final WebComponentUI ui;
	private final Content innerContent;
	
	private BlockLevelBox box;
	private Dimension pageSize;
	
	private Mode mode;
	private int curScrollY = 0;
	private int startScrollY;
	private int startPosY;
	private boolean useScrollBar;
	
	public ScrollBarContent(WebComponentUI ui, Content innerContent) {
		this.ui = ui;
		this.innerContent = innerContent;
	}
	
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		this.box = (BlockLevelBox) box;
		
		SizePosGroup spg = new SizePosGroup(
			sizepos.getSize().getWidth(), 0,
			0, 0,
			sizepos.getSize().getWidth(), -1);
		
		innerContent.render(box, rd, spg, context);
		
		sizepos.setMinLineHeight(spg.getSize().getHeight());
		sizepos.move(spg.getSize().getWidth(), true);
		
		this.pageSize = spg.getSize();
		
		this.useScrollBar =
			sizepos.getSize().getHeight() < pageSize.getHeight() &&
			sizepos.getSize().getHeight() != -1;
		
		if (useScrollBar) {
			//TODO: Avoid rendering twice
			spg = new SizePosGroup(
				sizepos.getSize().getWidth() - TRACKBAR_WIDTH, 0,
				0, 0,
				sizepos.getSize().getWidth() - TRACKBAR_WIDTH, -1);
			
			innerContent.render(box, rd, spg, context);
			
			this.pageSize = spg.getSize();
		}
	}

	public void processEvent(UIEvent event) {
		if (useScrollBar) {
			handleScrollbar(event);
		}
	}

	public int getCurrentScrollY() {
		return this.curScrollY;
	}

	public void paint(Box box_, RendererData rd, Rectangle viewport, WebPaintContext context) {
		paintScrollbar(rd, context);
		
		if (useScrollBar) {
			Dimension size = box.getFinalSize();
			
			RendererData childRD = rd.getSubcontext(
				0, 0,
				size.getWidth() - TRACKBAR_WIDTH, size.getHeight());
			
			childRD.translate(0, -curScrollY);
			
			Rectangle childViewport = new Rectangle(
				0, curScrollY,
				viewport.getWidth(), viewport.getHeight());
			
			context.getRenderer().paintMouseListener(
				rd, ui.getComponent(),
				size.getWidth() - TRACKBAR_WIDTH, 0,
				TRACKBAR_WIDTH, size.getHeight(),
				ev -> processEvent(ev));
			
			Position pos = box.getFinalPos();
			box.setFinalPos(new Position(0, 0));
			innerContent.paint(box, childRD, childViewport, context);
			box.setFinalPos(pos);
		} else {
			innerContent.paint(box, rd, viewport, context);
		}
	}
	
	
	private void handleScrollbar(UIEvent event) {
		Dimension outerSize = box.getFinalSize();
		int maxScrollY = getMaxScrollY();
		
		if (event instanceof MouseEvent) {
			MouseEvent ev = (MouseEvent) event;
			
			if (ev.getAction() == MouseEvent.MOVE) {
				Mode oldMode = mode;
				if (ev.getAction() == MouseEvent.MOVE && !ev.isExternal()) {
					mode = Mode.HOVER;
				} else if (ev.getAction() == MouseEvent.MOVE) {
					mode = Mode.NORMAL;
				}
				if (oldMode != mode) {
					ui.invalidate(InvalidationLevel.PAINT);
				}
				
				return;
			}
			
			if (ev.getAction() == MouseEvent.PRESS && ev.getButton() == MouseEvent.LEFT_BUTTON && !ev.isExternal()) {
				mode = Mode.DRAG;
				this.startScrollY = this.curScrollY;
				this.startPosY = ev.getAbsoluteY();
			} else if (ev.getAction() == MouseEvent.RELEASE && ev.getButton() == MouseEvent.LEFT_BUTTON) {
				mode = Mode.NORMAL;
			} else if (ev.getAction() == MouseEvent.DRAG && mode == Mode.DRAG) {
				this.curScrollY = this.startScrollY + (int) (
					(float) (ev.getAbsoluteY()-this.startPosY) /
					(float) (outerSize.getHeight()) *
					pageSize.getHeight());
				
				if (this.curScrollY < 0) {
					this.curScrollY = 0;
				}
				
				if (this.curScrollY > maxScrollY) {
					this.curScrollY = maxScrollY;
				}
			} else {
				return;
			}
			
			ui.invalidate(InvalidationLevel.PAINT);
		}
	}

	private int getMaxScrollY() {
		return pageSize.getHeight() - box.getFinalSize().getHeight();
	}
	
	private void paintScrollbar(RendererData rd, WebPaintContext context) {
		Dimension outerSize = box.getFinalSize();
		
		if (!useScrollBar) {
			return;
		}
		
		Pallete pallete = context.getPallete();
		
		Color scrollColor =
			mode == Mode.DRAG ? pallete.getAccentSelect() :
			mode == Mode.HOVER ? pallete.getAccentHover() :
			pallete.getAccent();
		
		GUIState state = rd.getState();
		rd.restoreState(state.clone());
		
		rd.getState().setForeground(scrollColor);
		rd.useForeground();
		
		int trackSize = outerSize.getHeight() - SCROLLBAR_WIDTH - PADDING_Y*2;
		int height = (int) (((double) outerSize.getHeight()/(double) pageSize.getHeight())*trackSize);
		int posY = (int) (((double) this.curScrollY/(double) pageSize.getHeight())*trackSize) + PADDING_Y;
		int posX = outerSize.getWidth() - SCROLLBAR_WIDTH - PADDING_X;
		
		Renderer r = context.getRenderer();
		
		r.drawEllipse(
			rd, posX, posY,
			SCROLLBAR_WIDTH, SCROLLBAR_WIDTH);
		r.drawFilledRect(
			rd, posX, posY + SCROLLBAR_WIDTH/2,
			SCROLLBAR_WIDTH, height + SCROLLBAR_WIDTH/2);
		r.drawEllipse(
			rd, posX, posY + SCROLLBAR_WIDTH/2 + height,
			SCROLLBAR_WIDTH, SCROLLBAR_WIDTH);
		
		rd.restoreState(state);
	}

	private enum Mode {
		NORMAL, HOVER, DRAG
	}

	@Override
	public Content split() {
		return new ScrollBarContent(ui, innerContent.split());
	}
	
}