package everyos.browser.javadom.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.javadom.intf.Event;
import everyos.browser.javadom.intf.EventInit;
import everyos.browser.javadom.intf.EventTarget;
import everyos.browser.javadom.intf.Node;
import everyos.browser.javadom.intf.PathStruct;
import everyos.browser.javadom.intf.ShadowRoot;
import everyos.browser.javadom.intf.Slot;
import everyos.browser.javadom.intf.Slottable;
import everyos.browser.javadom.intf.html.DOMWindowPart;
import everyos.browser.javadom.intf.html.PartialWindow;
import everyos.browser.juievents.intf.MouseEvent;

public class JDEvent implements Event {
	private String type;
	private EventTarget target = null;
	private EventTarget currentTarget = null;
	private List<PathStruct> path = new ArrayList<>();
	private short eventPhase = 0;
	private boolean stopPropogation;
	@SuppressWarnings("unused")
	private boolean stopImmediatePropogation;
	private boolean bubbles;
	private boolean cancelable;
	private boolean canceled;
	private boolean composed;
	private boolean isTrusted;
	private double timeStamp;
	private boolean dispatch;
	@SuppressWarnings("unused")
	private boolean initialized;
	
	public JDEvent(String type, EventInit eventInitDict) {
		innerEventCreationSteps(Time.now(), eventInitDict);
		setType(type);
	}
	public JDEvent() {
		this(null, null);
		setIsTrusted(true);
	}
	
	@Override public String getType() {
		return type;
	}

	@Override public EventTarget getTarget() {
		return target;
	}

	@Override public EventTarget getSrcElement() {
		return target;
	}
	
	@Override public EventTarget getCurrentTarget() {
		return currentTarget;
	}

	@Override public List<EventTarget> composedPath() {
		//I have no idea what this actually does, will have to look
		List<EventTarget> composedPath = new ArrayList<>();
		if (path.isEmpty()) return composedPath;
		EventTarget currentTarget = this.currentTarget;
		composedPath.add(currentTarget);
		int currentTargetIndex = 0;
		int currentTargetHiddenSubtreeLevel = 0;
		int index = path.size()-1;
		while (index>=0) {
			if (path.get(index).getRootOfClosedTree()) {
				currentTargetHiddenSubtreeLevel++;
			}
			if (path.get(index).getInvocationTarget()==currentTarget) {
				currentTargetIndex = index;
				break;
			}
			if (path.get(index).getSlotInClosedTree()) {
				currentTargetHiddenSubtreeLevel--;
			}
			index--;
		}
		int currentHiddenLevel = currentTargetHiddenSubtreeLevel;
		int maxHiddenLevel = currentTargetHiddenSubtreeLevel;
		index = currentTargetIndex-1;
		while (index>=0) {
			if (path.get(index).getRootOfClosedTree()) {
				currentHiddenLevel++;
			}
			if (currentHiddenLevel<=maxHiddenLevel) {
				composedPath.add(0, path.get(index).getInvocationTarget());
			}
			if (path.get(index).getSlotInClosedTree()) {
				currentHiddenLevel--;
				if(currentHiddenLevel<maxHiddenLevel) {
					maxHiddenLevel = currentHiddenLevel;
				}
			}
			index--;
		}
		currentHiddenLevel = currentTargetHiddenSubtreeLevel;
		maxHiddenLevel = currentTargetHiddenSubtreeLevel;
		index = currentTargetIndex+1;
		while (index<path.size()) {
			if (path.get(index).getRootOfClosedTree()) {
				currentHiddenLevel++;
			}
			if (currentHiddenLevel<=maxHiddenLevel) {
				composedPath.add(0, path.get(index).getInvocationTarget());
			}
			if (path.get(index).getSlotInClosedTree()) {
				currentHiddenLevel--;
				if(currentHiddenLevel<maxHiddenLevel) {
					maxHiddenLevel = currentHiddenLevel;
				}
			}
			index++;
		}
		return composedPath;
	}

