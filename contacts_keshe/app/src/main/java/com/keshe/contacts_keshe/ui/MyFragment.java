package com.keshe.contacts_keshe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.util.SharedPreference;

public class MyFragment extends Fragment {
    SharedPreference sharedPreference;
    Button btn_change_password, btn_logout;
    TextView tv_mymobile, tv_myusername;

    public MyFragment() {
        sharedPreference = SharedPreference.getInstance();
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
            Button btn_login = (Button) view.findViewById(R.id.btn_login);
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            view = inflater.inflate(R.layout.fragment_my, container, false);
            tv_mymobile = (TextView) view.findViewById(R.id.tv_mymobile);
            tv_myusername = (TextView) view.findViewById(R.id.tv_myusername);
            String mobile = sharedPreference.getMobile(getActivity().getBaseContext());
            String username = sharedPreference.getUserName(getActivity().getBaseContext());
            tv_mymobile.setText(mobile);
            tv_myusername.setText(username);
            btn_change_password = (Button) view.findViewById(R.id.btn_change_password);
            btn_change_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getBaseContext(), ChangePasswordActivity.class);
                    startActivity(intent);
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
