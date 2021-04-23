package everyos.browser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.directive.ExternalMouseListenerDirective;
import everyos.engine.ribbon.core.directive.MouseListenerDirective;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.graphics.Color;

public final class ActionButtonBehavior {
	private ActionButtonBehavior() {}
	
	public static void configure(Component component, Runnable handler,
		Color defaultColor, Color hoverColor, Color selectedColor,
		Color activeColor, Supplier<Boolean> activeChecker) {
		
		component
			.directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor))
			.directive(MouseListenerDirective.of(e->{
				if (e.getAction()==MouseEvent.MOVE) {
					((Component) e.getEventTarget()).directive(BackgroundDirective.of(hoverColor));
				}
				//TODO: These elements should only respond to the left mouse button, but the drag handler
				// wasn't working when I tried to do that.
				if (e.getAction()==MouseEvent.PRESS||e.getAction()==MouseEvent.DRAG) {
					((Component) e.getEventTarget()).directive(BackgroundDirective.of(selectedColor));
				}
				if (e.getButton()!=MouseEvent.LEFT_BUTTON) return;
				if (e.getAction()==MouseEvent.RELEASE) {
					if (handler!=null) handler.run();
					((Component) e.getEventTarget()).directive(BackgroundDirective.of(hoverColor));
				}
			}))
			.directive(ExternalMouseListenerDirective.of(e->{
				((Component) e.getEventTarget()).directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor));
			}));
	}
}
