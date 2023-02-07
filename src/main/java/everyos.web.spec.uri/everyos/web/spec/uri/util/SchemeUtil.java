package everyos.web.spec.uri.util;

public final class SchemeUtil {
	
	private static final String[] SPECIAL_SCHEMES = new String[] {
		"ftp", "file", "http", "https", "ws", "wss"
	};
	
	private static final int[] SPECIAL_PORTS = new int[] {
			
	};

	private SchemeUtil() {}

	public static boolean isSpecialScheme(String scheme) {
		for (String specialScheme: SPECIAL_SCHEMES) {
			if (scheme.equals(specialScheme)) {
				return true;
			}
		}
		
		return false;
	}

	public static int getDefaultPort(String scheme) {
		for (int i = 0; i < SPECIAL_SCHEMES.length; i++) {
			if (scheme.equals(SPECIAL_SCHEMES[i])) {
				return SPECIAL_PORTS[i];
			}
		}
		
		return -1;
	}
	
}