	@Override public short getEventPhase() {
		return eventPhase;
	}

	@Override public void stopPropogation() {
		this.stopPropogation = true;
	}

	@Override public boolean getCancelBubble() {
		return this.stopPropogation;
	}

	@Override public void setCancelBubble(boolean v) {
		this.stopPropogation = v;
	}
	
	@Override public void stopImmediatePropogation() {
		this.stopPropogation = true;
		this.stopImmediatePropogation = true;
	}

	@Override public boolean getBubbles() {
		return this.bubbles;
	}

	@Override public boolean getCancelable() {
		return this.cancelable;
	}
	@Override public boolean getReturnValue() {
		return !this.canceled;
	}
	@Override public void setReturnValue(boolean v) {
		if (v==false) {
			this.canceled = true;
		}
	}
	@Override public void preventDefault() {
		this.canceled = true;
	}
	@Override public boolean getDefaultPrevented() {
		return this.canceled;
	}
	@Override public boolean getComposed() {
		return this.composed;
	}

	@Override public boolean getIsTrusted() {
		return this.isTrusted;
	}
	@Override public double getTimeStamp() {
		return this.timeStamp;
	}
	@Override public void initEvent(String type, boolean bubbles, boolean cancelable) {
		if (getDispatch()) return;
		
		initialize(type, bubbles, cancelable);
	}

