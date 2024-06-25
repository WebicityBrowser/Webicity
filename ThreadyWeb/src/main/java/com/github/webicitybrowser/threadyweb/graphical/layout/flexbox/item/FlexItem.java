package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexGrowDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexShrinkDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.FlexDimension;

public class FlexItem {
	
	private final Box box;
	private final FlexItemSizePreferences sizePreferences;
	private final float flexGrow;
	private final float flexShrink;

	private float baseSize;
	private float hypotheticalMainSize;

	private float mainSize = RelativeDimension.UNBOUNDED;
	private float crossSize = RelativeDimension.UNBOUNDED;
	
	private RenderedUnit renderedUnit;

	private boolean isFrozen;
	private FlexDimension itemOffset;

	public FlexItem(Box box, FlexItemSizePreferences sizePreferences) {
		this.box = box;
		this.sizePreferences = sizePreferences;
		this.flexGrow = box
			.styleDirectives()
			.getDirectiveOrEmpty(FlexGrowDirective.class)
			.map(FlexGrowDirective::getFlexGrow)
			.orElse(0f);
		this.flexShrink = box
			.styleDirectives()
			.getDirectiveOrEmpty(FlexShrinkDirective.class)
			.map(FlexShrinkDirective::getFlexShrink)
			.orElse(1f);
	}

	public Box getBox() {
		return box;
	}

	public FlexItemSizePreferences getSizePreferences() {
		return sizePreferences;
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

	public RenderedUnit getRenderedUnit() {
		return renderedUnit;
	}

	public void setRenderedUnit(RenderedUnit renderedUnit) {
		this.renderedUnit = renderedUnit;
	}

	public void setFrozen(boolean isFrozen) {
		this.isFrozen = isFrozen;
	}

	public FlexDimension getItemOffset() {
		return itemOffset;
	}

	public void setItemOffset(FlexDimension itemOffset) {
		this.itemOffset = itemOffset;
	}

	public float getFlexGrow() {
		return flexGrow;
	}

	public float getFlexShrink() {
		return flexShrink;
	}

}
