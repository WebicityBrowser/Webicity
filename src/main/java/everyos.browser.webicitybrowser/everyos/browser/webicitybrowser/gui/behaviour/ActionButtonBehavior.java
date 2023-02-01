package everyos.browser.webicitybrowser.gui.behaviour;

import java.util.function.Supplier;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.gui.component.Component;

public final class ActionButtonBehavior {
	
	private ActionButtonBehavior() {}
	
	public static void configure(Component component, Runnable handler,
		ColorFormat defaultColor, ColorFormat hoverColor, ColorFormat selectedColor,
		ColorFormat activeColor, Supplier<Boolean> activeChecker) {
		
		component.directive(BackgroundColorDirective.of(activeChecker.get() ? activeColor : defaultColor));
		
		/*component.directive(MouseListenerDirective.of(e -> {
			if (e.getAction() == MouseEvent.MOVE || e.getButton() != MouseEvent.LEFT_BUTTON) {
				component.directive(BackgroundDirective.of(hoverColor));
			} else if (e.getAction() == MouseEvent.PRESS || e.getAction() == MouseEvent.DRAG) {
				component.directive(BackgroundDirective.of(selectedColor));
			} else if (e.getAction() == MouseEvent.RELEASE) {
				if (handler != null) {
					handler.run();
				}
				component.directive(BackgroundDirective.of(hoverColor));
			}
		}));
		
		component.directive(ExternalMouseListenerDirective.of(e -> {
			component.directive(BackgroundDirective.of(activeChecker.get() ? activeColor : defaultColor));
		}));*/
	}
	
}