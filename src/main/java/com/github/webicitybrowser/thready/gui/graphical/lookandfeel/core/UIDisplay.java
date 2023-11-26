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

	/**
	 * Renders the given box into a unit that can be painted.
	 * This will determine constraints such as sizes and
	 * positions for this box and any children.
	 * @param box the box to render
	 * @param globalRenderContext a context containing data or
	 * 	operations that may be useful for rendering the box
	 * @param localRenderContext a context containing data determined
	 *  by a parent box that may be useful for rendering the box
	 */
	V renderBox(U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext);

	/**
	 * Assign composite layers to the given unit. This is
	 * designed to make some operations, such as positioning,
	 * easier to implement. It is also meant to allow animations
	 * to be implemented efficiently.
	 * @param unit the unit to composite
	 * @param compositeContext a context containing data or
	 * 	operations that may be useful for compositing the unit
	 * @param localCompositeContext a context containing data
	 * 	determined by a parent unit that may be useful for compositing
	 * 	the unit
	 */
	default void composite(V unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		compositeContext.addPaintUnit(unit, localCompositeContext);
	};
	
	/**
	 * Paints the given unit onto the screen.
	 * @param unit the unit to paint
	 * @param globalPaintContext a context containing data or
	 * 	operations that may be useful for painting the unit
	 * @param localPaintContext a context containing data
	 * 	determined by a parent unit that may be useful for painting
	 */
	void paint(V unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext);

	/**
	 * Creates a message handler for the given unit. The message
	 * handler is used to handle events that occur on the unit.
	 * @param unit the unit to create a message handler for
	 * @param documentRect the rectangle, relative to the composite layer,
	 *  that the unit occupies
	 * @return the message handler
	 */
	MessageHandler createMessageHandler(V unit, Rectangle documentRect);
	
}
