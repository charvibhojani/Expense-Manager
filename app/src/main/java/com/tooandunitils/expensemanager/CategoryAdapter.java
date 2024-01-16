package com.tooandunitils.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter {

    public CategoryAdapter(@NonNull Context context, ArrayList<CategoryItem> categoryItems) {
        super(context, 0, categoryItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custome_spinner_layout,parent,false);
        }
        CategoryItem item = (CategoryItem) getItem(position);

        TextView tvspinner = convertView.findViewById(R.id.tv_spinnerlayout);
        if (item != null){
            tvspinner.setText(item.getCategory());
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_name,parent,false);
        }
        CategoryItem item = (CategoryItem) getItem(position);

        TextView tvdropdown = convertView.findViewById(R.id.category_tv);
        if (item != null){
            tvdropdown.setText(item.getCategory());
        }

        return convertView;
    }
}