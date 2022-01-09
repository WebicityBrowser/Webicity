package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.webicity.webribbon.gui.box.layout.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.layout.InlineLevelBox;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowContent;

public class BlockLevelBoxImp extends MultiBoxBase implements BlockLevelBox {
	
	//TODO: Should boxes hold their own content?
	private final BoxingStageBox parent;
	private final List<MultiBox> children;
	
	private boolean containsBlockElements = false;
	
	public BlockLevelBoxImp(BoxingStageBox parent) {
		this(parent, new ArrayList<MultiBox>(1));
	}
	
	public BlockLevelBoxImp(BoxingStageBox parent, List<MultiBox> children) {
		this.parent = parent;
		this.children = children;
	}

	@Override
	public void add(MultiBox box) {
		children.add(box);
		if (box instanceof BlockLevelBox) {
			containsBlockElements = true;
		}
	}

	@Override
	public void finish() {
		if (containsBlockElements) {
			blockInlineElements();
		}
		
		if (parent == null) {
			return;
		}
		
		parent.add(this);
	}

	@Override
	public MultiBox[] getChildren() {
		return this.children.toArray(new MultiBox[this.children.size()]);
	}
	
	@Override
	public void setChildren(List<MultiBox> children) {
		children.clear();
		for (MultiBox child: children) {
			children.add(child);
		}
	}
	
	@Override
	public MultiBox duplicate() {
		BlockLevelBoxImp box = new BlockLevelBoxImp(parent, new ArrayList<>(children.size()));
		box.setChildren(children);
		box.containsBlockElements = containsBlockElements;
		
		box.setProperties(getProperties());
		box.setContent(getContent());
		//TODO: Clone rest of attributes
		
		return box;
	}
	
	private void blockInlineElements() {
		int listSize = children.size();
		for (int i = 0; i < listSize; i++) {
			MultiBox child = children.get(i);
			if (children.get(i) instanceof InlineLevelBox) {
				MultiBox blockBox = new BlockLevelBoxImp(this, List.of(child));
				blockBox.setContent(new FlowContent());
				//TODO: Is this correct?
				blockBox.setProperties(ApplicablePropertyMap.empty());
				children.set(i, blockBox);
			}
		}
	}
	
}
