package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.animation.SlideInAnimation;
import everyos.browser.webicitybrowser.gui.directive.AnimationDirective;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.BreakComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.LabelComponent;
import everyos.engine.ribbon.core.directive.BackgroundDirective;
import everyos.engine.ribbon.core.directive.FontSizeDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.shape.Location;

public class WebicityButton extends BlockComponent {

	private final AnimatedComponent dropMenu;

	public WebicityButton(Component parent) {
		super();
		this.directive(FontSizeDirective.of(14));

		dropMenu = new AnimatedComponent();
		dropMenu.directive(AnimationDirective.of(new SlideInAnimation()));
		dropMenu.directive(PositionDirective.of(new Location(0, 0, 0, Styling.ELEMENT_PADDING + Styling.BUTTON_WIDTH)));
		parent.addChild(dropMenu);
		
		String[] menuOptions = {
			"Open New Window",
			"Open New Private Window",
			"Settings",
			"History",
			"Bookmarks"
		};
		
		BlockComponent content = new BlockComponent();
		content.directive(BackgroundDirective.of(Styling.BACKGROUND_SECONDARY_ACTIVE));
		content.addChild(new BlockComponent()
				.directive(SizeDirective.of(new Location(-1, -1, 0, 5))));
		content.addChild(new BreakComponent());
		for (String option: menuOptions) {
			content.addChild(new BlockComponent()
				.directive(SizeDirective.of(new Location(0, 10, -1, -1))));
			content.addChild(new LabelComponent().text(option));
			content.addChild(new BlockComponent()
					.directive(SizeDirective.of(new Location(0, 10, -1, -1))));
			content.addChild(new BreakComponent());
		}
		content.addChild(new BlockComponent()
				.directive(SizeDirective.of(new Location(-1, -1, 0, 5))));
		dropMenu.addChild(content);
	}

	public void toggleMenu() {
		dropMenu.setVisible(!dropMenu.isVisible());
	}

}