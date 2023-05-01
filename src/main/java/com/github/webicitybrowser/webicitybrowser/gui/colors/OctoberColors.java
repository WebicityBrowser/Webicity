package com.github.webicitybrowser.webicitybrowser.gui.colors;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.color.colors.RGBA8Color;
import com.github.webicitybrowser.thready.color.format.ColorFormat;

public class OctoberColors implements ColorPalette {

	private static final ColorFormat BACKGROUND_PRIMARY = new RGBA8Color(255, 150, 167);
	private static final ColorFormat BACKGROUND_SECONDARY = new RGBA8Color(255, 167, 179);
	private static final ColorFormat BACKGROUND_SECONDARY_HOVER = new RGBA8Color(255, 206, 206);
	private static final ColorFormat BACKGROUND_SECONDARY_SELECTED = Colors.DARK_GRAY;
	private static final ColorFormat BACKGROUND_SECONDARY_ACTIVE = new RGBA8Color(255, 180, 188);
	private static final ColorFormat BACKGROUND_SECONDARY_DANGER = new RGBA8Color(255, 191, 195);
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
