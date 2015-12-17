package com.keshe.contacts_keshe.api;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;


public class RestApi {
    public final static String HOST = "54.199.179.65/";
    public final static String HTTP = "http://";
    public final static String BASE_URL = HTTP + HOST;

    public final static String LOGIN = BASE_URL + "login";
    public final static String REGISTER = BASE_URL + "register";
    public final static String CHANGE_PASSWORD = BASE_URL + "change_password";
    public final static String CONTACTS = BASE_URL + "contacts";

    public static void login(String mobile, String password, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("username", mobile);
        params.put("password", password);
        AsyncHttpHelp.post(LOGIN, params, handler);
    }

    public static void register(String mobile, String username, String password, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("mobile", mobile);
        params.put("username", username);
        params.put("password", password);
        AsyncHttpHelp.get(REGISTER, params, handler);
    }

    public static void change_password(String password, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("password", password);
        AsyncHttpHelp.get(CHANGE_PASSWORD, params, handler);
    }

    public static void getContactList(AsyncHttpResponseHandler handler){
        AsyncHttpHelp.get(CONTACTS, handler);
    }

    public static void postContactList(JSONArray jsonArray, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("contacts", jsonArray.toString());
        AsyncHttpHelp.post(CONTACTS, params, handler);
    }

    public static void postContactList(int[] contact_id_list, AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("contacts", contact_id_list);
        AsyncHttpHelp.post(CONTACTS, params, handler);
    }
}
