package everyos.browser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.input.mouse.ExternalMouseListenerDirective;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.input.mouse.MouseListenerDirective;

public final class ActionButtonBehavior {
	private ActionButtonBehavior() {}
	
	public static void configure(Component component, Runnable handler,
		Color defaultColor, Color hoverColor, Color selectedColor,
		Color activeColor, Supplier<Boolean> activeChecker) {
		
		component
			.directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor))
			.directive(MouseListenerDirective.of(e->{
				if (e.getAction()==MouseEvent.MOVE || e.getButton() != MouseEvent.LEFT_BUTTON) {
					((Component) e.getEventTarget()).directive(BackgroundDirective.of(hoverColor));
				} else if (e.getAction() == MouseEvent.PRESS || e.getAction() == MouseEvent.DRAG) {
					((Component) e.getEventTarget()).directive(BackgroundDirective.of(selectedColor));
				} else if (e.getAction() == MouseEvent.RELEASE) {
					if (handler!=null) {
						handler.run();
					}
					((Component) e.getEventTarget()).directive(BackgroundDirective.of(hoverColor));
				}
			}))
			.directive(ExternalMouseListenerDirective.of(e->{
				((Component) e.getEventTarget()).directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor));
			}));
	}
}
