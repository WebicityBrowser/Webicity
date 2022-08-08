package everyos.browser.webicity.lacewebextensions.core.pipeline.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicity.lace.core.component.directive.DirectiveTarget;
import com.github.webicity.lace.core.laf.Content;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.imputils.pipeline.render.BasicRenderStageBox;

public class InlineLevelBoxStageBox implements BoxStageBox {

	private final Object identifier = new InlineLevelIdentifier();
	private final Content content;
	private final DirectiveTarget directives;
	private final List<BoxStageBox> children;
	
	private boolean containsBlockElements = false;
	
	public InlineLevelBoxStageBox(Content content, DirectiveTarget directives) {
		this(content, directives, new ArrayList<>());
	}
	
	private InlineLevelBoxStageBox(Content content, DirectiveTarget directives, List<BoxStageBox> children) {
		this.content = content;
		this.directives = directives;
		this.children = children;
	}
	
	@Override
	public void add(BoxStageBox child) {
		children.add(child);
		if (child instanceof BlockLevelBoxStageBox) {
			containsBlockElements = true;
		}
	}

	@Override
	public void integrate(List<BoxStageBox> boxes) {
		for (BoxStageBox box: boxes) {
			add(box);
		}
	}
	
	@Override
	public void finish(BoxStageBox parent) {
		if (parent == null) {
			return;
		}
		
		if (containsBlockElements) {
			parent.integrate(convertChildrenToBlockLevelBoxes());
		} else {
			parent.add(this);
		}
	}

	@Override
	public RenderStageBox convertToRenderStageBox() {
		RenderStageBox[] childRenderBoxes = new RenderStageBox[children.size()];
		for (int i = 0; i < children.size(); i++) {
			childRenderBoxes[i] = children.get(i).convertToRenderStageBox();
		}
		
		return new BasicRenderStageBox(identifier, content, directives, childRenderBoxes);
	}

	@Override
	public Object getIdentifier() {
		return identifier;
	}
	
	@Override
	public DirectiveTarget getDirectives() {
		return directives;
	}
	
	private List<BoxStageBox> convertChildrenToBlockLevelBoxes() {
		List<BoxStageBox> blockBoxes = new ArrayList<>();
		BoxStageBox currentBlockBox = null;
		for (int i = 0; i < children.size(); i++) {
			BoxStageBox child = children.get(i);
			if (child instanceof InlineLevelBoxStageBox) {
				if (currentBlockBox == null) {
					currentBlockBox = new BlockLevelBoxStageBox(content, directives);
					blockBoxes.add(currentBlockBox);
				}
				currentBlockBox.add(child);
			} else {
				currentBlockBox = null;
				blockBoxes.add(child);
			}
		}
		
		return blockBoxes;
	}
	
}
