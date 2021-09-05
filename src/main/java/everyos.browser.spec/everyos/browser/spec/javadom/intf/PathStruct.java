package everyos.browser.spec.javadom.intf;

import java.util.List;

public interface PathStruct {
	boolean getRootOfClosedTree();
	EventTarget getInvocationTarget();
	boolean getSlotInClosedTree();
	EventTarget getShadowAdjustedTarget();
	EventTarget getRelatedTarget();
	List<EventTarget> getTouchTargets();
}
