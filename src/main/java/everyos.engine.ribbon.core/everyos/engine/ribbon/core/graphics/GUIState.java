package everyos.engine.ribbon.core.graphics;

import everyos.engine.ribbon.core.rendering.RibbonFont;

public class GUIState implements Cloneable {
	private Color foreground = Color.BLACK;
	private Color background = Color.WHITE;
	private RibbonFont font = null;
	
	public GUIState() {}
	
	public void setForeground(Color foreground) {
		this.foreground = foreground;
	}
	
	public Color getForeground() {
		return foreground;
	};
	
	public void setBackground(Color background) {
		this.background = background;
	}
	
	public Color getBackground() {
		return background;
	};
	
	public void setFont(RibbonFont font) {
		this.font = font;
	}
	
	public RibbonFont getFont() {
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
