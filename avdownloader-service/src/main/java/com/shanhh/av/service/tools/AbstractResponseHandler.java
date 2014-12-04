package com.shanhh.av.service.tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public abstract class AbstractResponseHandler<T> implements ResponseHandler<T> {

    public static final int HTTP_UNSUCCESS_CODE = 300;

    @Override
    public T handleResponse(HttpResponse response) throws IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= HTTP_UNSUCCESS_CODE) {
            throw new HttpResponseException(statusLine.getStatusCode(), "cover not exists");
        }
        return handle(entity);
    }

    public abstract T handle(HttpEntity entity) throws IOException;
}