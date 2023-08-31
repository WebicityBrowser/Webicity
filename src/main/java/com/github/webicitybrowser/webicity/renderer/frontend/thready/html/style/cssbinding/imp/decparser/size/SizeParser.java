package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.cssbinding.imp.decparser.size;

import com.github.webicitybrowser.spec.css.property.CSSValue;
import com.github.webicitybrowser.spec.css.property.shared.length.AbsoluteLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.length.RelativeLengthValue;
import com.github.webicitybrowser.spec.css.property.shared.percentage.PercentageValue;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class SizeParser {
	
	private SizeParser() {}
	
	public static SizeCalculation parse(CSSValue value) {
		if (value instanceof AbsoluteLengthValue lengthValue) {
			float translatedValue = translateAbsoluteValue(lengthValue);
			return _1 -> translatedValue;
		} else if (value instanceof RelativeLengthValue lengthValue) {
			return translateRelativeValue(lengthValue);
		} else if (value instanceof PercentageValue percentageValue) {
			return context -> translatePercentageValue(context, percentageValue);
		}
		throw new UnsupportedOperationException("Unrecognized CSSValue: " + value);
	}

	private static float translateAbsoluteValue(AbsoluteLengthValue lengthValue) {
		float initialValue = lengthValue.getValue();
		switch (lengthValue.getUnit()) {
			case PX:
				return initialValue;
			case CM:
				return initialValue * 96 / 2.54f;
			case MM:
				return initialValue * 96 / 25.4f;
			case Q:
				return initialValue * 96 / 2.54f / 40;
			case IN:
				return initialValue * 96;
			case PC:
				return initialValue * 96 / 6;
			case PT:
				return initialValue * 96 / 72;
			default:
				throw new UnsupportedOperationException("Unrecognized AbsoluteLengthUnit: " + lengthValue.getUnit());
		}
	}

	private static SizeCalculation translateRelativeValue(RelativeLengthValue lengthValue) {
		float initialValue = lengthValue.getValue();
		switch (lengthValue.getUnit()) {
		case EM:
			return context -> context.relativeFont().getSize() * initialValue;
		case EX:
			return context -> context.relativeFont().getSize() * initialValue / 2;
		case CAP:
			return context -> context.relativeFont().getCapHeight() * initialValue;
		case CH:
			return context -> getCharacterAdvance(context, '0', .5f) * initialValue;
		case IC:
			return context -> getCharacterAdvance(context, '\u6C34', .5f) * initialValue;
		case REM:
			throw new UnsupportedOperationException("REM (Root-Relative Font Size) is not supported yet");
		case LH:
			throw new UnsupportedOperationException("LH (Line Height) is not supported yet");
		case RLH:
			throw new UnsupportedOperationException("RLH (Root-Relative Line Height) is not supported yet");
		case VW:
			return context -> context.viewportSize().width() * initialValue / 100;
		case VH:
			return context -> context.viewportSize().height() * initialValue / 100;
		case VI:
			throw new UnsupportedOperationException("VI (Viewport Inline Size) is not supported yet");
		case VB:
			throw new UnsupportedOperationException("VB (Viewport Block Size) is not supported yet");
		case VMIN:
			return context -> Math.min(context.viewportSize().width(), context.viewportSize().height()) * initialValue / 100;
		case VMAX:
			return context -> Math.max(context.viewportSize().width(), context.viewportSize().height()) * initialValue / 100;
		default:
			throw new UnsupportedOperationException("Unrecognized RelativeLengthUnit: " + lengthValue.getUnit());
		}
	}

	private static float translatePercentageValue(SizeCalculationContext context, PercentageValue percentageValue) {
		float axisValue = context.isHorizontal() ?
			context.parentSize().width() :
			context.parentSize().height();
		if (axisValue == RelativeDimension.UNBOUNDED) return RelativeDimension.UNBOUNDED;
		return axisValue * percentageValue.getValue() / 100;
	}

	private static float getCharacterAdvance(SizeCalculationContext context, char c, float fallback) {
		// TODO: Detect inline axis
		float zeroWidth = context.relativeFont().getCharacterWidth('0');
		if (zeroWidth > 0) return zeroWidth;
		return context.relativeFont().getHeight() * fallback;
	}

}
