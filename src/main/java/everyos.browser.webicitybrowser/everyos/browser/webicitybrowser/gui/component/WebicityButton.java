package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.FontSizeDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.shape.Location;

public class WebicityButton extends BlockComponent {

    private final OverlyingBlockComponent dropMenu;

    public WebicityButton(Component parent) {
        super();
        this.directive(FontSizeDirective.of(14));

        dropMenu = new OverlyingBlockComponent();

        TextButton content = new TextButton("Some crazy text");
        content.directive(PositionDirective.of(new Location(0, 0, 0, 0)));
        content.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
        dropMenu.addChild(content);

        dropMenu.directive(PositionDirective.of(new Location(0, 0, 0, Styling.ELEMENT_PADDING + Styling.BUTTON_WIDTH)));
        dropMenu.directive(SizeDirective.of(new Location(0, 150, 0, 100)));
        parent.addChild(dropMenu);
    }

    public void toggleMenu() {
        dropMenu.setInvisible(!dropMenu.isInvisible());
    }

}