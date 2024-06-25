package com.github.webicitybrowser.spec.css.property.shared.length;

public interface RelativeLengthValue extends LengthValue {

	RelativeLengthUnit getUnit();

	float getValue();
	
	public static enum RelativeLengthUnit {
		EM, EX, CAP, CH, IC, REM, LH, RLH, VW, VH, VI, VB, VMIN, VMAX
	}

}
