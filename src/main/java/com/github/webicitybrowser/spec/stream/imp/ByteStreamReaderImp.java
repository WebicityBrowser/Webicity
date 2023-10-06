package com.github.webicitybrowser.spec.stream.imp;

import com.github.webicitybrowser.spec.stream.ByteStreamReader;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ByteStreamReaderImp implements ByteStreamReader {

	@Override
	public byte[] readAllBytes(InputStreamReader inputStream) throws IOException {
		BufferedReader br = new BufferedReader(inputStream);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		int next;
		while((next = br.read()) != -1) {
			outputStream.write(next);
		}
		return outputStream.toByteArray();
	}

}
