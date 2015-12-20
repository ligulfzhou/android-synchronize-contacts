package com.keshe.contacts_keshe.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.api.Api;
import com.keshe.contacts_keshe.util.SharedPreference;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

public class RegisterActivity extends Activity {

    EditText et_mobile, et_username, et_password;
    Button btn_register, btn_back2LoginPage;

    SharedPreference sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sharedPreference = new SharedPreference();

        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    @Override
    protected void onStart(){
        super.onStart();
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_back2LoginPage = (Button) findViewById(R.id.btn_login);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mobile = et_mobile.getText().toString();
                final String username = et_username.getText().toString();
                final String password = et_password.getText().toString();
                if (!mobile.equals("") && !username.equals("") && !password.equals("")){
                    Api.register(mobile, username, password, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            sharedPreference.setMobile(getBaseContext(), mobile);
                            sharedPreference.setUserName(getBaseContext(), username);
                            sharedPreference.setPassword(getBaseContext(), password);
                            Toast.makeText(getBaseContext(), "注册成功,返回登陆", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getBaseContext(), "注册出错,请稍后再次尝试", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(getBaseContext(), "请正确填写表格", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
