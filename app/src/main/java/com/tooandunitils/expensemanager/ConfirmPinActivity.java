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

public class ConfirmPinActivity extends Activity implements View.OnClickListener {

    ImageView button1, button2, button3, button4, button5, button6, button7, button8, button9, buttonC, button0, buttonOk;
    View view1, view2, view3, view4;
    ArrayList<String> number_list1 = new ArrayList<>();
    String passcode = "";
    String username, email;
    String num_1, num_2, num_3, num_4;
    String passcode1 = "";
    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);

        Intent intent = getIntent();
        passcode = intent.getStringExtra("passcode");
        username = intent.getStringExtra("username");
        email = intent.getStringExtra("email");

        db = new DatabaseHelper(this);

        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        buttonC = findViewById(R.id.button_c);
        button0 = findViewById(R.id.button_0);
        buttonOk = findViewById(R.id.button_ok);

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
                number_list1.add("1");
                passnumber(number_list1);
                break;
            case R.id.button_2:
                number_list1.add("2");
                passnumber(number_list1);
                break;
            case R.id.button_3:
                number_list1.add("3");
                passnumber(number_list1);
                break;
            case R.id.button_4:
                number_list1.add("4");
                passnumber(number_list1);
                break;
            case R.id.button_5:
                number_list1.add("5");
                passnumber(number_list1);
                break;
            case R.id.button_6:
                number_list1.add("6");
                passnumber(number_list1);
                break;
            case R.id.button_7:
                number_list1.add("7");
                passnumber(number_list1);
                break;
            case R.id.button_8:
                number_list1.add("8");
                passnumber(number_list1);
                break;
            case R.id.button_9:
                number_list1.add("9");
                passnumber(number_list1);
                break;
            case R.id.button_0:
                number_list1.add("0");
                passnumber(number_list1);
                break;
            case R.id.button_c:
                number_list1.clear();
                passnumber(number_list1);
                break;
            case R.id.button_ok:
                if (passcode.equals(passcode1)) {

                    Boolean checkadddata = db.adddata(username, email, passcode1);

                    if (checkadddata) {
                        Toast.makeText(ConfirmPinActivity.this, "Account created ...", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, SignIn.class);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(ConfirmPinActivity.this, "Account doesn't created Because Email-id Already Existing ...", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, SignUp.class);
                        startActivity(i);
                        finish();
                    }

                } else {
                    number_list1.clear();
                    Toast.makeText(this, "PIN does not match!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void passnumber(ArrayList<String> number_list1) {
        if (number_list1.size() == 0) {

            view1.setBackgroundResource(R.drawable.bg_view_gray);
            view2.setBackgroundResource(R.drawable.bg_view_gray);
            view3.setBackgroundResource(R.drawable.bg_view_gray);
            view4.setBackgroundResource(R.drawable.bg_view_gray);
        } else {
            switch (number_list1.size()) {
                case 1:
                    num_1 = number_list1.get(0);
//                    view1.setBackgroundResource(R.drawable.bg_view_green);
                    break;
                case 2:
                    num_2 = number_list1.get(1);
//                    view2.setBackgroundResource(R.drawable.bg_view_green);
                    break;
                case 3:
                    num_3 = number_list1.get(2);
//                    view3.setBackgroundResource(R.drawable.bg_view_green);
                    break;
                case 4:
                    num_4 = number_list1.get(3);
//                    view4.setBackgroundResource(R.drawable.bg_view_green);
                    passcode1 = num_1 + num_2 + num_3 + num_4;

            }
        }
    }
}
