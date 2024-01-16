package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class ShowIncomeActivity extends Activity {

    RecyclerView lv;

    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_data_show);

        type = "income";
        lv = findViewById(R.id.lv);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);

        dataload();

    }

    public void dataload() {

        MyConst.show_Items.clear();

        MyConst.Total_Income = 0;

//        Collections.reverseOrder();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME + " ", null);

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
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                SharedPreferences sharedPreferences_inc = getSharedPreferences("Total_inc", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_inc = sharedPreferences_inc.edit();

                for (showItems item : MyConst.show_Items) {
                    int price = Integer.parseInt(item.getPrice());
                    MyConst.Total_Income += price;
                }

                editor.putInt("total_income", MyConst.Total_Income);
                editor.apply();

                editor_inc.putInt("total_data_income", MyConst.Total_Income);
                editor_inc.apply();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}