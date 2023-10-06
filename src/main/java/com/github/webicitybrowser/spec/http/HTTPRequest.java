package com.github.webicitybrowser.spec.http;

import com.github.webicitybrowser.spec.url.URL;

public record HTTPRequest(URL url, String method, HTTPRedirectHandler redirectHandler) {

}
