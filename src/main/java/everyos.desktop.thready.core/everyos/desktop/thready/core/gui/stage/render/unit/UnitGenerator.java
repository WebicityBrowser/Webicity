package everyos.desktop.thready.core.gui.stage.render.unit;

public interface UnitGenerator {
	
	NextUnitInfo getNextUnitInfo(ContextSwitch[] contextSwitches);
	
	Unit getMergedUnits();

	boolean completed();
	
}
