package everyos.browser.jhtml.parser;

import java.io.InputStreamReader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UnicodeHelper {
	//TODO: We probably should not have a direct dependency on GSON here
	private static JsonObject json;
	
	public int[] getCodePointsForNamedEntity(String name) {
		//if (!json.has(name)) return new int[] {}; //TODO
		JsonArray codepointsA = json.get(name).getAsJsonObject().get("codepoints").getAsJsonArray();
		int[] codepoints = new int[codepointsA.size()];
		for (int i=0; i<codepointsA.size(); i++) {
			codepoints[i] = codepointsA.get(i).getAsInt();
		}
		
		return codepoints;
	}
	
	public String[] getEntityNames() {
		return json.keySet().toArray(new String[json.size()]);
	}
	
	static {
		json = JsonParser.parseReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("references.json"))).getAsJsonObject();
	}
}
