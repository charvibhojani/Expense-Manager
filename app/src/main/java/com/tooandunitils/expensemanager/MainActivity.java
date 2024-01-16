package com.tooandunitils.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener{

    ImageView income, expense ;
    Toolbar imgmenu;
    DrawerLayout drawerLayout;
    TextView totalincome, totalexpense, texttotal, show_data_income, show_data_expense;
    SharedPreferences sharedPreferences;
    RecyclerView rvTrans;
    RelativeLayout incomeRv, expenseRv;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    TransactionAdapter transactionAdapter;

    ArrayList<TransactionItem> transactionItems = new ArrayList<>();
    ArrayList<CategoryItem> categoryItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        db = dbHelper.getReadableDatabase();

        income = findViewById(R.id.income);
        expense = findViewById(R.id.expense);
        show_data_income = findViewById(R.id.show_data_income);
        show_data_expense = findViewById(R.id.show_data_expense);
        totalincome = findViewById(R.id.total_income);
        totalexpense = findViewById(R.id.total_expense);
        texttotal = findViewById(R.id.txt_finalamount);
        rvTrans = findViewById(R.id.rv_trans);
        imgmenu = findViewById(R.id.img_menu);
        drawerLayout = findViewById(R.id.drawer_layout);
        incomeRv = findViewById(R.id.income_rv);
        expenseRv = findViewById(R.id.expense_rv);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout,imgmenu, R.string.nav_open, R.string.nav_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.closeDrawer(GravityCompat.START);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);


        SharedPreferences preferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) {
            // Clear SharedPreferences data
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Update the flag
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
        }

//        categoryItems = dbHelper.getcustomlist();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTrans.setLayoutManager(linearLayoutManager);

        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IncomeActivity.class);
                startActivity(intent);
            }
        });

        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivity(intent);
            }
        });

        incomeRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowIncomeActivity.class);
                startActivity(intent);
            }
        });

        expenseRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowExpenseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        int totalIncome = sharedPreferences.getInt("total_income", 0);
        totalincome.setText("" + totalIncome);

        sharedPreferences = getSharedPreferences("Expense", Context.MODE_PRIVATE);

        int totalExpense = sharedPreferences.getInt("total_expense", 0);
        totalexpense.setText("" + totalExpense);

        sharedPreferences = getSharedPreferences("Total_exp", Context.MODE_PRIVATE);

        int totalExpense_data = sharedPreferences.getInt("total_data_expense", 0);
        totalexpense.setText("" + totalExpense_data);

        sharedPreferences = getSharedPreferences("Total_inc", Context.MODE_PRIVATE);

        int totalIncome_data = sharedPreferences.getInt("total_data_income", 0);
        totalincome.setText("" + totalIncome_data);

        int final_amount = totalIncome_data - totalExpense_data;
        texttotal.setText("" + final_amount);

        if ((totalIncome_data - totalExpense_data) > 0) {
            texttotal.setTextColor((getResources().getColor(android.R.color.holo_green_dark)));

        } else if ((totalIncome_data - totalExpense_data) < 0) {
            texttotal.setTextColor((getResources().getColor(android.R.color.holo_red_dark)));

        } else {
            texttotal.setTextColor((getResources().getColor(android.R.color.white)));
        }

        MyConst.transactionItems.clear();

        Cursor cursor = db.rawQuery("select * from " + dbHelper.TABLE_NAME_3 + " ", null);

        if (cursor != null) {

            while (cursor.moveToNext()) {

                String category = cursor.getString(1);
                String price = cursor.getString(2);
                String time = cursor.getString(3);
                String type = cursor.getString(4);

                MyConst.transactionItems.add(new TransactionItem(category, price, time, type));

            }

            Collections.reverse(MyConst.transactionItems);
            transactionAdapter = new TransactionAdapter(MyConst.transactionItems,this);
            rvTrans.setAdapter(transactionAdapter);
        }

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home1:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.addincome:
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, IncomeActivity.class));
                break;
            case R.id.addexpense:
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
                break;
            case R.id.alltransaction:
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, AllTransaction.class));
                break;
            case R.id.aboutus:
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(MainActivity.this, AboutUs.class));
                break;
            case R.id.addcategory:
                drawerLayout.closeDrawer(GravityCompat.START);

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this);
                View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.category_dialog, null);
                EditText transactionCategory = dialogView.findViewById(R.id.txt_addcategory);
                Button btnAdd = dialogView.findViewById(R.id.btn_addcategory);
                builder.setView(dialogView);
                final androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String category = transactionCategory.getText().toString();
                        if (category.isEmpty()) {
                            transactionCategory.setError("category cannot be empty");
                            return;
                        }
                        dbHelper.adddata_trans(category);
                        categoryItems.add(new CategoryItem(category));
                        transactionAdapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                break;
        }
        return false;
    }
}