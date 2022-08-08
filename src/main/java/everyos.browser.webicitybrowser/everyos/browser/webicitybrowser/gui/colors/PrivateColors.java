package everyos.browser.webicitybrowser.gui.colors;

import com.github.webicity.lace.basics.color.RGBA16ColorImp;
import com.github.webicity.lace.core.color.Color;

public class PrivateColors implements Colors {
	
	private static final Color BACKGROUND_PRIMARY = RGBA16ColorImp.ofRGB8(217, 70, 70);
	private static final Color BACKGROUND_SECONDARY = RGBA16ColorImp.ofRGB8(255, 82, 82);
	private static final Color BACKGROUND_SECONDARY_HOVER = RGBA16ColorImp.LIGHT_GRAY;
	private static final Color BACKGROUND_SECONDARY_SELECTED = RGBA16ColorImp.DARK_GRAY;
	private static final Color BACKGROUND_SECONDARY_ACTIVE = RGBA16ColorImp.ofRGB8(253, 124, 124);
	private static final Color BACKGROUND_SECONDARY_DANGER = RGBA16ColorImp.ofRGB8(178, 0, 0);
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
