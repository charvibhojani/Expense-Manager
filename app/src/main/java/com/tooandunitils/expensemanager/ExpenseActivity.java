package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ExpenseActivity extends Activity implements AdapterView.OnItemSelectedListener {

    EditText et_price;
    ImageView add_expense, imgback;
    String price;
    private DatabaseHelper databaseHelper;
    String type, spinner;
    Spinner sp;

    ArrayList<CategoryItem> categoryItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        String current_time = get_current_time();

        databaseHelper = new DatabaseHelper(this);

        type = "expense";

        et_price = findViewById(R.id.et_price);
        add_expense = findViewById(R.id.add_expense);
        sp = findViewById(R.id.sp);
        imgback = findViewById(R.id.img_back);

        categoryItems = getCategoryItem();

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryItems);

        if (sp != null) {
            sp.setAdapter(categoryAdapter);
            sp.setOnItemSelectedListener(this);
        }

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        add_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                price = et_price.getText().toString();

                if (spinner.equals("Select Category")) {

                    Toast.makeText(ExpenseActivity.this, "Please select category", Toast.LENGTH_SHORT).show();

                } else if (price.equals("")) {

                    et_price.setError("Please write price");

                } else {

                    long count = databaseHelper.insertdata_exp(spinner, price);

                    long transaction = databaseHelper.recent_trans(spinner, price, current_time, type);

                    if (count != -1) {
                        Toast.makeText(getApplicationContext(), "Data added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ExpenseActivity.this, ShowExpenseActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public String get_current_time() {
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }

    private ArrayList<CategoryItem> getCategoryItem() {

        categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem("Select Category"));
        categoryItems.add(new CategoryItem("Business"));
        categoryItems.add(new CategoryItem("Travel"));
        categoryItems.add(new CategoryItem("Personal"));
        categoryItems.add(new CategoryItem("Music"));
        categoryItems.add(new CategoryItem("Food"));
        categoryItems.add(new CategoryItem("Groceries"));
        categoryItems.add(new CategoryItem("Electronic"));
        categoryItems.add(new CategoryItem("Study"));
        categoryItems.add(new CategoryItem("Health care"));
        categoryItems.add(new CategoryItem("Sports"));
        categoryItems.add(new CategoryItem("Bill"));
        categoryItems.add(new CategoryItem("Rent"));
        categoryItems.add(new CategoryItem("Salary"));
        categoryItems.add(new CategoryItem("Shopping"));
        categoryItems.add(new CategoryItem("Subscription"));
        return categoryItems;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        CategoryItem item = (CategoryItem) parent.getSelectedItem();
        spinner = item.getCategory();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
