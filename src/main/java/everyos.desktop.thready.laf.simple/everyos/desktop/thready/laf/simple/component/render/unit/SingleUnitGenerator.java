package everyos.desktop.thready.laf.simple.component.render.unit;

import everyos.desktop.thready.core.gui.stage.render.unit.ContextSwitch;
import everyos.desktop.thready.core.gui.stage.render.unit.NextUnitInfo;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.gui.stage.render.unit.UnitGenerator;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class SingleUnitGenerator implements UnitGenerator {
	
	private final Unit unit;
	
	private boolean includeUnit = false;
	private boolean isCompleted = false;

	public SingleUnitGenerator(Unit unit) {
		this.unit = unit;
	}
	
	@Override
	public NextUnitInfo getNextUnitInfo(ContextSwitch[] contextSwitches) {
		return new SingleNextUnitInfo();
	}

	@Override
	public Unit getMergedUnits() {
		if (includeUnit) {
			return unit;
		} else {
			return new EmptyUnit();
		}
	}

	private class SingleNextUnitInfo implements NextUnitInfo {

		@Override
		public void append() {
			ensureNotCompleted();
			
			includeUnit = true;
			isCompleted = true;
		}

		@Override
		public AbsoluteSize sizeAfterAppend() {
			ensureNotCompleted();
			
			return unit.getMinimumSize();
		}

		@Override
		public void skip() {
			ensureNotCompleted();
			
			isCompleted = true;
		}

		@Override
		public boolean completed() {
			return isCompleted;
		}
		
		protected void ensureNotCompleted() {
			if (isCompleted) {
				throw new IllegalStateException("This generator has already completed!");
			}
		}
		
	}
	
}
