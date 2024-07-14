package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf;

import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleDefinition;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.InnerDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.OuterDisplayDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.derived.DerivedFontDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow.LineHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LetterSpacingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.document.DocumentDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextDisplay;
import com.github.webicitybrowser.threadyweb.graphical.value.FloatDirection;
import com.github.webicitybrowser.threadyweb.graphical.value.InnerDisplay;
import com.github.webicitybrowser.threadyweb.graphical.value.OuterDisplay;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;
import com.github.webicitybrowser.threadyweb.tree.BreakComponent;
import com.github.webicitybrowser.threadyweb.tree.DocumentComponent;
import com.github.webicitybrowser.threadyweb.tree.ElementComponent;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;
import com.github.webicitybrowser.threadyweb.tree.image.ImageComponent;

public final class WebLookAndFeel {

	private WebLookAndFeel() {}
	
	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(DocumentComponent.class, DocumentDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(ElementComponent.class, ElementDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(BreakComponent.class, BreakDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(TextComponent.class, TextDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(ImageComponent.class, ImageDisplay::componentUI);

		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			FloatDirective.class, FloatDirective.of(FloatDirection.NONE), false, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			LineHeightDirective.class, LineHeightDirective.of(SizeCalculation.SIZE_AUTO), true, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			OuterDisplayDirective.class, OuterDisplayDirective.of(OuterDisplay.INLINE), false, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			InnerDisplayDirective.class, InnerDisplayDirective.of(InnerDisplay.FLOW), false, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			WhiteSpaceCollapseDirective.class, WhiteSpaceCollapseDirective.of(WhiteSpaceCollapse.COLLAPSE), true, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			LetterSpacingDirective.class, LetterSpacingDirective.of(SizeCalculation.SIZE_ZERO), true, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			LineBreakDirective.class, LineBreakDirective.of(LineBreak.NORMAL), true, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			DerivedFontDirective.class, DerivedFontDirective.of(null), true, InvalidationLevel.RENDER));
	}
	
}
