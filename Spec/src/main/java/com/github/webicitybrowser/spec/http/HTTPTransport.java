package com.github.webicitybrowser.spec.http;

import java.io.InputStream;
import java.io.OutputStream;

public record HTTPTransport(InputStream inputStream, OutputStream outputStream) {

}
