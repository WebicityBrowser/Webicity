package com.github.webicitybrowser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.ExternalMouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.MouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public final class ActionButtonBehavior {
	
	private ActionButtonBehavior() {}
	
	public static void configure(Component component, Runnable handler,
		ColorFormat defaultColor, ColorFormat hoverColor, ColorFormat selectedColor,
		ColorFormat activeColor, Supplier<Boolean> activeChecker) {
		
		component.directive(BackgroundColorDirective.of(activeChecker.get() ? activeColor : defaultColor));
		
		// TODO: Buttons may be wrong color after re-render
		component.directive(MouseListenerDirective.of(e -> {
			if (e.getAction() == MouseConstants.MOVE || e.getButton() != MouseConstants.LEFT_BUTTON) {
				component.directive(BackgroundColorDirective.of(hoverColor));
			} else if (e.getAction() == MouseConstants.PRESS || e.getAction() == MouseConstants.DRAG) {
				component.directive(BackgroundColorDirective.of(selectedColor));
			} else if (e.getAction() == MouseConstants.RELEASE) {
				if (handler != null) {
					handler.run();
				}
				component.directive(BackgroundColorDirective.of(hoverColor));
			}
		}));
		
		component.directive(ExternalMouseListenerDirective.of(e -> {
			component.directive(BackgroundColorDirective.of(activeChecker.get() ? activeColor : defaultColor));
		}));
	}
	
}