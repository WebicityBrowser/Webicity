package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.engine.ribbon.core.event.CharEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.input.keyboard.Key;
import everyos.engine.ribbon.core.input.keyboard.KeyboardEvent;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class URLBarUI extends SimpleBlockComponentUI {
	
	private final Appearence appearence;

	public URLBarUI(Component c, ComponentUI parent) {
		super(c, parent);
		
		this.appearence = new URLBarAppearence();
	}
	
	@Override
	protected Appearence getAppearence() {
		return this.appearence;
	}
	
	private class URLBarAppearence implements Appearence {
		private String text = "";
		
		private boolean active = true;
		
		private long lastCursorOn = System.currentTimeMillis();

		private Dimension bounds;

		private int computedCursorOffset = 0;
		private int currentCursorPosition = 0;
		private RendererData lastRenderer;
		
		private String textCache = "";
		
		@Override
		public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
			this.text = getComponent().<URLBar>casted().getText();
			
			if (!textCache.equals(text)) {
				textCache = text;
				
				if (currentCursorPosition > text.length()) {
					currentCursorPosition = text.length();
				}
				
				RibbonFontMetrics font = rd.getState().getFont();
				computedCursorOffset = StringWrapHelper.stringWidth(font, text.substring(0, currentCursorPosition));		
			}
			
			RibbonFontMetrics font = rd.getState().getFont();
			
			int width = StringWrapHelper.stringWidth(font, text);
			sizepos.move(width, true);
			sizepos.setMinLineHeight(font.getHeight());
			
			this.bounds = sizepos.getSize();
		}

		@Override
		public void paint(RendererData rd, PaintContext context) {
			this.lastRenderer = rd;
			
			Renderer r = context.getRenderer();
			
			rd.useBackground();
			r.drawEllipse(rd, 0, 0, bounds.getHeight(), bounds.getHeight());
			r.drawEllipse(rd, bounds.getWidth()-bounds.getHeight(), 0, bounds.getHeight(), bounds.getHeight());
			r.drawFilledRect(rd, bounds.getHeight()/2, 0, bounds.getWidth()-bounds.getHeight(), bounds.getHeight());
			
			//TODO: This is just temporary so I don't forget. Change URL bar UI to accept a URL instead
			// of raw text, and then we can perform the check on only the domain name, and only if the user
			// has not typed
			if (!text.matches("[a-z]*://[A-Za-z0-9-_.]+(/.*)?")) {
				rd.getState().setForeground(Color.RED);
				
				int diameter = bounds.getHeight() / 2;
				rd.useForeground();
				r.drawEllipse(rd, diameter/2, diameter/2, diameter, diameter);
				
				rd.getState().setForeground(Color.WHITE);
				rd.useForeground();
				r.drawText(rd, diameter, diameter/2, "!");
				
				rd.getState().setForeground(Color.RED);
			}
			
			rd.useForeground();
			r.drawText(rd, bounds.getHeight(), bounds.getHeight()/2-rd.getState().getFont().getHeight()/2, text);
			
			paintAnimation(r, rd, bounds);
			
			r.paintListener(e->processEvent(e));
		}
		
		private void paintAnimation(Renderer r, RendererData rd, Dimension bounds) {
			if (active) {
				if (System.currentTimeMillis()-lastCursorOn>1000) {
					lastCursorOn = System.currentTimeMillis();
				}
				if (System.currentTimeMillis()-lastCursorOn<500) {
					r.drawLine(rd, bounds.getHeight() + computedCursorOffset , bounds.getHeight()/4, 0, bounds.getHeight()/2);
				}
			}
		}

		@Override
		public void directive(UIDirective directive) {
			
		}

		@Override
		public void processEvent(UIEvent e) {
			RibbonFontMetrics font = lastRenderer.getState().getFont();
			
			if (e instanceof MouseEvent) {
				MouseEvent ev = (MouseEvent) e;
				if (ev.isExternal()) {
					return;
				}
				
				if (ev.getAction() == MouseEvent.PRESS && ev.getButton() == MouseEvent.LEFT_BUTTON) {
					int mouseX = ev.getRelativeX() - bounds.getHeight();
					
					if (mouseX < 0) {
						mouseX = 0;
					}
					
					int currentMouseOffset = 0;
					int i;
					for (i=0; i<text.length(); i++) {
						int charWidth = font.getCharWidth(text.codePointAt(i));
						if (currentMouseOffset + charWidth*.5 > mouseX) {
							break;
						}
						currentMouseOffset += charWidth;
					}
					
					this.computedCursorOffset = currentMouseOffset;
					this.currentCursorPosition = i;
				}
			} else if (e instanceof CharEvent) {
				String insertion = ((CharEvent) e).getChar();
				this.text = text.substring(0, currentCursorPosition) + insertion + text.substring(currentCursorPosition);
				currentCursorPosition++;
				computedCursorOffset += font.getCharWidth(insertion.codePointAt(0));
			} else if (e instanceof KeyboardEvent) {
				KeyboardEvent ev = (KeyboardEvent) e;
				if (ev.getKey() == Key.BACKSPACE && (ev.getAction() == KeyboardEvent.KEY_PRESS || ev.getAction() == KeyboardEvent.KEY_HOLD)) {
					if (currentCursorPosition == 0) {
						return;
					}
					computedCursorOffset -= font.getCharWidth(text.codePointAt(currentCursorPosition-1));
					this.text = text.substring(0, currentCursorPosition-1) + text.substring(currentCursorPosition);
					currentCursorPosition--;	
				} else if (ev.getKey() == Key.ENTER && ev.getAction() == KeyboardEvent.KEY_RELEASE) {
					getComponent().<URLBar>casted().getAction().accept(text);
				}
			} else {
				return;
			}
			
			invalidate(InvalidationLevel.PAINT);
		}
	}
}
