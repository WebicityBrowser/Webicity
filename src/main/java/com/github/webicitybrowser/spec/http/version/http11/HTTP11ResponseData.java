package com.github.webicitybrowser.spec.http.version.http11;

import com.github.webicitybrowser.spec.http.HTTPHeaders;
import com.github.webicitybrowser.spec.http.HTTPStatus;

public record HTTP11ResponseData(HTTPStatus status, HTTPHeaders headers) {

}
