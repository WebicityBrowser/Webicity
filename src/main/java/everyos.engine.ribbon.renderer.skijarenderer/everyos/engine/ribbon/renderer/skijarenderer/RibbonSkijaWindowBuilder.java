package everyos.engine.ribbon.renderer.skijarenderer;

public class RibbonSkijaWindowBuilder {

	private final int id;
	
	private boolean decorated = false;

	public RibbonSkijaWindowBuilder(int id) {
		this.id = id;
	}

	public void setDecorated(boolean decorated) {
		this.decorated = decorated;
	}

	public RibbonSkijaWindow build() {
		return new RibbonSkijaWindow(id, decorated);
	}
	
}
