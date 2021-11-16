package everyos.engine.ribbon.core.graphics;

import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.graphics.paintfill.PaintFill;

public class GUIState implements Cloneable {
	
	private PaintFill foreground = Color.BLACK;
	private PaintFill background = Color.WHITE;
	private RibbonFontMetrics font = null;
	
	public GUIState() {}
	
	public void setForeground(PaintFill foreground) {
		this.foreground = foreground;
	}
	
	public PaintFill getForeground() {
		return foreground;
	};
	
	public void setBackground(PaintFill background) {
		this.background = background;
	}
	
	public PaintFill getBackground() {
		return background;
	};
	
	public void setFont(RibbonFontMetrics font) {
		this.font = font;
	}
	
	public RibbonFontMetrics getFont() {
		return font;
	}
	
	public GUIState clone() {
		GUIState clone = new GUIState();
		
		clone.setBackground(this.getBackground());
		clone.setForeground(this.getForeground());
		clone.setFont(this.getFont());
		
		return clone;
	}
	
}
