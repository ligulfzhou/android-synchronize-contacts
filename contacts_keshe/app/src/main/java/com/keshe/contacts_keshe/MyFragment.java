package com.keshe.contacts_keshe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.keshe.contacts_keshe.util.SharedPreference;

public class MyFragment extends Fragment {

    SharedPreference sharedPreference;

    Button btn_change_password, btn_logout;

    public MyFragment() {
        sharedPreference = new SharedPreference();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(sharedPreference.isLoggedIn(getActivity().getBaseContext()) == 0){
            view = inflater.inflate(R.layout.not_logged_in, container, false);
        }else{
            view = inflater.inflate(R.layout.fragment_my, container, false);
            btn_change_password = (Button) view.findViewById(R.id.btn_change_password);
            btn_change_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getBaseContext(), "llllllll", Toast.LENGTH_LONG).show();
                }
            });

            btn_logout = (Button) view.findViewById(R.id.btn_logout);
            btn_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPreference.logOut(getActivity().getBaseContext());
                }
            });
        }
        return view;
    }
}
