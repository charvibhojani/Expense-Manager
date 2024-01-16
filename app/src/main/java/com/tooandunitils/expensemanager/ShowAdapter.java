package com.tooandunitils.expensemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.Myh> implements AdapterView.OnItemSelectedListener {

    ArrayList<showItems> showItems;
    ArrayList<CategoryItem> categoryItems;
    Context context;
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String updatecategory;
    String type;

    String category;

    public ShowAdapter(ArrayList<showItems> showItems, Context context, String type) {

        this.showItems = showItems;

        this.context = context;

        this.type = type;

        dbHelper = new DatabaseHelper(context);

        db = dbHelper.getReadableDatabase();

    }

    @NonNull
    @Override
    public Myh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_show, parent, false);

        return new Myh(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Myh holder, int position) {

        holder.category.setText(showItems.get(position).getCategory());

        holder.price.setText(showItems.get(position).getPrice());

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                update(v, position);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {

                int id = showItems.get(position).getId();

                dbHelper.DeleteCategory_expense(context, id);

                dbHelper.DeleteCategory_income(context, id);

                if (type.equals("income")) {

//                    notifyDataSetChanged();

                    Intent i = new Intent(context, ShowIncomeActivity.class);
                    context.startActivity(i);
                    ((ShowIncomeActivity) context).finish();

                } else if (type.equals("expense")) {

//                    notifyDataSetChanged();

                    Intent i = new Intent(context, ShowExpenseActivity.class);
                    context.startActivity(i);
                    ((ShowExpenseActivity) context).finish();
                }
            }
        });
    }

    public void update(View v, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
        View dialogview = LayoutInflater.from(v.getRootView().getContext()).inflate(R.layout.layout_dialog, null);
        EditText updateprice;
        Spinner updatecategory;
        Button btnupdate;

        updatecategory = dialogview.findViewById(R.id.updatecategory);

        updateprice = dialogview.findViewById(R.id.updateprice);
        btnupdate = dialogview.findViewById(R.id.btn_dialogupdate);

        builder.setView(dialogview);

        categoryItems = getcustomlist();

        CategoryAdapter adapter = new CategoryAdapter(context, categoryItems);

        if (updatecategory != null) {
            updatecategory.setAdapter(adapter);
            updatecategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CategoryItem item = (CategoryItem) parent.getSelectedItem();
                    category = item.getCategory();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        final AlertDialog dialog = builder.create();
        dialog.show();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = showItems.get(position).getId();

                int newpos = position + 1;

                String price = updateprice.getText().toString();

                if (category.isEmpty()) {
                    Toast.makeText(context, "category cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (price.isEmpty()) {
                    updateprice.setError("Price cannot be empty");
                    return;
                }

//                dbHelper.trans_data(category, price, id);

                dbHelper.UpdateCategory(category, price, type, newpos);
                dialog.dismiss();

                if (type.equals("income")) {

                    Intent i = new Intent(context, ShowIncomeActivity.class);
                    context.startActivity(i);
                    ((ShowIncomeActivity) context).finish();

                } else if (type.equals("expense")) {

                    Intent i = new Intent(context, ShowExpenseActivity.class);
                    context.startActivity(i);
                    ((ShowExpenseActivity) context).finish();

                }

            }
        });
    }

    private ArrayList<CategoryItem> getcustomlist() {
        categoryItems = new ArrayList<>();

        categoryItems.add(new CategoryItem(""));
        categoryItems.add(new CategoryItem("Bussiness"));
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
    public int getItemCount() {
        return showItems.size();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        CategoryItem item = (CategoryItem) adapterView.getSelectedItem();
        updatecategory = item.getCategory();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static class Myh extends RecyclerView.ViewHolder {
        TextView category, price;
        ImageView btnUpdate, btnDelete, btnExpenseUpdate;

        public Myh(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.category);
            price = itemView.findViewById(R.id.price);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}