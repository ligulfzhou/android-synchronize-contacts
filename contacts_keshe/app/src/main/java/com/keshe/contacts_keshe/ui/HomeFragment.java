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
import android.widget.ListView;
import android.widget.Toast;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.adapter.ContactsAdapter;
import com.keshe.contacts_keshe.api.Api;
import com.keshe.contacts_keshe.bean.Contact;
import com.keshe.contacts_keshe.util.ContactFetcher;
import com.keshe.contacts_keshe.util.SharedPreference;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ArrayList<Contact> listContacts;
    ListView listView;
    ContactsAdapter contactsAdapter;

    SharedPreference sharedPreference;
    int isLoggedin = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = SharedPreference.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listContacts = new ContactFetcher(getActivity().getBaseContext()).fetchAll();

        Log.d("listcontacts", listContacts.toString());

        listView = (ListView) view.findViewById(R.id.listview_contacts);
        contactsAdapter = new ContactsAdapter(getActivity().getBaseContext(), R.id.listview_contacts, listContacts);
        listView.setAdapter(contactsAdapter);

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode,
                                                  int position, long id, boolean checked) {
                final int checkedCount = listView.getCheckedItemCount();
                mode.setTitle("选中" + checkedCount + "个");
                // Calls toggleSelection method from ListViewAdapter Class
                contactsAdapter.toggleSelection(position);
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.upload:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = contactsAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop

//                        for (int i = (selected.size() - 1); i >= 0; i--) {
//                            if (selected.valueAt(i)) {
//                                Contact selecteditem = contactsAdapter.getItem(selected.keyAt(i));
//                                // Remove selected items following the ids
//                                contactsAdapter.remove(selecteditem);
//                            }
//                        }

                        List<String> contacts = new ArrayList<String>();

                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Contact selecteditem = contactsAdapter.getItem(selected.keyAt(i));
                                contacts.add(selecteditem.toString());
                            }
                        }
//                        Toast.makeText(getActivity().getBaseContext(), contacts.toString(), Toast.LENGTH_LONG).show();
                        String token = sharedPreference.getToken(getActivity().getBaseContext());
                        Api.postContactList(contacts, token, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String str = new String(responseBody);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(str);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getActivity().getBaseContext(), "上传成功", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                String str = new String(responseBody);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(str);
                                    Toast.makeText(getActivity().getBaseContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.activity_main, menu);
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                contactsAdapter.removeSelection();
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });

        return view;
    }
}
