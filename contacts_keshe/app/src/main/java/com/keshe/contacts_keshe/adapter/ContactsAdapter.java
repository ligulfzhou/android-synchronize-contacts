package com.keshe.contacts_keshe.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.keshe.contacts_keshe.bean.Contact;
import com.keshe.contacts_keshe.R;

import java.util.List;

public class ContactsAdapter extends ArrayAdapter<Contact> {

    Context context;
    LayoutInflater layoutInflater;
    List<Contact> contactList;
    private SparseBooleanArray mSelectedItemsIds;

    public ContactsAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.contactList = objects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contact contact = getItem(position);

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.listview_contact, parent, false);
        }
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvPhone = (TextView) view.findViewById(R.id.tvPhone);
        tvName.setText(contact.getName());
        tvPhone.setText("");
        tvPhone.setText(contact.getNumber());
        return view;
    }

    @Override
    public void remove(Contact object) {
        contactList.remove(object);
        notifyDataSetChanged();
    }

    public List<Contact> getContactList() {
        return contactList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

//    public List<Contact> get_select_contact() {
//        List<Contact> contacts = new ArrayList<Contact>();
////        for (int i = 0; i < mSelectedItemsIds.size(); i++) {
////            for (int i = 0; i < contactList.size(); i++){
////                if(mSelectedItemsIds[i] == contactList[i].get)
////            }
////        }
//        for (int i = 0; i < mSelectedItemsIds.size(); i++){
//            mSelectedItemsIds[i].keyat
//        }
//        return contacts;
//    }
}
