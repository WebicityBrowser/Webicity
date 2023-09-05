package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexGrowDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexShrinkDirective;

public class FlexItem {
	
	private final Box box;

	private float baseSize;
	private float hypotheticalMainSize;

	private float mainSize;
	private float crossSize;
	
	private boolean isFrozen;
	private RenderedUnit renderedUnit;

	private FlexDimension itemOffset;

	private float flexGrow;
	private float flexShrink;

	public FlexItem(Box box) {
		this.box = box;
	}

	public Box getBox() {
		return box;
	}

	public float getBaseSize() {
		return baseSize;
	}

	public void setBaseSize(float baseSize) {
		this.baseSize = baseSize;
	}

	public float getHypotheticalMainSize() {
		return hypotheticalMainSize;
	}

	public void setHypotheticalMainSize(float hypotheticalMainSize) {
		this.hypotheticalMainSize = hypotheticalMainSize;
	}

	public float getMainSize() {
		return mainSize;
	}

	public void setMainSize(float mainSize) {
		this.mainSize = mainSize;
	}

	public float getCrossSize() {
		return crossSize;
	}

	public void setCrossSize(float crossSize) {
		this.crossSize = crossSize;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	public RenderedUnit getRenderedUnit() {
		return renderedUnit;
	}

	public void setRenderedUnit(RenderedUnit renderedUnit) {
		this.renderedUnit = renderedUnit;
	}

	public FlexDimension getItemOffset() {
		return itemOffset;
	}

	public void setItemOffset(FlexDimension itemOffset) {
		this.itemOffset = itemOffset;
	}

	public float getFlexGrow() {
		if (flexGrow == 0) {
			flexGrow = box
				.styleDirectives()
				.getDirectiveOrEmpty(FlexGrowDirective.class)
				.map(FlexGrowDirective::getFlexGrow)
				.orElse(0f);
		}
		return flexGrow;
	}

	public float getFlexShrink() {
		if (flexShrink == 0) {
			flexShrink = box
				.styleDirectives()
				.getDirectiveOrEmpty(FlexShrinkDirective.class)
				.map(FlexShrinkDirective::getFlexShrink)
				.orElse(1f);
		}
		return flexShrink;
	}

}
