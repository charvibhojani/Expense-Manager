package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SignIn extends Activity {

    EditText etEmail;
    ImageView signIn;
    TextView Register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        etEmail = findViewById(R.id.etemail);
        signIn = findViewById(R.id.sign_in);
        Register = findViewById(R.id.register);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean checkvalidation = ischeckvalidation();
                if (checkvalidation) {

                    String Email = etEmail.getText().toString();
                    DatabaseHelper db = new DatabaseHelper(SignIn.this);
                    boolean isEmailMatched = db.fatchemail(Email);

                    if (isEmailMatched) {
                        Intent i = new Intent(SignIn.this, PinActivity.class);
                        i.putExtra("pinType", "login");
                        i.putExtra("email", Email);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(SignIn.this, " please enter valid email-id..", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private Boolean ischeckvalidation() {

        if (etEmail.length() == 0) {
            etEmail.setError("Please Enter Email-id");
            return false;
        }
        return true;
    }

    public void exitdialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);

        builder1.setMessage("Are you sure you want to exit ?");

        builder1.setCancelable(true);

        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();

                finish();
            }
        });

        builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        exitdialog();
    }
}