	protected void initialize(String type, boolean bubbles, boolean cancelable) {
		this.initialized = true;
		this.stopPropogation = false;
		this.stopImmediatePropogation = false;
		this.canceled = false;
		this.isTrusted = false;
		this.target = null;
		this.type = type;
		this.bubbles = bubbles;
		this.cancelable = cancelable;
	}
	protected boolean getDispatch() {
		return this.dispatch;
	}
	protected void setType(String type) {
		this.type = type;
	}
	protected void setInitialized(boolean b) {
		this.initialized = b;
	}
	protected void setTimeStamp(double time) {
		this.timeStamp = time;
	}
	protected void setBubbles(boolean bubbles) {
		this.bubbles = bubbles;
	}
	protected void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}
	protected void setComposed(boolean composed) {
		this.composed = composed;
	}
	protected void setIsTrusted(boolean b) {
		this.isTrusted = b;
	}
	protected void setDispatch(boolean b) {
		this.dispatch = b;
	}
	protected EventTarget getRelatedTarget() {
		return null;
	}
	protected List<PathStruct> getPath() {
		return path;
	}
	
	
	protected void innerEventCreationSteps(double time, EventInit eventInitDict) {
		setInitialized(true);
		setTimeStamp(time);
		
		//Copy eventInitDict
		if (eventInitDict!=null) {
			setBubbles(eventInitDict.getBubbles());
			setCancelable(eventInitDict.getCancelable());
			setComposed(eventInitDict.getComposed());
		}
		
		eventConstructingSteps();
	}

	protected void eventConstructingSteps() {
		
	}
	
	private void dispatchEventListener(EventTarget target, EventTarget legacyTargetOverride, boolean legacyOutputDidListenersThrow) {
		setDispatch(true);
		EventTarget targetOverride = target;
		assert(legacyTargetOverride==null||targetOverride instanceof PartialWindow);
		if (legacyTargetOverride!=null&&targetOverride instanceof PartialWindow) {
			target = ((PartialWindow) target).getDocument();
		}
		EventTarget activationTarget = null;
		EventTarget relatedTarget = retarget(getRelatedTarget(), target);
		if (target!=relatedTarget||target==getRelatedTarget()) {
			//TODO: Another instance where we should use dependency inversion
			//to avoid relying on `ArrayList` as our list
			List<EventTarget> touchTargets = new ArrayList<>();
			for (EventTarget touchTarget: getTouchTargetList()) {
				touchTargets.add(retarget(touchTarget, target));
			}
			appendToAnEventPath(target, targetOverride, relatedTarget, touchTargets, false);
			boolean isActivationEvent = this instanceof MouseEvent &&
				((MouseEvent) this).getType().equals("click");
			if (isActivationEvent) { //TODO: Check if target has activation behaviour
				activationTarget = target;
			}
			Slottable slottable = target instanceof Slottable&&((Slottable) target).isAssigned()?(Slottable) target:null;
			boolean slotInClosedTree = false;
			EventTarget parent = target.getTheParent(this);
			while (parent!=null) {
				if (slottable!=null) {
					assert(parent instanceof Slot);
					slottable = null;
					((Slot) parent).getRootNode(null);
				}
				if (parent instanceof Slottable&&((Slottable) parent).isAssigned()) {
					slottable = (Slottable) parent;
				}
				relatedTarget = retarget(this.getRelatedTarget(), parent);
				touchTargets = new ArrayList<>();
				for (EventTarget touchTarget: getTouchTargetList()) {
					touchTargets.add(retarget(touchTarget, parent));
				}
				if (parent instanceof DOMWindowPart || (parent instanceof Node && false)) { //TODO
					if (isActivationEvent && bubbles && activationTarget==null && parent.getActivationBehaviour()!=null) {
						activationTarget = parent;
					}
					appendToAnEventPath(parent, null, relatedTarget, touchTargets, slotInClosedTree);
				} else if (parent==relatedTarget) {
					parent = null;
				} else {
					target = parent;
					if (isActivationEvent && activationTarget==null && parent.getActivationBehaviour()!=null) {
						activationTarget = target;
					}
					appendToAnEventPath(parent, target, relatedTarget, touchTargets, slotInClosedTree);
				}
				if (parent!=null) {
					parent = parent.getTheParent(this);
				}
				slotInClosedTree = false;
			}
			PathStruct clearTargetsStruct = null;
			for (PathStruct struct: this.path) {
				if (struct.getShadowAdjustedTarget()!=null) {
					clearTargetsStruct = struct;
				}
			}
			boolean clearTargets = internalCheckClearTargets(clearTargetsStruct);
			//TODO: Finish this method. This is going on too long
		}
	}
	
	private boolean internalCheckClearTargets(PathStruct cls) {
		EventTarget sat = cls.getShadowAdjustedTarget();
		if (sat instanceof Node && ((Node) sat).getRootNode(null) instanceof ShadowRoot) {
			return true;
		}
		
		EventTarget rt = cls.getRelatedTarget();
		if (rt instanceof Node && ((Node) rt).getRootNode(null) instanceof ShadowRoot) {
			return true;
		}
		
		for (EventTarget tt: cls.getTouchTargets()) {
			if (tt instanceof Node && ((Node) tt).getRootNode(null) instanceof ShadowRoot) {
				return true;
			}
		}
		
		return false;
	}
	
	private void appendToAnEventPath(EventTarget invocationTarget, EventTarget shadowAdjustedTarget, EventTarget relatedTarget,
		List<EventTarget> touchTargets, boolean slotInClosedTree) {
		
		boolean invocationTargetInShadowTree = false;
		if (invocationTarget instanceof Node && ((Node) invocationTarget).getRootNode(null) instanceof ShadowRoot) {
			invocationTargetInShadowTree = true;
		}
		boolean rootOfClosedTree = false;
		if (invocationTarget instanceof ShadowRoot && ((ShadowRoot) invocationTarget).getMode().equals("closed")) {
			rootOfClosedTree = true;
		}
		getPath().add(new JDPathStruct(
			invocationTarget, invocationTargetInShadowTree, shadowAdjustedTarget, relatedTarget, touchTargets, rootOfClosedTree, slotInClosedTree));
	}
	private List<EventTarget> getTouchTargetList() {
		// TODO Auto-generated method stub
		return null;
	}
	private EventTarget retarget(Object a, Object b) {
		//TODO
		return target;
	}
	
}
