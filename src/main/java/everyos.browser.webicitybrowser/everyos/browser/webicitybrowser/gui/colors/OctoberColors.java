package everyos.browser.webicitybrowser.gui.colors;

import com.github.webicity.lace.basics.color.RGBA16ColorImp;
import com.github.webicity.lace.core.color.Color;

public class OctoberColors implements Colors {
	
	private static final Color BACKGROUND_PRIMARY = RGBA16ColorImp.ofRGB8(255, 150, 167);
	private static final Color BACKGROUND_SECONDARY = RGBA16ColorImp.ofRGB8(255, 167, 179);
	private static final Color BACKGROUND_SECONDARY_HOVER = RGBA16ColorImp.ofRGB8(255, 206, 206);
	private static final Color BACKGROUND_SECONDARY_SELECTED = RGBA16ColorImp.DARK_GRAY;
	private static final Color BACKGROUND_SECONDARY_ACTIVE = RGBA16ColorImp.ofRGB8(255, 180, 188);
	private static final Color BACKGROUND_SECONDARY_DANGER = RGBA16ColorImp.ofRGB8(255, 191, 195);
	private static final Color FOREGROUND_PRIMARY = RGBA16ColorImp.WHITE;

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
