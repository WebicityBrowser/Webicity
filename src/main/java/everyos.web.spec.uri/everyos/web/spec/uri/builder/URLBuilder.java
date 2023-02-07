package everyos.web.spec.uri.builder;

public class URLBuilder {

	private String scheme;
	private String username;
	private String password;
	private String host = "";
	private int port = -1;
	private String path;
	
	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
	
	public String getScheme() {
		return this.scheme;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public String getHost() {
		return this.host;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}

}
