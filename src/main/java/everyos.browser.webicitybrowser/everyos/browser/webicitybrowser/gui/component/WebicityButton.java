package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.FontSizeDirective;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class WebicityButton extends TextButton {

    private final OverlyingBlockComponent dropMenu;

    public WebicityButton(Component parent) {
        super(Styling.PRODUCT_NAME, new WebicityButtonAppearance());
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

    private static class WebicityButtonAppearance implements Appearence {

        private Dimension bounds;
        private int d = Styling.BUTTON_WIDTH; // Diameter

        @Override
        public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
            bounds = sizepos.getSize();
        }

        @Override
        public void paint(Renderer r) {
            r.useBackground();

            int w = bounds.getWidth();
            int h = bounds.getHeight();

            r.drawFilledRect(0, 0, w, h - d / 2);
            r.drawEllipse(w - d, h - d, d, d);
            r.drawFilledRect(0, h - d / 2, w-d/2, d/2);
        }

        @Override
        public void directive(UIDirective directive) {

        }

        @Override
        public void processEvent(UIEvent e) {

        }
    }

}