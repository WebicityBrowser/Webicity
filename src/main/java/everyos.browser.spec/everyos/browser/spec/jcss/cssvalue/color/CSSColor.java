package everyos.browser.spec.jcss.cssvalue.color;

public interface CSSColor {
	
	public static final CSSColor BLACK = ofRGB8(0, 0, 0);
	public static final CSSColor WHITE = ofRGB8(255, 255, 255);
	
	public static final CSSColor TRANSPARENT = ofRGBA8(0, 0, 0, 0);
	
	static CSSColor ofRGB8(int r, int g, int b) {
		return ofRGBA8(r, g, b, 255);
	}
	
	static CSSColor ofRGBA8(int r, int g, int b, int a) {
		return new CSSColorImp(); //TODO
	}
	
	public class CSSColorImp implements CSSColor {
		
	}

}
