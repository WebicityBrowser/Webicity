package everyos.web.spec.uri.util;

import everyos.web.spec.uri.builder.URLBuilder;

public final class URLBuilderUtil {

	private URLBuilderUtil() {}
	
	public static boolean hasCredentials(URLBuilder builder) {
		return
			builder.getUsername() != null ||
			builder.getPassword() != null;
	}
	
}
