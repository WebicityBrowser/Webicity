package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.dimensions.RelativePosition;
import com.github.webicitybrowser.thready.dimensions.RelativeSize;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.directive.BackgroundColorDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.SizeDirective;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.LookAndFeelBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.style.StyleDefinition;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.ContainerDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text.TextDisplay;
import com.github.webicitybrowser.thready.gui.tree.basics.ContainerComponent;
import com.github.webicitybrowser.thready.gui.tree.basics.TextComponent;

public final class SimpleLookAndFeel {

	private static final RelativeDimension UNBOUNDED_DIMENSION = new RelativeDimension(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED);

	private SimpleLookAndFeel() {}

	public static void installTo(LookAndFeelBuilder lookAndFeelBuilder) {
		lookAndFeelBuilder.registerComponentUI(ContainerComponent.class, ContainerDisplay::componentUI);
		lookAndFeelBuilder.registerComponentUI(TextComponent.class, TextDisplay::componentUI);

		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			SizeDirective.class, SizeDirective.of(new RelativeSize(UNBOUNDED_DIMENSION, UNBOUNDED_DIMENSION)),
			false, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			PositionDirective.class, PositionDirective.of(new RelativePosition(UNBOUNDED_DIMENSION, UNBOUNDED_DIMENSION)),
			false, InvalidationLevel.RENDER));
		lookAndFeelBuilder.registerStyleDefinition(new StyleDefinition<>(
			BackgroundColorDirective.class, BackgroundColorDirective.of(Colors.TRANSPARENT),
			false, InvalidationLevel.RENDER));

	}
	
}
