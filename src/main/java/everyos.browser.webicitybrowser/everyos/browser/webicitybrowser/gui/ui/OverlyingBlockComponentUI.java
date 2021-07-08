package everyos.browser.webicitybrowser.gui.ui;

import everyos.browser.webicitybrowser.gui.Styling;
import everyos.browser.webicitybrowser.gui.component.OverlyingBlockComponent;
import everyos.browser.webicitybrowser.util.TimeSystem;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.SimpleBlockComponentUI;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;
import everyos.engine.ribbon.ui.simple.layout.InlineBlockLayout;

public class OverlyingBlockComponentUI extends SimpleBlockComponentUI {

    private Appearence appearence;
    private OverlyingBlockComponent component;
    private InlineBlockLayout layout;

    public OverlyingBlockComponentUI(Component c, ComponentUI parent) {
        super(c, parent);
        component = getComponent().casted(OverlyingBlockComponent.class);
        this.appearence = new OverlyingBlockComponentUIAppearence();
        layout = (InlineBlockLayout) getLayout();
        layout.setAutoManageChildren(false);
    }

    @Override
    public Appearence getAppearence() {
        return appearence;
    }

    private class OverlyingBlockComponentUIAppearence implements Appearence {

        private Dimension bounds;
        private float _progress = 0;

        @Override
        public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
            // TODO: Fix component's bounds shadowing other under it buttons even when invisible due to which they cant receive mouse actions.
            this.bounds = sizepos.getSize();
            layout.renderChildren(r, sizepos, uimgr);
        }

        @Override
        public void paint(Renderer r) {
            if (component.isInvisible()) {
                if (_progress > 0) _progress -= TimeSystem.getDeltaSeconds();
                else return;
            } else {
                if (_progress < 1)
                    _progress += TimeSystem.getDeltaSeconds();
            }

            r.useBackground();
            int width = bounds.getWidth();
            int height = bounds.getHeight();

            int heightAnimated = (int) (height * _progress);

            r = r.getSubcontext(0, 0, width, heightAnimated);
            r.translate(0, heightAnimated - height);

            r.fillRoundRect(0, 0, width, height, Styling.BUTTON_WIDTH * 3);

            r.useForeground();

            layout.paintChildren(r);
        }

        @Override
        public void directive(UIDirective directive) {

        }

        @Override
        public void processEvent(UIEvent e) {

        }
    }

}
