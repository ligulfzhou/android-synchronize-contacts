package com.keshe.contacts_keshe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.api.Api;
import com.keshe.contacts_keshe.util.SharedPreference;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class ChangePasswordActivity extends Activity {

    Button btn_change_password;
    EditText et_password1, et_password2;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sharedPreference = SharedPreference.getInstance();
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);

        btn_change_password = (Button) findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getBaseContext(), et_password1.getText().toString() + " " + et_password2.getText().toString(), Toast.LENGTH_LONG).show();
                String password1 = et_password1.getText().toString();
                String password2 = et_password2.getText().toString();
                if(password1.equals(password2)){
                    String token = sharedPreference.getToken(getBaseContext());
                    Api.change_password(password1, token, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Toast.makeText(getBaseContext(), "修改密码成功, 请重新登陆", Toast.LENGTH_LONG).show();
                            sharedPreference.logOut(getBaseContext());
                            sharedPreference.clearSharedPreference(getBaseContext());
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getBaseContext(), "修改密码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
