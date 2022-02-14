package everyos.browser.webicitybrowser.gui.colors;

import com.github.anythingide.lace.basics.color.RGBA16ColorImp;
import com.github.anythingide.lace.core.color.Color;

public class NormalColors implements Colors {
	
	private static final Color BACKGROUND_PRIMARY = RGBA16ColorImp.ofRGB8(0, 119, 131);
	private static final Color BACKGROUND_SECONDARY = RGBA16ColorImp.ofRGB8(0, 178, 152);
	private static final Color BACKGROUND_SECONDARY_HOVER = RGBA16ColorImp.LIGHT_GRAY;
	private static final Color BACKGROUND_SECONDARY_SELECTED = RGBA16ColorImp.DARK_GRAY;
	private static final Color BACKGROUND_SECONDARY_ACTIVE = RGBA16ColorImp.ofRGB8(0, 151, 129);
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
