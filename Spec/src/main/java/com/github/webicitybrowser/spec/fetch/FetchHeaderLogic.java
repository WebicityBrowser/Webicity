package com.github.webicitybrowser.spec.fetch;

import java.util.ArrayList;
import java.util.List;

public final class FetchHeaderLogic {
	
	private FetchHeaderLogic() {
	}
	
	public static List<String> extractHeaderListValues(String headerName, FetchHeaderList headerList) {
		if (headerList.getHeaderValue(headerName) == null) {
			return null;
		}

		// TODO: More precise extraction
		List<String> values = new ArrayList<>();
		String headerValue = headerList.getHeaderValue(headerName);
		String[] splitValues = headerValue.split(",");
		for (String value : splitValues) {
			values.add(value.trim());
		}

		return values;
	}

}
