package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PinActivity extends Activity implements View.OnClickListener {

    ImageView button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonC, button0, buttonOk;
    View view1, view2, view3, view4;
    ArrayList<String> number_list = new ArrayList<>();
    String passcode = "";
    String username, email;
    TextView txt_create, txtReset;
    String num_1, num_2, num_3, num_4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        initializecomponents();
        Intent i = getIntent();

        String pinType = i.getStringExtra("pinType");
        username = i.getStringExtra("username");
        email = i.getStringExtra("email");

        if (pinType != null && pinType.equals("login")) {
            txt_create.setText("Enter a 4-digit PIN");
            txtReset.setVisibility(View.VISIBLE);

            txtReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(PinActivity.this, ResetPassword.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                    finish();
                }
            });

            buttonOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (number_list.size() < 4) {
                        number_list.clear();
                        Toast.makeText(PinActivity.this, "PIN is incomplete!", Toast.LENGTH_SHORT).show();
                    } else {

                        String email = getIntent().getStringExtra("email");
                        DatabaseHelper db = new DatabaseHelper(PinActivity.this);
                        String password = db.getPasswordandemail(email);

                        if (passcode.equals(password)) {
                            Intent intent = new Intent(PinActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            number_list.clear();
                            Toast.makeText(PinActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else if (pinType != null && pinType.equals("create")) {
            txt_create.setText("Create a 4-digit PIN");
            txtReset.setVisibility(View.GONE);
        }
    }

    private void initializecomponents() {
        buttonOk = findViewById(R.id.button_ok);
        buttonC = findViewById(R.id.button_c);
        view1 = findViewById(R.id.view_1);
        view2 = findViewById(R.id.view_2);
        view3 = findViewById(R.id.view_3);
        view4 = findViewById(R.id.view_4);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        button0 = findViewById(R.id.button_0);
        txt_create = findViewById(R.id.txt_create);
        txtReset = findViewById(R.id.txt_reset);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonOk.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_1:
                number_list.add("1");
                passnumber(number_list);
                break;
            case R.id.button_2:
                number_list.add("2");
                passnumber(number_list);
                break;
            case R.id.button_3:
                number_list.add("3");
                passnumber(number_list);
                break;
            case R.id.button_4:
                number_list.add("4");
                passnumber(number_list);
                break;
            case R.id.button_5:
                number_list.add("5");
                passnumber(number_list);
                break;
            case R.id.button_6:
                number_list.add("6");
                passnumber(number_list);
                break;
            case R.id.button_7:
                number_list.add("7");
                passnumber(number_list);
                break;
            case R.id.button_8:
                number_list.add("8");
                passnumber(number_list);
                break;
            case R.id.button_9:
                number_list.add("9");
                passnumber(number_list);
                break;
            case R.id.button_0:
                number_list.add("0");
                passnumber(number_list);
                break;
            case R.id.button_c:
                number_list.clear();
                passnumber(number_list);
                break;
            case R.id.button_ok:
                if (number_list.size() < 4) {
                    number_list.clear();
                    Toast.makeText(this, "PIN is incomplete!", Toast.LENGTH_SHORT).show();

                } else {

                    {
                        Intent i = new Intent(PinActivity.this, ConfirmPinActivity.class);
                        i.putExtra("passcode", passcode);
                        i.putExtra("username", username);
                        i.putExtra("email", email);
                        startActivity(i);
                        finish();
                    }
                }
                break;
        }
    }

    private void passnumber(ArrayList<String> number_list) {
        if (number_list.size() == 0) {
            view1.setBackgroundResource(R.drawable.bg_view_gray);
            view2.setBackgroundResource(R.drawable.bg_view_gray);
            view3.setBackgroundResource(R.drawable.bg_view_gray);
            view4.setBackgroundResource(R.drawable.bg_view_gray);
        } else {
            switch (number_list.size()) {
                case 1:
                    num_1 = number_list.get(0);
                    view1.setBackgroundResource(R.drawable.bg_view_green);
                    break;
                case 2:
                    num_2 = number_list.get(1);
                    view2.setBackgroundResource(R.drawable.bg_view_green);
                    break;
                case 3:
                    num_3 = number_list.get(2);
                    view3.setBackgroundResource(R.drawable.bg_view_green);
                    break;
                case 4:
                    num_4 = number_list.get(3);
                    view4.setBackgroundResource(R.drawable.bg_view_green);
                    passcode = num_1 + num_2 + num_3 + num_4;
            }
        }
    }
}