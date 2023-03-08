package everyos.parser.portalhtml;

public interface UnicodeDictionary {

	String[] getEntityNames();
	
	int[] getCodePointsForNamedEntity(String name);

}
