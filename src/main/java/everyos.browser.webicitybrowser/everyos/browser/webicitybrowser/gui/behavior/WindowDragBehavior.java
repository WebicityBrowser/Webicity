package everyos.browser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import everyos.browser.webicitybrowser.gui.window.GUIWindow;
import everyos.desktop.thready.basic.directive.ExternalMouseListenerDirective;
import everyos.desktop.thready.basic.directive.MouseListenerDirective;
import everyos.desktop.thready.basic.event.MouseEvent;
import everyos.desktop.thready.basic.event.MouseListener;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.stage.message.MouseConstants;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.util.AbsolutePositionMath;

public final class WindowDragBehavior {

	private WindowDragBehavior() {}
	
	public static void addDragBehavior(Component windowDecor, Supplier<GUIWindow> windowSupplier) {
		MouseListener mouseListener = new MouseListener() {
			
			private AbsolutePosition windowStartPosition;
			private AbsolutePosition mouseStartPosition;
			private boolean isSelected;
			
			@Override
			public void accept(MouseEvent e) {
				GUIWindow window = windowSupplier.get();
				if (e.getAction() == MouseConstants.DRAG && isSelected) {
					AbsolutePosition mouseChange = AbsolutePositionMath.diff(e.getScreenPosition(), mouseStartPosition);
					AbsolutePosition newWindowPosition = AbsolutePositionMath.sum(windowStartPosition, mouseChange);
					window.setPosition(newWindowPosition);
				} else if (e.getButton() != MouseConstants.LEFT_BUTTON) {
					return;
				} else if (e.getAction() == MouseConstants.PRESS && e.isSource()) {
					isSelected = true;
					windowStartPosition = window.getPosition();
					mouseStartPosition = e.getScreenPosition();
				} else if (e.getAction() == MouseConstants.RELEASE) {
					isSelected = false;
				}
			}
			
		};
		
		windowDecor.directive(MouseListenerDirective.of(mouseListener));
		windowDecor.directive(ExternalMouseListenerDirective.of(mouseListener));
	}
	
}
