package everyos.engine.ribbon.core.graphics;

import java.util.function.Function;

import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public interface Component extends UIEventTarget {
	
	/**
	 * Send an instruction to the L&F
	 * @param uicls A class representing which type of ComponentUIs should receive this directive
	 * @param directive The directive to be sent
	 * @return Chain-able self
	 */
	Component directive(Class<? extends ComponentUI> uicls, UIDirectiveWrapper directive);
	
	/**
	 * Sends a UIDirective to all applicable UIs
	 * @param directive The directive to be sent
	 * @return Chain-able self
	 */
	Component directive(UIDirectiveWrapper directive);
	
	/**
	 * Get all UI directives this component holds, applicable to a given class
	 * @param cls The class to get applicable UI directives of
	 * @return The applicable UI directives
	 */
	<T extends ComponentUI> UIDirectiveWrapper[] getDirectives(Class<T> cls);
	
	void addChild(Component component);
	void addChild(int pos, Component component);
	
	void removeChild(Component component);
	void removeChild(int i);

	/**
	 * Set which children this component is a parent of
	 * @param children An array of the components to be considered children
	 * @return Chain-able self
	 */
	Component children(Component[] children);
	
	/**
	 * Get an array of all components set as a child of this component
	 * @return An array of all components set as a child of this component
	 */
	Component[] getChildren();
	
	/**
	 * Invalidate any ComponentUIs attached to this component,
	 * up to the stage represented by the specified level
	 * @param level Represents what stage to invalidate up to
	 */
	void invalidate(InvalidationLevel level);
	
	/**
	 * Convenience method for easier casting
	 * @param <T> The type of class to cast this object to
	 * @return This object, but casted to type T
	 */
	<T extends Component> T casted();
	
	/**
	 * Binds a ComponentUI to this component, allowing it to receive events from this component
	 * @param ui The ComponentUI to be bound
	 */
	void bind(ComponentUI ui);
	
	/**
	 * Reverses the effect of binding a ComponentUI to this component
	 * @param ui The ComponentUI to be unbound
	 */
	void unbind(ComponentUI ui);
	
	/**
	 * Recursively query this component and any children against a filter
	 * to get a list of any components matching this filter
	 * @param query The filter to compare components against
	 * @return An array of all components that were matched
	 */
	Component[] query(Function<Component, Boolean> query);
	
}