package com.keshe.contacts_keshe.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;


public class Api {
    public final static String HOST = "192.168.0.106:8001/";
    public final static String HTTP = "http://";
    public final static String BASE_URL = HTTP + HOST;

    public final static String LOGIN = BASE_URL + "login";
    public final static String REGISTER = BASE_URL + "register";
    public final static String CHANGE_PASSWORD = BASE_URL + "change_password";
    public final static String CONTACTS = BASE_URL + "contacts";

    public static void login(String mobile, String password, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("password", password);
        AsyncHttpHelp.post(LOGIN, params, handler);
    }

    public static void register(String mobile, String username, String password, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("username", username);
        params.put("password", password);
        AsyncHttpHelp.post(REGISTER, params, handler);
    }

    public static void change_password(String password, String token, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("password", password);
        AsyncHttpClient client = AsyncHttpHelp.getHttpClient();
        client.addHeader("Authorization", "Basic "+ token);
        client.post(CHANGE_PASSWORD, params, handler);
    }

    public static void getContactList(String token, AsyncHttpResponseHandler handler){
        AsyncHttpClient client = AsyncHttpHelp.getHttpClient();
        client.addHeader("Authorization", "Basic " + token);
        client.get(CONTACTS, handler);
    }

    public static void postContactList(List<String> contacts, String token, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        String contacts_str = "";
        contacts_str += contacts.get(0);
        for(int i = 1; i < contacts.size(); i++){
            contacts_str += ";" + contacts.get(i);
        }
        params.put("contacts", contacts_str);
        AsyncHttpClient client = AsyncHttpHelp.getHttpClient();
        client.addHeader("Authorization", "Basic " + token);
        client.post(CONTACTS, params, handler);
    }
}
