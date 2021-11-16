package everyos.browser.spec.jcss.cssvalue.color;

import java.util.HashMap;
import java.util.Map;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.IdentToken;
import everyos.engine.ribbon.core.graphics.paintfill.Color;

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
			Color.of(240, 248, 255), Color.of(250, 235, 215), Color.of(0, 255, 255), Color.of(127, 255, 212), Color.of(240, 255, 255),
			Color.of(245, 245, 220), Color.of(255, 228, 196), Color.of(0, 0, 0), Color.of(255, 235, 205), Color.of(0, 0, 255),
			Color.of(138, 43, 226), Color.of(165, 42, 42), Color.of(222, 184, 135), Color.of(95, 158, 160), Color.of(127, 255, 0),
			Color.of(210, 105, 30), Color.of(255, 127, 80), Color.of(100, 149, 237), Color.of(255, 248, 220), Color.of(220, 20, 60),
			Color.of(0, 255, 255), Color.of(0, 0, 139), Color.of(0, 139, 139), Color.of(184, 134, 11), Color.of(169, 169, 169),
			Color.of(0, 100, 0), Color.of(169, 169, 169), Color.of(189, 183, 107), Color.of(139, 0, 139), Color.of(85, 107, 47),
			Color.of(255, 140, 0), Color.of(153, 50, 204), Color.of(139, 0, 0), Color.of(233, 150, 122), Color.of(143, 188, 143),
			Color.of(72, 61, 139), Color.of(47, 79, 79), Color.of(47, 79, 79), Color.of(0, 206, 209), Color.of(148, 0, 211),
			Color.of(255, 20, 147), Color.of(0, 191, 255), Color.of(105, 105, 105), Color.of(105, 105, 105), Color.of(30, 144, 255),
			Color.of(178, 34, 34), Color.of(255, 250, 240), Color.of(34, 139, 34), Color.of(255, 0, 255), Color.of(220, 220, 220),
			Color.of(248, 248, 255), Color.of(255, 215, 0), Color.of(218, 165, 32), Color.of(128, 128, 128), Color.of(0, 128, 0),
			Color.of(173, 255, 47), Color.of(128, 128, 128), Color.of(240, 255, 240), Color.of(255, 105, 180), Color.of(205, 92, 92),
			Color.of(75, 0, 130), Color.of(255, 255, 240), Color.of(240, 230, 140), Color.of(230, 230, 250), Color.of(255, 240, 245),
			Color.of(124, 252, 0), Color.of(255, 250, 205), Color.of(173, 216, 230), Color.of(240, 128, 128), Color.of(224, 255, 255),
			Color.of(250, 250, 210), Color.of(211, 211, 211), Color.of(144, 238, 144), Color.of(211, 211, 211), Color.of(255, 182, 193),
			Color.of(255, 160, 122), Color.of(32, 178, 170), Color.of(135, 206, 250), Color.of(119, 136, 153), Color.of(119, 136, 153),
			Color.of(176, 196, 222), Color.of(255, 255, 224), Color.of(0, 255, 0), Color.of(50, 205, 50), Color.of(250, 240, 230),
			Color.of(255, 0, 255), Color.of(128, 0, 0), Color.of(102, 205, 170), Color.of(0, 0, 205), Color.of(186, 85, 211),
			Color.of(147, 112, 219), Color.of(60, 179, 113), Color.of(123, 104, 238), Color.of(0, 250, 154), Color.of(72, 209, 204),
			Color.of(199, 21, 133), Color.of(25, 25, 112), Color.of(245, 255, 250), Color.of(255, 228, 225), Color.of(255, 228, 181),
			Color.of(255, 222, 173), Color.of(0, 0, 128), Color.of(253, 245, 230), Color.of(128, 128, 0), Color.of(107, 142, 35),
			Color.of(255, 165, 0), Color.of(255, 69, 0), Color.of(218, 112, 214), Color.of(238, 232, 170), Color.of(152, 251, 152),
			Color.of(175, 238, 238), Color.of(219, 112, 147), Color.of(255, 239, 213), Color.of(255, 218, 185), Color.of(205, 133, 63),
			Color.of(255, 192, 203), Color.of(221, 160, 221), Color.of(176, 224, 230), Color.of(128, 0, 128), Color.of(102, 51, 153),
			Color.of(255, 0, 0), Color.of(188, 143, 143), Color.of(65, 105, 225), Color.of(139, 69, 19), Color.of(250, 128, 114),
			Color.of(244, 164, 96), Color.of(46, 139, 87), Color.of(255, 245, 238), Color.of(160, 82, 45), Color.of(192, 192, 192),
			Color.of(135, 206, 235), Color.of(106, 90, 205), Color.of(112, 128, 144), Color.of(112, 128, 144), Color.of(255, 250, 250),
			Color.of(0, 255, 127), Color.of(70, 130, 180), Color.of(210, 180, 140), Color.of(0, 128, 128), Color.of(216, 191, 216),
			Color.of(255, 99, 71), Color.of(64, 224, 208), Color.of(238, 130, 238), Color.of(245, 222, 179), Color.of(255, 255, 255),
			Color.of(245, 245, 245), Color.of(255, 255, 0), Color.of(154, 205, 50)
	};
	
	static {
		for (int i = 0; i < colorNames.length; i++) {
			colors.put(colorNames[i], colorValues[i]);
		}
	}
}
