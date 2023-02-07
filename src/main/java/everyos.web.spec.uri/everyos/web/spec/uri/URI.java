package everyos.web.spec.uri;

public interface URI {

	String getScheme();
	
	String getUserInfo();
	
	String getHost();
	
	String getAuthority();
	
	String getPath();
	
	String getQuery();
	
	String getFragment();
	
	int getPort();
	
}
