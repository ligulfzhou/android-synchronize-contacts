package com.keshe.contacts_keshe;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.keshe.contacts_keshe.api.RestApi;
import com.keshe.contacts_keshe.util.SharedPreference;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

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

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile = et_mobile.getText().toString();
                String password = et_password.getText().toString();

                Toast.makeText(getBaseContext(), mobile + " " + password, Toast.LENGTH_LONG).show();
                if (!mobile.equals("")  && password.equals("")){
                    RestApi.login(mobile, password, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String reponse = responseBody.toString();

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }
            }
        });

    }
}
