package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.javadom.imp.TimeUtil;
import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.OverlyingBlockComponent;
import everyos.browser.webicitybrowser.util.TimeSystem;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.PositionDirective;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.Location;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.helper.ComputedChildrenHelper;
import everyos.engine.ribbon.ui.simple.helper.StringWrapHelper;

public class OverlyingBlockComponentUI extends SimpleBlockComponentUI {

    private Appearence appearence;
    private OverlyingBlockComponent component;
    private ComponentUI contentPaneUI;


    public OverlyingBlockComponentUI(Component c, ComponentUI parent) {
        super(c, parent);
        component = (OverlyingBlockComponent) c;
        this.appearence = new OverlyingBlockComponentUIAppearence();
    }

    @Override
    public Appearence getAppearence() {
        return appearence;
    }

    private class OverlyingBlockComponentUIAppearence implements Appearence {

        private Dimension bounds;

        @Override
        public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
//            sizepos.move(strwidth+r.getFontPaddingHeight(), true);
//            sizepos.setMinLineHeight(r.getFontHeight());

            this.bounds = sizepos.getSize();

            contentPaneUI = uimgr.get(component.contentView, OverlyingBlockComponentUI.this);
            contentPaneUI.render(r, sizepos, uimgr);
//            contentPaneUI.directive(SizeDirective.of(new Location(1, 0, 1, 0)).getDirective());
        }

        private float progress = 0;

        @Override
        public void paint(Renderer r) {
            if (component.isInvisible()) {
                if (progress > 0) progress -= TimeSystem.getDeltaSeconds();
                else return;
            } else {
                if (progress < 1)
                    progress += TimeSystem.getDeltaSeconds();
            }

            r.useBackground();
            int w = bounds.getWidth();
            int h = bounds.getHeight();

            int ha = (int) (h * progress);

            r = r.getSubcontext(0, 0, w, ha);

            r.fillRoundRect(0, ha-h, w, h, Styling.BUTTON_WIDTH*3);

            r.useForeground();

            contentPaneUI.paint(r);
        }

        @Override
        public void directive(UIDirective directive) {

        }

        @Override
        public void processEvent(UIEvent e) {

        }
    }

}
