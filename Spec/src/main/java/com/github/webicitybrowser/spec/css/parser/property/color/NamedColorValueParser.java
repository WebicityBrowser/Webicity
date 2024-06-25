package com.github.webicitybrowser.spec.css.parser.property.color;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class NamedColorValueParser implements PropertyValueParser<ColorValue> {

	private static Map<String, ColorValue> colors = new HashMap<>();

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length < 1 || !(tokens[offset] instanceof IdentToken)) {
			return PropertyValueParseResultImp.empty();
		}

		String name = ((IdentToken) tokens[offset]).getValue();
		ColorValue color = colors.get(name);

		if (color != null) {
			return PropertyValueParseResultImp.of(color, 1);
		}

		return PropertyValueParseResultImp.empty();
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
	
	private static final ColorValue[] colorValues = new ColorValue[] {
		new RGBColorValueImp(240, 248, 255), new RGBColorValueImp(250, 235, 215), new RGBColorValueImp(0, 255, 255), new RGBColorValueImp(127, 255, 212), new RGBColorValueImp(240, 255, 255),
		new RGBColorValueImp(245, 245, 220), new RGBColorValueImp(255, 228, 196), new RGBColorValueImp(0, 0, 0), new RGBColorValueImp(255, 235, 205), new RGBColorValueImp(0, 0, 255),
		new RGBColorValueImp(138, 43, 226), new RGBColorValueImp(165, 42, 42), new RGBColorValueImp(222, 184, 135), new RGBColorValueImp(95, 158, 160), new RGBColorValueImp(127, 255, 0),
		new RGBColorValueImp(210, 105, 30), new RGBColorValueImp(255, 127, 80), new RGBColorValueImp(100, 149, 237), new RGBColorValueImp(255, 248, 220), new RGBColorValueImp(220, 20, 60),
		new RGBColorValueImp(0, 255, 255), new RGBColorValueImp(0, 0, 139), new RGBColorValueImp(0, 139, 139), new RGBColorValueImp(184, 134, 11), new RGBColorValueImp(169, 169, 169),
		new RGBColorValueImp(0, 100, 0), new RGBColorValueImp(169, 169, 169), new RGBColorValueImp(189, 183, 107), new RGBColorValueImp(139, 0, 139), new RGBColorValueImp(85, 107, 47),
		new RGBColorValueImp(255, 140, 0), new RGBColorValueImp(153, 50, 204), new RGBColorValueImp(139, 0, 0), new RGBColorValueImp(233, 150, 122), new RGBColorValueImp(143, 188, 143),
		new RGBColorValueImp(72, 61, 139), new RGBColorValueImp(47, 79, 79), new RGBColorValueImp(47, 79, 79), new RGBColorValueImp(0, 206, 209), new RGBColorValueImp(148, 0, 211),
		new RGBColorValueImp(255, 20, 147), new RGBColorValueImp(0, 191, 255), new RGBColorValueImp(105, 105, 105), new RGBColorValueImp(105, 105, 105), new RGBColorValueImp(30, 144, 255),
		new RGBColorValueImp(178, 34, 34), new RGBColorValueImp(255, 250, 240), new RGBColorValueImp(34, 139, 34), new RGBColorValueImp(255, 0, 255), new RGBColorValueImp(220, 220, 220),
		new RGBColorValueImp(248, 248, 255), new RGBColorValueImp(255, 215, 0), new RGBColorValueImp(218, 165, 32), new RGBColorValueImp(128, 128, 128), new RGBColorValueImp(0, 128, 0),
		new RGBColorValueImp(173, 255, 47), new RGBColorValueImp(128, 128, 128), new RGBColorValueImp(240, 255, 240), new RGBColorValueImp(255, 105, 180), new RGBColorValueImp(205, 92, 92),
		new RGBColorValueImp(75, 0, 130), new RGBColorValueImp(255, 255, 240), new RGBColorValueImp(240, 230, 140), new RGBColorValueImp(230, 230, 250), new RGBColorValueImp(255, 240, 245),
		new RGBColorValueImp(124, 252, 0), new RGBColorValueImp(255, 250, 205), new RGBColorValueImp(173, 216, 230), new RGBColorValueImp(240, 128, 128), new RGBColorValueImp(224, 255, 255),
		new RGBColorValueImp(250, 250, 210), new RGBColorValueImp(211, 211, 211), new RGBColorValueImp(144, 238, 144), new RGBColorValueImp(211, 211, 211), new RGBColorValueImp(255, 182, 193),
		new RGBColorValueImp(255, 160, 122), new RGBColorValueImp(32, 178, 170), new RGBColorValueImp(135, 206, 250), new RGBColorValueImp(119, 136, 153), new RGBColorValueImp(119, 136, 153),
		new RGBColorValueImp(176, 196, 222), new RGBColorValueImp(255, 255, 224), new RGBColorValueImp(0, 255, 0), new RGBColorValueImp(50, 205, 50), new RGBColorValueImp(250, 240, 230),
		new RGBColorValueImp(255, 0, 255), new RGBColorValueImp(128, 0, 0), new RGBColorValueImp(102, 205, 170), new RGBColorValueImp(0, 0, 205), new RGBColorValueImp(186, 85, 211),
		new RGBColorValueImp(147, 112, 219), new RGBColorValueImp(60, 179, 113), new RGBColorValueImp(123, 104, 238), new RGBColorValueImp(0, 250, 154), new RGBColorValueImp(72, 209, 204),
		new RGBColorValueImp(199, 21, 133), new RGBColorValueImp(25, 25, 112), new RGBColorValueImp(245, 255, 250), new RGBColorValueImp(255, 228, 225), new RGBColorValueImp(255, 228, 181),
		new RGBColorValueImp(255, 222, 173), new RGBColorValueImp(0, 0, 128), new RGBColorValueImp(253, 245, 230), new RGBColorValueImp(128, 128, 0), new RGBColorValueImp(107, 142, 35),
		new RGBColorValueImp(255, 165, 0), new RGBColorValueImp(255, 69, 0), new RGBColorValueImp(218, 112, 214), new RGBColorValueImp(238, 232, 170), new RGBColorValueImp(152, 251, 152),
		new RGBColorValueImp(175, 238, 238), new RGBColorValueImp(219, 112, 147), new RGBColorValueImp(255, 239, 213), new RGBColorValueImp(255, 218, 185), new RGBColorValueImp(205, 133, 63),
		new RGBColorValueImp(255, 192, 203), new RGBColorValueImp(221, 160, 221), new RGBColorValueImp(176, 224, 230), new RGBColorValueImp(128, 0, 128), new RGBColorValueImp(102, 51, 153),
		new RGBColorValueImp(255, 0, 0), new RGBColorValueImp(188, 143, 143), new RGBColorValueImp(65, 105, 225), new RGBColorValueImp(139, 69, 19), new RGBColorValueImp(250, 128, 114),
		new RGBColorValueImp(244, 164, 96), new RGBColorValueImp(46, 139, 87), new RGBColorValueImp(255, 245, 238), new RGBColorValueImp(160, 82, 45), new RGBColorValueImp(192, 192, 192),
		new RGBColorValueImp(135, 206, 235), new RGBColorValueImp(106, 90, 205), new RGBColorValueImp(112, 128, 144), new RGBColorValueImp(112, 128, 144), new RGBColorValueImp(255, 250, 250),
		new RGBColorValueImp(0, 255, 127), new RGBColorValueImp(70, 130, 180), new RGBColorValueImp(210, 180, 140), new RGBColorValueImp(0, 128, 128), new RGBColorValueImp(216, 191, 216),
		new RGBColorValueImp(255, 99, 71), new RGBColorValueImp(64, 224, 208), new RGBColorValueImp(238, 130, 238), new RGBColorValueImp(245, 222, 179), new RGBColorValueImp(255, 255, 255),
		new RGBColorValueImp(245, 245, 245), new RGBColorValueImp(255, 255, 0), new RGBColorValueImp(154, 205, 50)
	};
	
	static {
		for (int i = 0; i < colorNames.length; i++) {
			colors.put(colorNames[i], colorValues[i]);
		}
	}

}
