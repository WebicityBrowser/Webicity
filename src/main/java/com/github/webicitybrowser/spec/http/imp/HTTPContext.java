package com.github.webicitybrowser.spec.http.imp;

import java.util.Map;

import com.github.webicitybrowser.spec.http.HTTPTransferEncoding;
import com.github.webicitybrowser.spec.http.HTTPTransport;

public record HTTPContext(String userAgent, HTTPTransport transport, Map<String, HTTPTransferEncoding> transferEncodings) {

}
