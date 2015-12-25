package com.keshe.contacts_keshe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keshe.contacts_keshe.R;
import com.keshe.contacts_keshe.api.Api;
import com.keshe.contacts_keshe.util.SharedPreference;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    SharedPreference sharedPreference;
    EditText et_password, et_mobile;
    Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreference = SharedPreference.getInstance();;
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);

        String mobile = sharedPreference.getMobile(getBaseContext());
        if (mobile != null && !mobile.equals("")) {
            et_mobile.setText(mobile);
        }
        String password = sharedPreference.getPassword(getBaseContext());
        if (password != null && !password.equals("")) {
            et_password.setText(password);
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_mobile.getText().toString();
                final String password = et_password.getText().toString();

                if (!mobile.equals("") && !password.equals("")) {
//                    Toast.makeText(getBaseContext(), mobile + " " + password, Toast.LENGTH_LONG).show();

                    Api.login(mobile, password, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String str = new String(responseBody);
                            JSONObject jsonObject = null, jsonObject1 = null;
                            try {
                                jsonObject = new JSONObject(str);
                                jsonObject1 = jsonObject.getJSONObject("user");

                                sharedPreference.setMobile(getBaseContext(), jsonObject1.get("mobile").toString());
                                sharedPreference.setUserName(getBaseContext(), jsonObject1.get("username").toString());
                                sharedPreference.setPassword(getBaseContext(), password);
                                sharedPreference.setToken(getBaseContext(), jsonObject1.get("token").toString());

                                sharedPreference.logIn(getBaseContext());

                                Toast.makeText(getBaseContext(), "登陆成功", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            Toast.makeText(getBaseContext(), str, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getBaseContext(), "登陆失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Toast.makeText(getBaseContext(), "请填写手机号和密码", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
