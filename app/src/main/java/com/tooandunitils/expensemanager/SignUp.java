package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

public class SignUp extends Activity {

    EditText etName, etEmail;
    ImageView signUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etname);
        etEmail = findViewById(R.id.etemail);
        signUp = findViewById(R.id.sign_up);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkvalidation = ischeckvalidation();
                String username = etName.getText().toString();
                String email = etEmail.getText().toString();

                if (checkvalidation) {
                    Intent i = new Intent(SignUp.this, PinActivity.class);
                    i.putExtra("pinType", "create");
                    i.putExtra("username", username);
                    i.putExtra("email", email);
                    startActivity(i);
                    finish();
                }

            }
        });

    }

    private Boolean ischeckvalidation() {
        if (etName.length() == 0) {
            etName.setError("Please Enter Username");
            return false;
        }
        if (etEmail.length() == 0) {
            etEmail.setError("Please Enter Email-id");
            return false;
        }

        String email = etEmail.getText().toString().trim();
        if (!isValidEmail(email)) {
            etEmail.setError("Please Enter a Valid Email");
            return false;
        }
        return true;
    }

    private boolean isValidEmail(CharSequence email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.toString().matches(emailPattern);
    }

}
