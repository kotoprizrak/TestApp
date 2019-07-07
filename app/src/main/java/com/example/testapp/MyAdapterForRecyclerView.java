package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//Пользовательский адаптер для работы со списком главной активности
public class MyAdapterForRecyclerView extends RecyclerView.Adapter<MyAdapterForRecyclerView.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Product> products;
    private Context context;

    //ключи для работы с intent'ами
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_DESCRIPTION = "product_description";
    public static final String PRODUCT_IMAGE = "product_image";

    public MyAdapterForRecyclerView(Context context, List<Product> products) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        String id = " " + products.get(position).getId();
        String name = products.get(position).getName();

        holder.tvId.setText(id);
        holder.tvName.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), AboutProductActivity.class);

                intent.putExtra(PRODUCT_NAME, products.get(position).getName());
                intent.putExtra(PRODUCT_ID, products.get(position).getId());
                intent.putExtra(PRODUCT_DESCRIPTION, products.get(position).getDescription());
                intent.putExtra(PRODUCT_IMAGE, products.get(position).getPicture());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
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

