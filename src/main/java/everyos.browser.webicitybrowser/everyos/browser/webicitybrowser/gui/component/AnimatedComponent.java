package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.components.component.BlockComponent;

public class AnimatedComponent extends BlockComponent {
    private boolean isVisible = false;

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public boolean isVisible() {
        return isVisible;
    }
}
