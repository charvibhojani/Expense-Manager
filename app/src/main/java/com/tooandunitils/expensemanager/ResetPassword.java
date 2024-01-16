package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ResetPassword extends Activity {

    EditText OldPassword, NewPassword, ConfirmNewPassword;
    ImageView ResetPassword;
    String email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        OldPassword = findViewById(R.id.et_old_pass);
        NewPassword = findViewById(R.id.et_new_pass);
        ConfirmNewPassword = findViewById(R.id.et_confirm_new);
        ResetPassword = findViewById(R.id.reset_pass);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        DatabaseHelper db = new DatabaseHelper(ResetPassword.this);
        password = db.getPasswordandemail(email);

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPassword = OldPassword.getText().toString();
                String newPassword = NewPassword.getText().toString();
                String confirmPassword = ConfirmNewPassword.getText().toString();

                if (oldPassword.equals(password)) {
                    if (newPassword.equals(confirmPassword)) {

                        db.updatePassword(email, newPassword);
                        Toast.makeText(ResetPassword.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ResetPassword.this, SignIn.class);
                        startActivity(i);
                        finish();

                    } else {
                        ConfirmNewPassword.setError("New password and confirm password doesn't match");
                    }

                } else {
                    OldPassword.setError("password doesn't match");
                }
            }
        });

        NewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 4) {
                    String newText = s.toString().substring(0, 4);
                    NewPassword.setText(newText);
                    NewPassword.setSelection(newText.length());
                }

            }
        });

        OldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 4) {
                    String newText = s.toString().substring(0, 4);
                    OldPassword.setText(newText);
                    OldPassword.setSelection(newText.length());
                }

            }
        });

        ConfirmNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() > 4) {
                    String newText = s.toString().substring(0, 4);
                    ConfirmNewPassword.setText(newText);
                    ConfirmNewPassword.setSelection(newText.length());
                }
            }
        });
    }
}
