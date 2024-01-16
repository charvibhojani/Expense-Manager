package com.tooandunitils.expensemanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.Myh> {

    ArrayList<TransactionItem> transactionItems;
    Context context;
    DatabaseHelper db;

    public TransactionAdapter(ArrayList<TransactionItem> transactionItems, Context context) {

        this.transactionItems = transactionItems;
        this.context = context;
        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public TransactionAdapter.Myh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recent_transaction, parent, false);

        return new Myh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.Myh holder, int position) {

        String data = MyConst.transactionItems.get(position).getType();

        if (data.equals("income")) {
            holder.transPrice.setTextColor(Color.GREEN);

        } else if (data.equals("expense")) {
            holder.transPrice.setTextColor(Color.RED);

        }

        holder.transCategory.setText(MyConst.transactionItems.get(position).getCategory());

        holder.transPrice.setText(MyConst.transactionItems.get(position).getPrice());

        holder.transTime.setText(MyConst.transactionItems.get(position).getTime());

        String category = MyConst.transactionItems.get(position).getCategory();

        int iconResId = R.drawable.black_bg;

        if (category.equals("Music")) {
            iconResId = R.drawable.ic_category_music;
        } else if (category.equals("Bussiness")) {
            iconResId = R.drawable.ic_category_business;
        } else if (category.equals("Travel")) {
            iconResId = R.drawable.ic_category_travel;
        } else if (category.equals("Food")) {
            iconResId = R.drawable.ic_category_food;
        } else if (category.equals("Electronic")) {
            iconResId = R.drawable.ic_category_electronics;
        } else if (category.equals("Sports")) {
            iconResId = R.drawable.ic_category_sports;
        } else if (category.equals("Health care")) {
            iconResId = R.drawable.ic_category_health;
        } else if (category.equals("Groceries")) {
            iconResId = R.drawable.ic_category_grocery;
        } else if (category.equals("Personal")) {
            iconResId = R.drawable.ic_category_personal;
        } else if (category.equals("Study")) {
            iconResId = R.drawable.ic_category_study;
        } else if (category.equals("Bill")) {
            iconResId = R.drawable.ic_category_bill;
        } else if (category.equals("Subscription")) {
            iconResId = R.drawable.ic_category_subscription;
        } else if (category.equals("Shopping")) {
            iconResId = R.drawable.ic_category_shopping;
        } else if (category.equals("Rent")) {
            iconResId = R.drawable.ic_category_rent;
        } else if (category.equals("Salary")) {
            iconResId = R.drawable.ic_category_salary;
        }
        holder.iconImg.setImageResource(iconResId);

    }

    @Override
    public int getItemCount() {
        return transactionItems.size();
    }

    public class Myh extends RecyclerView.ViewHolder {

        TextView transCategory, transPrice, transTime;
        ImageView iconImg;

        public Myh(@NonNull View itemView) {
            super(itemView);

            transCategory = itemView.findViewById(R.id.trans_category);
            transPrice = itemView.findViewById(R.id.trans_price);
            transTime = itemView.findViewById(R.id.trans_time);
            iconImg = itemView.findViewById(R.id.icon_img);
        }
    }

    public interface Myclick {

        public void getmypos(int pos);

    }
}
