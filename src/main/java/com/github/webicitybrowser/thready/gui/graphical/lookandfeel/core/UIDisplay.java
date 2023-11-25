package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

/**
 * The UI display manages the render cycle for a component.
 * It is composed of various algorithms that either convert
 * one form of data to another, or have an IO effect.
 */
public interface UIDisplay<T extends Context, U extends Box, V extends RenderedUnit> {

	/**
	 * Creates a context for the given component. The context
	 * is used to store information about the component that
	 * is persistent even when the surrounding tree changes,
	 * as long as the component remains in the tree.
	 * @param componentUI the component to create a context for
	 * @return the context
	 */
	T createContext(ComponentUI componentUI);
	
	/**
	 * Creates a box for the given component. The box is used
	 * to determine how the component interacts with other
	 * components in the tree.
	 * @param displayContext the context for the component,
	 * 	as explained in {@link #createContext(ComponentUI)}
	 * @param boxContext a context containing data or operations
	 * 	that may be useful for creating the box
	 * @param styleGenerator generates styling information for
	 * 	the component
	 * @return the box
	 */
	List<U> generateBoxes(T displayContext, BoxContext boxContext, StyleGenerator styleGenerator);

	V renderBox(U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext);

	default void composite(V unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		compositeContext.addPaintUnit(unit, localCompositeContext);
	};
	
	void paint(V unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext);

	MessageHandler createMessageHandler(V unit, Rectangle documentRect);
	
}
