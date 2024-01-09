package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import java.util.Optional;

import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ReplacedInfo;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;
import com.github.webicitybrowser.threadyweb.tree.image.ImageComponent;
import com.github.webicitybrowser.threadyweb.tree.image.ImageStatus;

public record ImageBox(UIDisplay<?, ?, ?> display, ImageComponent owningComponent, DirectivePool styleDirectives) implements Box {

	@Override
	public Optional<ReplacedInfo> replacedInfo() {
		ImageStatus imageStatus = owningComponent.getImageStatus();
		if (!imageStatus.canImageBeShown()) return Optional.empty();
		ImageFrame firstImageFrame = imageStatus.imageData().frames()[0];
		float intrinsicWidth = firstImageFrame.width();
		float intrinsicHeight = firstImageFrame.height();
		float intrinsicRatio = intrinsicWidth / intrinsicHeight;
		return Optional.of(new ImageReplacedInfo(intrinsicWidth, intrinsicHeight, intrinsicRatio));
	}

	@Override
	public boolean isFluid() {
		OuterDisplay outerDisplay = styleDirectives
			.getDirectiveOrEmpty(OuterDisplayDirective.class)
			.map(OuterDisplayDirective::getOuterDisplay)
			.orElse(OuterDisplay.INLINE);
		return outerDisplay == OuterDisplay.INLINE;
	}

	@Override
	public boolean managesSelf() {
		return true;
	}

	private static record ImageReplacedInfo(float intrinsicWidth, float intrinsicHeight, float intrinsicRatio) implements ReplacedInfo {}

}
