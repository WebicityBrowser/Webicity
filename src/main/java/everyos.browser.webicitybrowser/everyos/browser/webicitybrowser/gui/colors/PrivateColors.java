package everyos.browser.webicitybrowser.gui.colors;

import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.colors.RGBA8Color;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;

public class PrivateColors implements ColorPalette {

	private static final ColorFormat BACKGROUND_PRIMARY = new RGBA8Color(217, 70, 70);
	private static final ColorFormat BACKGROUND_SECONDARY = new RGBA8Color(255, 82, 82);
	private static final ColorFormat BACKGROUND_SECONDARY_HOVER = Colors.LIGHT_GRAY;
	private static final ColorFormat BACKGROUND_SECONDARY_SELECTED = Colors.DARK_GRAY;
	private static final ColorFormat BACKGROUND_SECONDARY_ACTIVE = new RGBA8Color(253, 124, 124);
	private static final ColorFormat BACKGROUND_SECONDARY_DANGER = new RGBA8Color(178, 0, 0);
	private static final ColorFormat FOREGROUND_PRIMARY = Colors.WHITE;

	@Override
	public ColorFormat getBackgroundPrimary() {
		return BACKGROUND_PRIMARY;
	}

	@Override
	public ColorFormat getBackgroundSecondary() {
		return BACKGROUND_SECONDARY;
	}

	@Override
	public ColorFormat getBackgroundSecondaryHover() {
		return BACKGROUND_SECONDARY_HOVER;
	}

	@Override
	public ColorFormat getBackgroundSecondarySelected() {
		return BACKGROUND_SECONDARY_SELECTED;
	}

	@Override
	public ColorFormat getBackgroundSecondaryActive() {
		return BACKGROUND_SECONDARY_ACTIVE;
	}

	@Override
	public ColorFormat getBackgroundSecondaryDanger() {
		return BACKGROUND_SECONDARY_DANGER;
	}

	@Override
	public ColorFormat getForegroundPrimary() {
		return FOREGROUND_PRIMARY;
	}

}
