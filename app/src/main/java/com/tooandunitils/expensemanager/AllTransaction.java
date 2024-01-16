package com.tooandunitils.expensemanager;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllTransaction extends Activity {

    RecyclerView recyclerView;
    TextView txtempty;
    ArrayList<TransactionItem> transactionItems;
    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);

        recyclerView = findViewById(R.id.transactionrecyclerview);
        txtempty = findViewById(R.id.txtempty);
        db = new DatabaseHelper(this);

        try {
            Cursor cursor = new DatabaseHelper(this).readdata();

            transactionItems = new ArrayList<>();

            while (cursor.moveToNext()) {

                String category = cursor.getString(1);
                String price = cursor.getString(2);
                String time = cursor.getString(3);
                String type = cursor.getString(4);

                TransactionItem obj = new TransactionItem(category, price, time, type);
                transactionItems.add(obj);
            }

        } catch (Exception e) {
            Log.d("database error", e.getMessage());
        }

        if (transactionItems.isEmpty()) {
            txtempty.setVisibility(View.VISIBLE);  // Show the emptyTextView
        } else {
            txtempty.setVisibility(View.GONE);     // Hide the emptyTextView
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TransactionAdapter adapter = new TransactionAdapter(transactionItems, this);

        recyclerView.setAdapter(adapter);

    }
}
