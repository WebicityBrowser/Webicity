package everyos.desktop.thready.core.gui.stage.composite;

public interface Compositor {
	
	void addChild(CompositeLayer child);

	CompositeLayer createCompositeLayer();
	
}
