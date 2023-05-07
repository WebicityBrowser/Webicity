package com.github.webicitybrowser.thready.windowing.skija.imp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;

public class SkijaInvalidationScheduler implements InvalidationScheduler {
	
	private final List<ScheduledInvalidation> scheduledInvalidations = new LinkedList<>();
	
	@Override
	public void scheduleInvalidationInMillis(long millis, ComponentUI ui, InvalidationLevel level) {
		scheduleInvalidationAtSystemMillis(System.currentTimeMillis() + millis, ui, level);
	}

	@Override
	public void scheduleInvalidationAtSystemMillis(long millis, ComponentUI ui, InvalidationLevel level) {
		scheduledInvalidations.add(new ScheduledInvalidation(millis, ui, level));
	}

	@Override
	public void scheduleInvalidationForNextFrame(ComponentUI ui, InvalidationLevel level) {
		scheduleInvalidationAtSystemMillis(0, ui, level);
	}
	
	public void tick() {
		// TODO: What happens if system time rolls over to negative value?
		List<ScheduledInvalidation> passedInvalidations = seperatePassedInvalidations();
		for (ScheduledInvalidation invalidation: passedInvalidations) {
			invalidation.ui().invalidate(invalidation.level());
		}
	}
	
	private List<ScheduledInvalidation> seperatePassedInvalidations() {
		List<ScheduledInvalidation> passedInvalidations = new ArrayList<>();
		for (int i = 0; i < scheduledInvalidations.size(); i++) {
			ScheduledInvalidation invalidation = scheduledInvalidations.get(i);
			if (System.currentTimeMillis() > invalidation.systemMillis()) {
				continue;
			}
			
			scheduledInvalidations.remove(invalidation);
			passedInvalidations.add(invalidation);
			i--;
		}
		
		return passedInvalidations;
	}

	private record ScheduledInvalidation(long systemMillis, ComponentUI ui, InvalidationLevel level) {}

}
