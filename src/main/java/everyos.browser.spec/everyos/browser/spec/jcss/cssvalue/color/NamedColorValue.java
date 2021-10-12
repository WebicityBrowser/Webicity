package everyos.browser.spec.jcss.cssvalue.color;

import java.util.HashMap;
import java.util.Map;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.IdentToken;
import everyos.engine.ribbon.core.graphics.Color;

public class NamedColorValue {
	
	private static Map<String, Color> colors = new HashMap<>();
	
	public static ValueParseInfo<Color> parse(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof IdentToken)) {
			return ValueParseInfo.<Color>empty();
		}
		
		String colorName = ((IdentToken) cssTokens[off]).getValue();
		
		Color color = colors.getOrDefault(colorName, null);
		
		if (color == null) {
			return ValueParseInfo.<Color>empty();
		} else {
			return new ValueParseInfo<Color>(color, 1);
		}
	}
	
	
	private static final String[] colorNames = new String[] {
			"aliceblue", "antiquewhite", "aqua", "aquamarine", "azure",
			"beige", "bisque", "black", "blanchedalmond", "blue",
			"blueviolet", "brown", "burlywood", "cadetblue", "chartreuse",
			"chocolate", "coral", "cornflowerblue", "cornsilk", "crimson",
			"cyan", "darkblue", "darkcyan", "darkgoldenrod", "darkgray",
			"darkgreen", "darkgrey", "darkkhaki", "darkmagenta", "darkolivegreen",
			"darkorange", "darkorchid", "darkred", "darksalmon", "darkseagreen",
			"darkslateblue", "darkslategray", "darkslategrey", "darkturquoise", "darkviolet",
			"deeppink", "deepskyblue", "dimgray", "dimgrey", "dodgerblue",
			"firebrick", "floralwhite", "forestgreen", "fuchsia", "gainsboro",
			"ghostwhite", "gold", "goldenrod", "gray", "green",
			"greenyellow", "grey", "honeydew", "hotpink", "indianred",
			"indigo", "ivory", "khaki", "lavender", "lavenderblush",
			"lawngreen", "lemonchiffon", "lightblue", "lightcoral", "lightcyan",
			"lightgoldenrodyellow", "lightgray", "lightgreen", "lightgrey", "lightpink",
			"lightsalmon", "lightseagreen", "lightskyblue", "lightslategray", "lightslategrey",
			"lightsteelblue", "lightyellow", "lime", "limegreen", "linen",
			"magenta", "maroon", "mediumaquamarine", "mediumblue", "mediumorchid",
			"mediumpurple", "mediumseagreen", "mediumslateblue", "mediumspringgreen", "mediumturquoise",
			"mediumvioletred", "midnightblue", "mintcream", "mistyrose", "moccasin",
			"navajowhite", "navy", "oldlace", "olive", "olivedrab",
			"orange", "orangered", "orchid", "palegoldenrod", "palegreen",
			"paleturquoise", "palevioletred", "papayawhip", "peachpuff", "peru",
			"pink", "plum", "powderblue", "purple", "rebeccapurple",
			"red", "rosybrown", "royalblue", "saddlebrown", "salmon",
			"sandybrown", "seagreen", "seashell", "sienna", "silver",
			"skyblue", "slateblue", "slategray", "slategrey", "snow",
			"springgreen", "steelblue", "tan", "teal", "thistle",
			"tomato", "turquoise", "violet", "wheat", "white",
			"whitesmoke", "yellow", "yellowgreen"
	};
	
	private static final Color[] colorValues = new Color[] {
			new Color(240, 248, 255), new Color(250, 235, 215), new Color(0, 255, 255), new Color(127, 255, 212), new Color(240, 255, 255),
			new Color(245, 245, 220), new Color(255, 228, 196), new Color(0, 0, 0), new Color(255, 235, 205), new Color(0, 0, 255),
			new Color(138, 43, 226), new Color(165, 42, 42), new Color(222, 184, 135), new Color(95, 158, 160), new Color(127, 255, 0),
			new Color(210, 105, 30), new Color(255, 127, 80), new Color(100, 149, 237), new Color(255, 248, 220), new Color(220, 20, 60),
			new Color(0, 255, 255), new Color(0, 0, 139), new Color(0, 139, 139), new Color(184, 134, 11), new Color(169, 169, 169),
			new Color(0, 100, 0), new Color(169, 169, 169), new Color(189, 183, 107), new Color(139, 0, 139), new Color(85, 107, 47),
			new Color(255, 140, 0), new Color(153, 50, 204), new Color(139, 0, 0), new Color(233, 150, 122), new Color(143, 188, 143),
			new Color(72, 61, 139), new Color(47, 79, 79), new Color(47, 79, 79), new Color(0, 206, 209), new Color(148, 0, 211),
			new Color(255, 20, 147), new Color(0, 191, 255), new Color(105, 105, 105), new Color(105, 105, 105), new Color(30, 144, 255),
			new Color(178, 34, 34), new Color(255, 250, 240), new Color(34, 139, 34), new Color(255, 0, 255), new Color(220, 220, 220),
			new Color(248, 248, 255), new Color(255, 215, 0), new Color(218, 165, 32), new Color(128, 128, 128), new Color(0, 128, 0),
			new Color(173, 255, 47), new Color(128, 128, 128), new Color(240, 255, 240), new Color(255, 105, 180), new Color(205, 92, 92),
			new Color(75, 0, 130), new Color(255, 255, 240), new Color(240, 230, 140), new Color(230, 230, 250), new Color(255, 240, 245),
			new Color(124, 252, 0), new Color(255, 250, 205), new Color(173, 216, 230), new Color(240, 128, 128), new Color(224, 255, 255),
			new Color(250, 250, 210), new Color(211, 211, 211), new Color(144, 238, 144), new Color(211, 211, 211), new Color(255, 182, 193),
			new Color(255, 160, 122), new Color(32, 178, 170), new Color(135, 206, 250), new Color(119, 136, 153), new Color(119, 136, 153),
			new Color(176, 196, 222), new Color(255, 255, 224), new Color(0, 255, 0), new Color(50, 205, 50), new Color(250, 240, 230),
			new Color(255, 0, 255), new Color(128, 0, 0), new Color(102, 205, 170), new Color(0, 0, 205), new Color(186, 85, 211),
			new Color(147, 112, 219), new Color(60, 179, 113), new Color(123, 104, 238), new Color(0, 250, 154), new Color(72, 209, 204),
			new Color(199, 21, 133), new Color(25, 25, 112), new Color(245, 255, 250), new Color(255, 228, 225), new Color(255, 228, 181),
			new Color(255, 222, 173), new Color(0, 0, 128), new Color(253, 245, 230), new Color(128, 128, 0), new Color(107, 142, 35),
			new Color(255, 165, 0), new Color(255, 69, 0), new Color(218, 112, 214), new Color(238, 232, 170), new Color(152, 251, 152),
			new Color(175, 238, 238), new Color(219, 112, 147), new Color(255, 239, 213), new Color(255, 218, 185), new Color(205, 133, 63),
			new Color(255, 192, 203), new Color(221, 160, 221), new Color(176, 224, 230), new Color(128, 0, 128), new Color(102, 51, 153),
			new Color(255, 0, 0), new Color(188, 143, 143), new Color(65, 105, 225), new Color(139, 69, 19), new Color(250, 128, 114),
			new Color(244, 164, 96), new Color(46, 139, 87), new Color(255, 245, 238), new Color(160, 82, 45), new Color(192, 192, 192),
			new Color(135, 206, 235), new Color(106, 90, 205), new Color(112, 128, 144), new Color(112, 128, 144), new Color(255, 250, 250),
			new Color(0, 255, 127), new Color(70, 130, 180), new Color(210, 180, 140), new Color(0, 128, 128), new Color(216, 191, 216),
			new Color(255, 99, 71), new Color(64, 224, 208), new Color(238, 130, 238), new Color(245, 222, 179), new Color(255, 255, 255),
			new Color(245, 245, 245), new Color(255, 255, 0), new Color(154, 205, 50)
	};
	
	static {
		for (int i = 0; i < colorNames.length; i++) {
			colors.put(colorNames[i], colorValues[i]);
		}
	}
}
