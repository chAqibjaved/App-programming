package com.aqib.mymedreminder.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aqib.mymedreminder.R;
import com.aqib.mymedreminder.data.entities.User;
import com.aqib.mymedreminder.repository.LocalLoginRepository;
import com.aqib.mymedreminder.repository.UserRepositoryInterface;
import com.aqib.mymedreminder.utils.DialogUtils;

public class ForgotPasswordActivity extends AppCompatActivity {

    private UserRepositoryInterface userRepositoryInterface;

    Toolbar toolbar;

    EditText forgot_edt_dob,forgot_edt_passportId,forgot_edt_new_password,forgot_edt_conform_password;

    TextView forgot_txt_next_or_change_password;

    LinearLayout forgot_layout_validate_user,forgot_layout_new_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        toolbar=findViewById(R.id.toolbar);
        Toolbar toolbar=findViewById(R.id.toolbar);
        // Set the toolbar as the ActionBar
        setActionBar(toolbar);
//        // Optionally, set a title for the toolbar
        getActionBar().setTitle("Forgot Password");
        forgot_edt_dob=findViewById(R.id.forgot_edt_dob);
        forgot_edt_passportId=findViewById(R.id.forgot_edt_passportId);
        forgot_txt_next_or_change_password=findViewById(R.id.forgot_txt_next_or_change_password);
        forgot_layout_validate_user=findViewById(R.id.forgot_layout_validate_user);
        forgot_layout_new_password=findViewById(R.id.forgot_layout_new_password);
        forgot_edt_new_password=findViewById(R.id.forgot_edt_new_password);
        forgot_edt_conform_password=findViewById(R.id.forgot_edt_conform_password);

        userRepositoryInterface=new LocalLoginRepository(ForgotPasswordActivity.this);

        forgot_edt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showDatePickerDialog(ForgotPasswordActivity.this, new DialogUtils.DatePickerCallback() {
                    @Override
                    public void onDateSelected(int year, int month, int day) {
                        String selectedDate = day + "/" + month + "/" + year;
                        forgot_edt_dob.setText(selectedDate);
                    }
                });
            }
        });
        forgot_txt_next_or_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(forgot_txt_next_or_change_password.getText().toString().equals("Next")){
                    validateUserdetails();
                }
                else if(forgot_txt_next_or_change_password.getText().toString().equals("Change Password")){
                    changepassword();
                }
            }
        });


    }

    private void validateUserdetails() {
        String passportId = forgot_edt_passportId.getText().toString().toString().trim();
        String dob = forgot_edt_dob.getText().toString().trim();
        // Validation for empty fields
        if (dob.isEmpty()) {
            forgot_edt_dob.setError("Date of Birth cannot be empty");
            return;
        }
        if (passportId.isEmpty()) {
            forgot_edt_passportId.setError("Required");
            return;
        }

        userRepositoryInterface.validateUser(passportId, dob, new UserRepositoryInterface.ValidateCallBack() {
            @Override
            public void onSuccess(User user) {
                forgot_txt_next_or_change_password.setText("Change Password");
                forgot_layout_validate_user.setVisibility(View.GONE);
                forgot_layout_new_password.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String errormessage) {
                DialogUtils.showOkDialog(ForgotPasswordActivity.this,"Error",errormessage);
            }

        });
    }

    private void changepassword() {
        String passportId = forgot_edt_passportId.getText().toString().trim();
        String newPassword = forgot_edt_new_password.getText().toString().trim();
        String confirmPassword = forgot_edt_conform_password.getText().toString().trim();

        // Validation for empty fields
        if (newPassword.isEmpty()) {
            forgot_edt_new_password.setError("Password cannot be empty");
            return;
        }

        if (confirmPassword.isEmpty()) {
            forgot_edt_conform_password.setError("Confirm Password cannot be empty");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            forgot_edt_conform_password.setError("Passwords do not match");
            return;
        }


        userRepositoryInterface.changePassword(passportId, newPassword, new UserRepositoryInterface.SaveCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(ForgotPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity
            }

            @Override
            public void onError(String errorMessage) {
                DialogUtils.showOkDialog(ForgotPasswordActivity.this,"Error",errorMessage);
            }
        });
    }
}