package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutProductActivity extends AppCompatActivity {

    TextView tvId;
    TextView tvDesc;
    TextView tvName;
    ImageView picture;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_product);
        setTitle(getString(R.string.about_product));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvId = findViewById(R.id.textViewId);
        tvDesc = findViewById(R.id.textViewDesc);
        tvName = findViewById(R.id.textViewName);
        picture = findViewById(R.id.imageViewAboutProduct);

        intent = getIntent();
        tvId.setText(" " + intent.getIntExtra(MyAdapterForRecyclerView.PRODUCT_ID, 0));
        tvDesc.setText("" + intent.getStringExtra(MyAdapterForRecyclerView.PRODUCT_DESCRIPTION));
        tvName.setText("" + intent.getStringExtra(MyAdapterForRecyclerView.PRODUCT_NAME));
        picture.setImageBitmap((Bitmap) intent.getParcelableExtra(MyAdapterForRecyclerView.PRODUCT_IMAGE));
    }

    //Обеспечивает возврат в предыдущую активность
    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}
