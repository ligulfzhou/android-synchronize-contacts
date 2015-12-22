package com.keshe.contacts_keshe.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.adapter.ContactsAdapter;
import com.keshe.contacts_keshe.api.Api;
import com.keshe.contacts_keshe.bean.Contact;
import com.keshe.contacts_keshe.util.ContactFetcher;

import java.util.ArrayList;

import com.keshe.contacts_keshe.util.SharedPreference;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.content.Intent;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerFragment extends Fragment {

    ArrayList<Contact> listContacts;
    ListView listView;
    ContactsAdapter contactsAdapter;

    SharedPreference sharedPreference;

    public ServerFragment() {
        sharedPreference = SharedPreference.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        int isLoggedIn = sharedPreference.isLoggedIn(getActivity().getBaseContext());
        View view;
        if (isLoggedIn == 0) {
            view = inflater.inflate(R.layout.not_logged_in, container, false);
            Button btn_login = (Button) view.findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            view = inflater.inflate(R.layout.fragment_server, container, false);

            String token = sharedPreference.getToken(getActivity().getBaseContext());
            listContacts = new ArrayList<Contact>();
            Api.getContactList(token, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String str = new String(responseBody);
                    JSONObject jsonObject = null;
                    JSONObject temp;
                    try {
                        jsonObject = new JSONObject(str);
                        JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                        for (int i = 0; i < jsonArray.length(); i++){
                            temp = jsonArray.getJSONObject(i);
                            listContacts.add(new Contact(temp.getString("name"), temp.getString("number")));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });


//            listContacts = new ContactFetcher(getActivity().getBaseContext()).fetchAll();
//            listContacts = new ArrayList<Contact>();

            listView = (ListView) view.findViewById(R.id.listview_contacts);
            contactsAdapter = new ContactsAdapter(getActivity().getBaseContext(), R.id.listview_contacts, listContacts);
            listView.setAdapter(contactsAdapter);
        }
        return view;
    }
}
