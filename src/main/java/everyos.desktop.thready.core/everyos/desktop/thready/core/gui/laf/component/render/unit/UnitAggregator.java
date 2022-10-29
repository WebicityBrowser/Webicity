package everyos.desktop.thready.core.gui.laf.component.render.unit;

public interface UnitAggregator {

	boolean accept(Unit unit);
	
	Unit combine();
	
}
