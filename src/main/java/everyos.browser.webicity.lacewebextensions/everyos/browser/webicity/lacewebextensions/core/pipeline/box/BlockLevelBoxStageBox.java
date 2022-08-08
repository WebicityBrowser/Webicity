package everyos.browser.webicity.lacewebextensions.core.pipeline.box;

import java.util.ArrayList;
import java.util.List;

import com.github.webicity.lace.core.component.directive.DirectiveTarget;
import com.github.webicity.lace.core.laf.Content;
import com.github.webicity.lace.core.pipeline.box.BoxStageBox;
import com.github.webicity.lace.core.pipeline.render.RenderStageBox;
import com.github.webicity.lace.imputils.pipeline.render.BasicRenderStageBox;

import everyos.browser.webicity.lacewebextensions.webuilaf.content.WebUIWebComponentContent;

public class BlockLevelBoxStageBox implements BoxStageBox {

	private final Object identifier = new BlockLevelIdentifier();
	private final Content content;
	private final DirectiveTarget directives;
	private final List<BoxStageBox> children;
	
	private boolean containsBlockElements = false;
	
	public BlockLevelBoxStageBox(Content content, DirectiveTarget directives) {
		this(content, directives, new ArrayList<>());
	}
	
	private BlockLevelBoxStageBox(Content content, DirectiveTarget directives, List<BoxStageBox> children) {
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
		if (containsBlockElements) {
			blockInlineElements();
		}
		
		if (parent == null) {
			return;
		}
		
		parent.add(this);
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
	
	private void blockInlineElements() {
		int listSize = children.size();
		for (int i = 0; i < listSize; i++) {
			BoxStageBox child = children.get(i);
			if (child instanceof InlineLevelBoxStageBox) {
				BoxStageBox blockBox = new BlockLevelBoxStageBox(
					new WebUIWebComponentContent(SingleChildLayoutManagerUI.get()),
					child.getDirectives(),
					List.of(child));
				children.set(i, blockBox);
			}
		}
	}

}
