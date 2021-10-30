package everyos.browser.webicitybrowser.gui.colors;

import everyos.engine.ribbon.core.graphics.Color;

public class OctoberColors implements Colors {
	
	private static final Color BACKGROUND_PRIMARY = new Color(255, 150, 167);
	private static final Color BACKGROUND_SECONDARY = new Color(255, 167, 179);
	private static final Color BACKGROUND_SECONDARY_HOVER = new Color(255, 206, 206);
	private static final Color BACKGROUND_SECONDARY_SELECTED = Color.DARK_GRAY;
	private static final Color BACKGROUND_SECONDARY_ACTIVE = new Color(255, 180, 188);
	private static final Color BACKGROUND_SECONDARY_DANGER = new Color(255, 191, 195);
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
