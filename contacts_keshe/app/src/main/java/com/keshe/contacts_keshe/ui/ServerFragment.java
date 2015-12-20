package com.keshe.contacts_keshe.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.adapter.ContactsAdapter;
import com.keshe.contacts_keshe.bean.Contact;
import com.keshe.contacts_keshe.ui.LoginActivity;
import com.keshe.contacts_keshe.util.ContactFetcher;

import java.util.ArrayList;
import com.keshe.contacts_keshe.util.SharedPreference;
import android.content.Intent;

public class ServerFragment extends Fragment {

    ArrayList<Contact> listContacts;
    ListView listView;
    ContactsAdapter contactsAdapter;

    SharedPreference sharedPreference;
    int isLoggedin = 0;

    public ServerFragment() {
        sharedPreference = new SharedPreference();
    }

    @Override
    public void onStart() {
        super.onStart();
//        sharedPreference = new SharedPreference();
//        isLoggedin = sharedPreference.isLoggedIn(getActivity().getBaseContext());
//        if (isLoggedin==1)
//            Toast.makeText(getActivity().getBaseContext(), "helloworld", Toast.LENGTH_LONG).show();
//        else{
//            Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
//            startActivity(intent);
//        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        int isLoggedIn = sharedPreference.isLoggedIn(getActivity().getBaseContext());
        View view;
        if(isLoggedIn == 0){
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

            listContacts = new ContactFetcher(getActivity().getBaseContext()).fetchAll();

            Toast.makeText(getActivity().getBaseContext(), listContacts.toString(), Toast.LENGTH_LONG).show();
            Log.d("listcontacts", listContacts.toString());

            listView = (ListView) view.findViewById(R.id.listview_contacts);
            contactsAdapter = new ContactsAdapter(getActivity().getBaseContext(), listContacts);
            listView.setAdapter(contactsAdapter);
        }
        return view;
    }
}
