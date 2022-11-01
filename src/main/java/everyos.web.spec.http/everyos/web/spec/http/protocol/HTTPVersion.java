package everyos.web.spec.http.protocol;

public interface HTTPVersion {

	int getMajorVersion();
	
	int getMinorVersion();
	
	String getVersionString();
	
}
