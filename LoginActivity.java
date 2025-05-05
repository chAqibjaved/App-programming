package com.aqib.mymedreminder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aqib.mymedreminder.MainActivity;
import com.aqib.mymedreminder.R;
import com.aqib.mymedreminder.data.entities.User;
import com.aqib.mymedreminder.repository.LocalLoginRepository;
import com.aqib.mymedreminder.repository.UserRepositoryInterface;
import com.aqib.mymedreminder.utils.DialogUtils;
import com.aqib.mymedreminder.utils.Session;

public class LoginActivity extends Activity {
    TextView login_btn_sign_up, login_btn_sign_in, login_txt_forgot_password;

    EditText login_edt_sign_in_cnic,login_edt_sign_in_password;

    private UserRepositoryInterface loginRepositoryInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_btn_sign_up=findViewById(R.id.login_btn_sign_up);
        login_btn_sign_in=findViewById(R.id.login_btn_sign_in);
        login_edt_sign_in_cnic=findViewById(R.id.login_edt_sign_in_cnic);
        login_edt_sign_in_password=findViewById(R.id.login_edt_sign_in_password);
        login_txt_forgot_password=findViewById(R.id.login_txt_forgot_password);
        loginRepositoryInterface=new LocalLoginRepository(LoginActivity.this);

        login_btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        login_btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        login_txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
            }
        });

    }

    private void login() {
        String passportId = login_edt_sign_in_cnic.getText().toString();
        String password = login_edt_sign_in_password.getText().toString();
        loginRepositoryInterface.login(passportId, password, new UserRepositoryInterface.ValidateCallBack() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                Session.saveUserDetail(getApplicationContext(),user);
                startHomepage();
            }

            @Override
            public void onError(String errormessage) {
                DialogUtils.showOkDialog(LoginActivity.this,"Error",errormessage);
            }
        } );

    }

    private void startHomepage() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close login activity
    }
}