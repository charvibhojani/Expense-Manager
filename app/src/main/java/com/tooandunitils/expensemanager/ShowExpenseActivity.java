package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowExpenseActivity extends Activity {

    RecyclerView lv;

    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_data_expense);

        type = "expense";

        lv = findViewById(R.id.lv);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);

        dataload();
    }

    public void dataload() {

        MyConst.show_Items.clear();

        MyConst.Total_Expense = 0;

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME_2 + " ", null);

        if (cursor != null) {

            while (cursor.moveToNext()) {

                int id = cursor.getInt(0);
                String category = cursor.getString(1);
                String price = cursor.getString(2);

                MyConst.show_Items.add(new showItems(category, price, id));

            }

            ShowAdapter showAdapter = new ShowAdapter(MyConst.show_Items, this, type);
            lv.setAdapter(showAdapter);

            try {
                SharedPreferences sharedPreferences = getSharedPreferences("Expense", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                SharedPreferences sharedPreferences_exp = getSharedPreferences("Total_exp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_exp = sharedPreferences_exp.edit();

                for (showItems item : MyConst.show_Items) {
                    int price = Integer.parseInt(item.getPrice());
                    MyConst.Total_Expense += price;
                }

                editor.putInt("total_expense", MyConst.Total_Expense);
                editor.apply();

                editor_exp.putInt("total_data_expense", MyConst.Total_Expense);
                editor_exp.apply();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
