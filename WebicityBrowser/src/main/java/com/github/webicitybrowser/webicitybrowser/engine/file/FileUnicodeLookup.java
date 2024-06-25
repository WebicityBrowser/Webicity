package com.github.webicitybrowser.webicitybrowser.engine.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class FileUnicodeLookup implements CharacterReferenceLookup {

	private final JsonObject json;

	public FileUnicodeLookup(InputStream stream) {
		this.json = parseJson(stream);
	}

	@Override
	public String[] getEntityNames() {
		return json.keySet().toArray(new String[json.size()]);
	}

	@Override
	public int[] getCodePointsForNamedEntity(String reference) {
		JsonArray codepointsJson = json
			.get(reference)
			.getAsJsonObject()
			.get("codepoints")
			.getAsJsonArray();
		
		int[] codepoints = new int[codepointsJson.size()];
		for (int i = 0; i < codepointsJson.size(); i++) {
			codepoints[i] = codepointsJson.get(i).getAsInt();
		}
		
		return codepoints;
	}
	
	private JsonObject parseJson(InputStream stream) {
		JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
		try {
			stream.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		json.remove("_comment");
		
		return json;
	}

}
