package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.zip.Inflater;

//Пользовательский адаптер для работы со списком главного активити
public class MyAdapterForRecyclerView extends RecyclerView.Adapter<MyAdapterForRecyclerView.MyViewHolder> {

    private LayoutInflater inflater;
    private Product[] products;
    private Context context;

    public MyAdapterForRecyclerView(Context context, Product[] products) {
        this.products = products;
        this.context = context;
        this.inflater = LayoutInflater.from(context.getApplicationContext());
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycler_view_part, parent,false);
        return new MyAdapterForRecyclerView.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String id = " " + products[position].getId();
        String name = " " + products[position].getName();

        holder.tvId.setText(id);
        holder.tvName.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), AboutCompanyActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvId;
        TextView tvName;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvId = itemView.findViewById(R.id.textViewRecyclerViewId);
            tvName = itemView.findViewById(R.id.textViewRecyclerViewName);
        }
    }
}

