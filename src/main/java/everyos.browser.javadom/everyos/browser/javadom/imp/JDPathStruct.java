package everyos.browser.javadom.imp;

import java.util.List;

import everyos.browser.javadom.intf.EventTarget;
import everyos.browser.javadom.intf.PathStruct;

public class JDPathStruct implements PathStruct {

	private boolean rootOfClosedTree;
	private EventTarget invocationTarget;
	private boolean slotInClosedTree;
	private EventTarget shadowAdjustedTarget;
	private EventTarget relatedTarget;
	private List<EventTarget> touchTargetList;

	public JDPathStruct(EventTarget invocationTarget, boolean invocationTargetInShadowTree,
		EventTarget shadowAdjustedTarget, EventTarget relatedTarget, List<EventTarget> touchTargetList,
		boolean rootOfClosedTree, boolean slotInClosedTree) {
		
		this.rootOfClosedTree = rootOfClosedTree;
		this.invocationTarget = invocationTarget;
		this.shadowAdjustedTarget = shadowAdjustedTarget;
		this.slotInClosedTree = slotInClosedTree;
		this.relatedTarget = relatedTarget;
		this.touchTargetList = touchTargetList;
	}

	@Override
	public boolean getRootOfClosedTree() {
		return rootOfClosedTree;
	}

	@Override
	public EventTarget getInvocationTarget() {
		return invocationTarget;
	}

	@Override
	public boolean getSlotInClosedTree() {
		return slotInClosedTree;
	}

	@Override
	public EventTarget getShadowAdjustedTarget() {
		return shadowAdjustedTarget;
	}

	@Override
	public EventTarget getRelatedTarget() {
		return relatedTarget;
	}
	
	@Override
	public List<EventTarget> getTouchTargets() {
		return touchTargetList;
	}
}
