package com.github.webicitybrowser.webicitybrowser.gui.behavior;

import java.util.function.Supplier;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.gui.graphical.directive.ExternalMouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.MouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.gui.graphical.event.mouse.MouseEvent;
import com.github.webicitybrowser.thready.gui.graphical.event.mouse.MouseListener;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.gui.window.GUIWindow;

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
