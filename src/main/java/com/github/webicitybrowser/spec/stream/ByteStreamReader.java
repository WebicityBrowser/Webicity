package com.github.webicitybrowser.spec.stream;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ByteStreamReader {

	private ByteStreamReader() {

	}

	public static byte[] readAllBytes(InputStreamReader inputStream) throws IOException {
		BufferedReader br = new BufferedReader(inputStream);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		int next;
		while((next = br.read()) != -1) {
			outputStream.write(next);
		}
		return outputStream.toByteArray();
	}

}
