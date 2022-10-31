package everyos.desktop.thready.core.gui.laf.component.composite;

public interface Compositor {
	
	void addChild(CompositeLayer child);

	CompositeLayer createCompositeLayer();
	
}
