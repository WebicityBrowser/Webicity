package everyos.browser.webicitybrowser.gui.behaviour;

import java.util.function.Supplier;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.basic.directive.ExternalMouseListenerDirective;
import everyos.desktop.thready.basic.directive.MouseListenerDirective;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.stage.message.MouseConstants;

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