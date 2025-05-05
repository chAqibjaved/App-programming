package com.aqib.mymedreminder.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aqib.mymedreminder.R;
import com.aqib.mymedreminder.data.entities.User;
import com.aqib.mymedreminder.repository.LocalLoginRepository;
import com.aqib.mymedreminder.repository.UserRepositoryInterface;
import com.aqib.mymedreminder.utils.DialogUtils;

public class SignupActivity extends Activity {

  Toolbar toolbar;

  EditText signup_edt_name,signup_edt_dob,signup_edt_passportid,signup_edt_disease;
  EditText signup_edt_password,signup_edt_conform_password;

  TextView signup_txt_sign_up_user;

   private UserRepositoryInterface userRepositoryInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        toolbar=findViewById(R.id.toolbar);
        Toolbar toolbar=findViewById(R.id.toolbar);
        // Set the toolbar as the ActionBar
        setActionBar(toolbar);
//        // Optionally, set a title for the toolbar
        getActionBar().setTitle("Sign Up");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        signup_edt_name=findViewById(R.id.signup_edt_name);
        signup_edt_dob=findViewById(R.id.signup_edt_dob);
        signup_edt_passportid=findViewById(R.id.signup_edt_passportid);
        signup_edt_disease=findViewById(R.id.signup_edt_disease);
        signup_edt_password=findViewById(R.id.signup_edt_password);
        signup_edt_conform_password=findViewById(R.id.signup_edt_conform_password);
        signup_txt_sign_up_user=findViewById(R.id.signup_txt_sign_up_user);
        userRepositoryInterface=new LocalLoginRepository(SignupActivity.this);
        signup_txt_sign_up_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        signup_edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showDatePickerDialog(SignupActivity.this, new DialogUtils.DatePickerCallback() {
                    @Override
                    public void onDateSelected(int year, int month, int day) {
                        String selectedDate = day + "/" + month + "/" + year;
                        signup_edt_dob.setText(selectedDate);
                    }
                });
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void register() {
        String name = signup_edt_name.getText().toString().trim();
        String dob = signup_edt_dob.getText().toString();
        String passportid = signup_edt_passportid.getText().toString();
        String disease = signup_edt_disease.getText().toString().trim();
        String password = signup_edt_password.getText().toString().trim();
        String confirmPassword = signup_edt_conform_password.getText().toString();

        if (validateForm()) {
            userRepositoryInterface.signUp(new User(name, dob, passportid, disease, password), new UserRepositoryInterface.SaveCallBack() {
                @Override
                public void onSuccess() {
                    Toast.makeText(SignupActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish(); // Close registration activity
                }

                @Override
                public void onError(String errorMessage) {
                    DialogUtils.showOkDialog(SignupActivity.this, "Sign Up Error", errorMessage);

                }
            });
        }

    }

    private boolean validateForm() {
        boolean isValid = true;

        // Retrieve data from form fields
        String name = signup_edt_name.getText().toString().trim();
        String age = signup_edt_dob.getText().toString();
        String passportId = signup_edt_passportid.toString(); // Assuming you are using MaskedEditText
        String disease = signup_edt_disease.getText().toString().trim();
        String password = signup_edt_password.getText().toString();
        String confirmPassword = signup_edt_conform_password.getText().toString();

        // Reset any previous errors
        signup_edt_name.setError(null);
        signup_edt_dob.setError(null);
        signup_edt_passportid.setError(null);
        signup_edt_disease.setError(null);
        signup_edt_password.setError(null);
        signup_edt_conform_password.setError(null);

        // Check if Name field is empty
        if (TextUtils.isEmpty(name)) {
            signup_edt_name.setError("Name is required");
            isValid = false;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            signup_edt_name.setError("alphabetic characters only");
            isValid = false;

        }


        // Check if DOB field is empty
        if (TextUtils.isEmpty(age)) {
            signup_edt_dob.setError("DOB is required");
            isValid = false;
        }

        // Check if CNIC field is empty
        if (TextUtils.isEmpty(passportId)) {
            signup_edt_passportid.setError("PassportId is required");
            isValid = false;
        }


        // Check if Disease field is empty
        if (TextUtils.isEmpty(disease)) {
           signup_edt_disease.setError("Disease is required");
            isValid = false;
        }

        if (!disease .matches("[a-zA-Z ]+")) {
            signup_edt_disease.setError("alphabetic characters only");
            isValid = false;
        }

        // Check if Password field is empty
        if (TextUtils.isEmpty(password)) {
            signup_edt_password.setError("Password is required");
            isValid = false;
        }

        if (password.length() < 4) {
            signup_edt_password.setError("Password is very short");
            isValid = false;
        }

        // Check if Confirm Password field is empty
        if (TextUtils.isEmpty(confirmPassword)) {
            signup_edt_conform_password.setError("Confirm Password is required");
            isValid = false;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            signup_edt_conform_password.setError("Passwords do not match");
            isValid = false;
        }

        return isValid;
    }
}