package com.github.webicitybrowser.webicitybrowser.gui.behavior;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.directive.basics.ChildrenDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.ExternalMouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.MouseListenerDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.event.MouseEvent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseConstants;
import com.github.webicitybrowser.thready.windowing.core.event.mouse.MouseListener;

public final class ComponentDragBehavior {

	private ComponentDragBehavior() {}
	
	public static <T> void addComponentDragBehavior(Component target, DragConfiguration<T> configuration) {
	
		MouseListener mouseListener = new MouseListener() {
			
			private AbsolutePosition componentStartPosition;
			private AbsolutePosition mouseStartPosition;
			private Component selectedComponentTile;
			
			@Override
			public void accept(MouseEvent e) {
				ComponentDragTracker<T> tracker = configuration.getTracker();
				if (e.getAction() == MouseConstants.DRAG && selectedComponentTile != null) {
					AbsolutePosition mouseChange = AbsoluteDimensionsMath.diff(e.getViewportPosition(), mouseStartPosition, AbsolutePosition::new);
					AbsolutePosition newComponentPosition = AbsoluteDimensionsMath.sum(componentStartPosition, mouseChange, AbsolutePosition::new);
					target.directive(PositionDirective.of(newComponentPosition));
				} else if (e.getButton() != MouseConstants.LEFT_BUTTON) {
					return;
				} else if (e.getAction() == MouseConstants.PRESS && e.isSource()) {
					tracker.set(configuration.createDragData());
					selectedComponentTile = configuration.createDragTile();
					configuration.getDragRoot().directive(ChildrenDirective.of(selectedComponentTile));
					componentStartPosition = e.getViewportPosition();
					mouseStartPosition = e.getViewportPosition();
				} else if (e.getAction() == MouseConstants.RELEASE) {
					if (tracker.transfer()) {
						configuration.onDragComplete();
					}
					stopDragOp();
				}
			}

			private void stopDragOp() {
				selectedComponentTile = null;
				configuration.getDragRoot().directive(ChildrenDirective.of());
			}
			
		};
		
		target.directive(MouseListenerDirective.of(mouseListener));
		target.directive(ExternalMouseListenerDirective.of(mouseListener));
		
	}
	
	public static interface DragConfiguration<T> {
		
		ComponentDragTracker<T> getTracker();
		
		Component getDragRoot();
		
		Component createDragTile();
		
		T createDragData();
		
		void onDragComplete();
		
	}
	
	public static interface ComponentDragTracker<T> {
		
		void set(T data);
		
		void cancel();
		
		boolean transfer();
		
		void setDropReceiver(Function<T, Boolean> data);
		
	}
	
	public static <T> ComponentDragTracker<T> create() {
		return new ComponentDragTracker<T>() {

			private T data;
			private Function<T, Boolean> dropReceiver;
			
			@Override
			public void set(T data) {
				this.data = data;
				
			}

			@Override
			public void cancel() {
				data = null;
				dropReceiver = null;
			}

			@Override
			public boolean transfer() {
				if (dropReceiver == null) {
					return false;
				}
				return dropReceiver.apply(data);
			}

			@Override
			public void setDropReceiver(Function<T, Boolean> receiver) {
				this.dropReceiver = receiver;
			}
			
		};
	}
	
}
