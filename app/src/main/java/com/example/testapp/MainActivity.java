package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    //Product[] products = new Product[2];
    private List<Product> products;

    //PREFERENCES:
    private SharedPreferences mySettings;
    private static final String APP_SETINGS = "my_settings";
    private static final String JSON_EXISTS = "json_exists";

    private static final String FILE_NAME = "myProducts.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.main_screen));

        recyclerView = findViewById(R.id.myRecyclerView);
        products = new ArrayList<>();

        mySettings = getSharedPreferences(APP_SETINGS, Context.MODE_PRIVATE);
        if (mySettings.getBoolean(JSON_EXISTS, false))
            ReadJSON();//Есть настройки - в отдельном потоке выгружаем данные
        else
            CreateJSON();//Нет настроек - добавляем данные в отдельном потоке
        InitializeImages();

        MyAdapterForRecyclerView adapter = new MyAdapterForRecyclerView(this, products);
        recyclerView.setAdapter(adapter);
    }

    //Запись данных в json-файл
    private void CreateJSON()
    {
        AddProducts();
        Gson gson = new Gson();
        String jsonString = gson.toJson(products);
        Log.d("Содержимой файла json: ", jsonString);

        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Файл JSON не был создан", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        finally
        {
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    //чтение данных из json-файла
    private void ReadJSON()
    {
        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;

        try
        {
            fileInputStream = getApplicationContext().openFileInput(FILE_NAME);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            //DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            products = gson.fromJson(streamReader, List.class);
            //Product[] products2 = gson.fromJson(streamReader, Product[].class);
            Log.d("123", products.get(0).getName() + " " + products.get(0).getId());
        }
        catch (IOException ex)
        {
            SharedPreferences.Editor editor = mySettings.edit();
            editor.putBoolean(JSON_EXISTS, false);
            editor.apply();
            ex.printStackTrace();
        }
        finally
        {
            if (streamReader != null)
            {
                try
                {
                    streamReader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null)
            {
                try
                {
                    fileInputStream.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    private void AddProducts()
    {
        products.add(new Product("Bookeen Saga", 1, "Описание 1"));
        products.add(new Product("Dexp FL2 Jupiter", 2, "Описание 2"));
        products.add(new Product("Dexp L1 Moon", 3, "Описание 3"));
        products.add(new Product("Dexp L2 Moon", 4, "Описание 4"));
        products.add(new Product("Dexp S1 Symbol", 5, "Описание 5"));
        products.add(new Product("Dexp T2 Composition", 6, "Описание 6"));
        products.add(new Product("PocketBook 614 Plus", 7, "Описание 7"));
        products.add(new Product("PocketBook 625 LE", 8, "Описание 8"));
        products.add(new Product("PocketBook 740", 9, "Описание 9"));
    }

    private void InitializeImages()
    {
        for (int i = 0; i < products.size(); i++)
        {
            InputStream inputStream = null;
            Drawable drawable;
            try
            {
                inputStream = getApplicationContext().getAssets().open(products.get(i).getName() + ".jpg");
                drawable = Drawable.createFromStream(inputStream,null);
                products.get(i).setDrawable(drawable);
            }
            catch (IOException exp)
            {
                exp.printStackTrace();
                Log.d("123",exp + "");
            }
        }
    }

}
