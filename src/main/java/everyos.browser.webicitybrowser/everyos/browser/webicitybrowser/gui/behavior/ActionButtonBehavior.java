package everyos.browser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import com.github.anythingide.lace.basics.component.directive.BackgroundDirective;
import com.github.anythingide.lace.basics.component.directive.ExternalMouseListenerDirective;
import com.github.anythingide.lace.basics.component.directive.MouseListenerDirective;
import com.github.anythingide.lace.basics.component.event.MouseEvent;
import com.github.anythingide.lace.core.color.Color;
import com.github.anythingide.lace.core.component.Component;

public final class ActionButtonBehavior {
	private ActionButtonBehavior() {}
	
	public static void configure(Component component, Runnable handler,
		Color defaultColor, Color hoverColor, Color selectedColor,
		Color activeColor, Supplier<Boolean> activeChecker) {
		
		component.directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor));
		component.directive(MouseListenerDirective.of(e->{
			if (e.getAction() == MouseEvent.MOVE || e.getButton() != MouseEvent.LEFT_BUTTON) {
				((Component) e.getEventTarget()).directive(BackgroundDirective.of(hoverColor));
			} else if (e.getAction() == MouseEvent.PRESS || e.getAction() == MouseEvent.DRAG) {
				((Component) e.getEventTarget()).directive(BackgroundDirective.of(selectedColor));
			} else if (e.getAction() == MouseEvent.RELEASE) {
				if (handler!=null) {
					handler.run();
				}
				((Component) e.getEventTarget()).directive(BackgroundDirective.of(hoverColor));
			}
		}));
		component.directive(ExternalMouseListenerDirective.of(e->{
			((Component) e.getEventTarget()).directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor));
		}));
	}
}