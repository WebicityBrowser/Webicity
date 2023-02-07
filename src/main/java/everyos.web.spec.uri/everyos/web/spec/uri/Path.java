package everyos.web.spec.uri;

import java.util.List;

public interface Path {

	boolean isOpaque();
	
	String getPathString();
	
	List<String> getPathList();
	
}
