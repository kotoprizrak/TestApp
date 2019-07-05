package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button button;
    Intent intent;
    Product[] products = new Product[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.main_screen));

        button = findViewById(R.id.button);

        products[0] = new Product("Booken Saga", 1, "text");
        products[1] = new Product("Dexp L1 Moon", 2, "text2");
        ExportToJson();
    }

    public void onClickButton(View view)
    {
        intent = new Intent(this,AboutProductActivity.class);
        startActivity(intent);
    }

    public void ExportToJson()
    {
        Gson gson = new Gson();
        String gsonString = gson.toJson(products); //??
        Log.d("123",gsonString);
    }

//    private void InitProducts(Product[] products) //
//    {
//        for (int i = 0; i < products.length; i++)
//        {
//            InputStream inputStream = getApplicationContext().getAssets()
//                    .open(products[i].getName() + ".jpg");
//
//        }
//
//    }

}
