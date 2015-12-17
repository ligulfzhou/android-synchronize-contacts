package com.keshe.contacts_keshe.api;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by ubuntu on 15-6-3.
 */

public class AsyncHttpHelp {

    public final static int TIMEOUT_CONNECTION = 20000;// 连接超时时间
    public final static int TIMEOUT_SOCKET = 20000;// socket超时

//    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    public static AsyncHttpClient getHttpClient(){
        AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(TIMEOUT_CONNECTION);
        client.setResponseTimeout(TIMEOUT_SOCKET);
        return client;
    }

    public static void get(String url, AsyncHttpResponseHandler handler) {
        getHttpClient().get(url, handler);
        log(new StringBuilder("GET ").append(url).toString());
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        getHttpClient().get(url, params, handler);
        log(new StringBuilder("GET ").append(url).append("?").append(params).toString());
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        getHttpClient().post(url, params, handler);
        log(new StringBuilder("POST ").append(url).append("?").append(params).toString());
    }

    public static void post(String url, AsyncHttpResponseHandler handler) {
        getHttpClient().post(url, handler);
        log(new StringBuilder("POST ").append(url).append("?").toString());
    }

    private static void log(String log) {
        Log.d("http", log);
    }

}