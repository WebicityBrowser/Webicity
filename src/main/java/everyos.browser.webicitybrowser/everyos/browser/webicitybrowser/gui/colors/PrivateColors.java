package everyos.browser.webicitybrowser.gui.colors;

import everyos.engine.ribbon.core.graphics.paintfill.Color;

public class PrivateColors implements Colors {
	
	private static final Color BACKGROUND_PRIMARY = Color.of(217, 70, 70);
	private static final Color BACKGROUND_SECONDARY = Color.of(255, 82, 82);
	private static final Color BACKGROUND_SECONDARY_HOVER = Color.LIGHT_GRAY;
	private static final Color BACKGROUND_SECONDARY_SELECTED = Color.DARK_GRAY;
	private static final Color BACKGROUND_SECONDARY_ACTIVE = Color.of(253, 124, 124);
	private static final Color BACKGROUND_SECONDARY_DANGER = Color.of(178, 0, 0);
	private static final Color FOREGROUND_PRIMARY = Color.WHITE;

	@Override
	public Color getBackgroundPrimary() {
		return BACKGROUND_PRIMARY;
	}

	@Override
	public Color getBackgroundSecondary() {
		return BACKGROUND_SECONDARY;
	}

	@Override
	public Color getBackgroundSecondaryHover() {
		return BACKGROUND_SECONDARY_HOVER;
	}

	@Override
	public Color getBackgroundSecondarySelected() {
		return BACKGROUND_SECONDARY_SELECTED;
	}

	@Override
	public Color getBackgroundSecondaryActive() {
		return BACKGROUND_SECONDARY_ACTIVE;
	}

	@Override
	public Color getBackgroundSecondaryDanger() {
		return BACKGROUND_SECONDARY_DANGER;
	}

	@Override
	public Color getForegroundPrimary() {
		return FOREGROUND_PRIMARY;
	}

}
