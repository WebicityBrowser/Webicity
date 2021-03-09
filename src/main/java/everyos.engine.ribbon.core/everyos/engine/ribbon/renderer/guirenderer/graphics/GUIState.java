package everyos.engine.ribbon.renderer.guirenderer.graphics;

public class GUIState {
	private Color foreground = Color.BLACK;
	private Color background = Color.WHITE;

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
	
	public GUIState clone() {
		GUIState clone = new GUIState();
		
		clone.setBackground(this.getBackground());
		clone.setForeground(this.getForeground());
		
		return clone;
	}
}
