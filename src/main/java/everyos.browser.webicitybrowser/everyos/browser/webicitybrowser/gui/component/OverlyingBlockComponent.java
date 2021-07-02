package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.SizeDirective;
import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIDirectiveWrapper;

public class OverlyingBlockComponent extends BlockComponent {
    private final Component contentView;

    private float incremental = 0.01f;
    private boolean isInvisible = true;

    public OverlyingBlockComponent(Component contentView) {
        this.contentView = contentView;
        super.addChild(contentView);
    }

    @Override
    public void addChild(Component component) {
    }

    @Override
    public void addChild(int pos, Component component) {
    }

    @Override
    public Component directive(UIDirectiveWrapper directive) {
        if (directive.getDirective() instanceof SizeDirective)
            contentView.directive(directive);
        return super.directive(directive);
    }

    public void makeVisible() {
        isInvisible = false;
    }

    public void makeInvisible() {
        isInvisible = true;
    }

    public boolean isInvisible() {
        return isInvisible;
    }

    public float getIncremental() {
        return incremental;
    }
}
