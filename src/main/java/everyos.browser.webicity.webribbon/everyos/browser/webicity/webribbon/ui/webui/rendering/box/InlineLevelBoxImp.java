package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.webicity.webribbon.gui.box.layout.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.layout.InlineLevelBox;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;


//TODO: Inline graphics are very broken
public class InlineLevelBoxImp extends MultiBoxBase implements InlineLevelBox {
	
	//TODO: Should this be an optional?
	private final BoxingStageBox parent;
	private final List<MultiBox> children;
	
	private boolean containsBlockElements = false;

	public InlineLevelBoxImp(BoxingStageBox parent) {
		this(parent, new ArrayList<MultiBox>(1));
	}
	
	private InlineLevelBoxImp(BoxingStageBox parent, List<MultiBox> children) {
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
		if (parent == null) {
			return;
		}
		
		if (containsBlockElements) {
			List<MultiBox> integrated = new ArrayList<>();
			MultiBox blockBox = null;
			for (int i = 0; i < children.size(); i++) {
				MultiBox child = children.get(i);
				if (child instanceof InlineLevelBox) {
					if (blockBox == null) {
						//TODO: This had a .split() before, and I forget why
						blockBox = new BlockLevelBoxImp(this);
						blockBox.setContent(getContent());
						blockBox.setProperties(getProperties());
						integrated.add(blockBox);
					}
					blockBox.add(child);
				} else {
					blockBox = null;
					integrated.add(child);
				}
			}
			parent.integrate(integrated.toArray(new MultiBox[integrated.size()]));
		} else {
			parent.add(this);
		}
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
		InlineLevelBoxImp box = new InlineLevelBoxImp(parent, new ArrayList<>(children.size()));
		box.setChildren(children);
		box.containsBlockElements = containsBlockElements;
		
		box.setProperties(getProperties());
		box.setContent(getContent());
		//TODO: Clone rest of attributes
		
		return box;
	}
	
}
