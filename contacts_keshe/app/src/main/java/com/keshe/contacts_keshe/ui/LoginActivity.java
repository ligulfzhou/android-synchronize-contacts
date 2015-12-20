package com.keshe.contacts_keshe.ui;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
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
        sharedPreference = new SharedPreference();
    }

    @Override
    protected void onStart() {
        super.onStart();
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_password = (EditText) findViewById(R.id.et_password);

        String mobile = sharedPreference.getMobile(getBaseContext());
        if (mobile != null && !mobile.equals("")){
            et_mobile.setText(mobile);
        }
        String password = sharedPreference.getPassword(getBaseContext());
        if(password != null && !password.equals("")){
            et_password.setText(password);
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_mobile.getText().toString();
                String password = et_password.getText().toString();

                if (!mobile.equals("")  && !password.equals("")){
//                    Toast.makeText(getBaseContext(), mobile + " " + password, Toast.LENGTH_LONG).show();

                    Api.login(mobile, password, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            JSONObject jsonObject = null;
                            JSONObject json_user = null;
                            try {
                                jsonObject= new JSONObject(responseBody.toString());

//                                Log.d("userinfo", jsonObject.toString());
//                                json_user = jsonObject.getJSONObject("user");
//                                Log.d("json_user", json_user.toString());
//                                Toast.makeText(getBaseContext(), json_user.toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException e) {
                                Toast.makeText(getBaseContext(), "zhuanhuan", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
//                            String token = null;
//                            try {
//                                token = jsonObject.getString("username");
//                                Toast.makeText(getBaseContext(), "huoqu", Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            Toast.makeText(getBaseContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            Toast.makeText(getBaseContext(), "..........", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
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
