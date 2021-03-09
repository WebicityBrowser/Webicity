package everyos.browser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.ExternalMouseListenerDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.MouseListenerDirective;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseEvent;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;

public final class ActionButtonBehavior {
	private ActionButtonBehavior() {}
	
	public static void configure(Component component, Runnable handler,
		Color defaultColor, Color hoverColor, Color selectedColor,
		Color activeColor, Supplier<Boolean> activeChecker) {
		
		component
			.directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor))
			.directive(MouseListenerDirective.of(e->{
				if (e.getAction()==MouseEvent.MOVE) {
					e.getComponent().directive(BackgroundDirective.of(hoverColor));
				}
				//TODO: These elements should only respond to the left mouse button, but the drag handler
				// wasn't working when I tried to do that.
				if (e.getAction()==MouseEvent.PRESS||e.getAction()==MouseEvent.DRAG) {
					e.getComponent().directive(BackgroundDirective.of(selectedColor));
				}
				if (e.getButton()!=MouseEvent.LEFT_BUTTON) return;
				if (e.getAction()==MouseEvent.RELEASE) {
					if (handler!=null) handler.run();
					e.getComponent().directive(BackgroundDirective.of(hoverColor));
				}
			}))
			.directive(ExternalMouseListenerDirective.of(e->{
				e.getComponent().directive(BackgroundDirective.of(activeChecker.get()?activeColor:defaultColor));
			}));
	}
}
