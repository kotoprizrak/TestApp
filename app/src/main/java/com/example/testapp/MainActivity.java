package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private List<Product> products;

    //PREFERENCES:
    private SharedPreferences mySettings;
    private static final String APP_SETINGS = "my_settings";
    private static final String JSON_EXISTS = "json_exists";

    //Имя файла json
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
            ReadJSON();//Есть настройки - выгружаем данные из файла json
        else
            CreateJSON();//Нет настроек - создаём файл json
        InitializeImages();

        MyAdapterForRecyclerView adapter = new MyAdapterForRecyclerView(this, products);
        recyclerView.setAdapter(adapter);
    }


    //Переопределенный метод для вывода иконки "О компании"
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    //Переопределенный метод для обработки нажатия на иконку "О компании"
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.icon_about_company)
        {
            Intent intent = new Intent(this, AboutCompanyActivity.class);
            startActivity(intent);
        }
        return true;
    }

    //Запись данных в json-файл
    private void CreateJSON()
    {
        AddProducts();
        Gson gson = new Gson();
        String jsonString = gson.toJson(products);
        Log.d("Содержимое json: ", jsonString);

        FileOutputStream fileOutputStream = null;

        try
        {
            fileOutputStream = getApplicationContext().openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            SharedPreferences.Editor editor = mySettings.edit();
            editor.putBoolean(JSON_EXISTS, true);
            editor.apply();
        }
        catch (Exception e)
        {
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
            Type myListType = new TypeToken<ArrayList<Product>>(){}.getType();
            products = gson.fromJson(streamReader, myListType);
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

    //Добавление в список новых экземпляров класса Product
    private void AddProducts()
    {
        products.add(new Product("Электронная книга Bookeen Saga", 1,
                "Электронная книга, выполненная по технологии E-ink"));
        products.add(new Product("Электронная книга Dexp FL2 Jupiter", 2,
                "Dexp - Одна из самых доступных электронных книжек на рынке"));
        products.add(new Product("Электронная книга Dexp L1 Moon", 3,
                "Dexp - Одна из самых доступных электронных книжек на рынке"));
        products.add(new Product("Электронная книга Dexp L2 Moon", 4,
                "Dexp - Одна из самых доступных электронных книжек на рынке"));
        products.add(new Product("Электронная книга Dexp S1 Symbol", 5,
                "Dexp - Одна из самых доступных электронных книжек на рынке"));
        products.add(new Product("Электронная книга Dexp T2 Composition", 6,
                "Самый лучший представитель электронной книги Dexp"));
        products.add(new Product("Электронная книга PocketBook 614 Plus", 7,
                "PocketBook - Наипопулярнейший бренд среди электронных книжек"));
        products.add(new Product("Электронная книга PocketBook 625 LE", 8, "Описание"));
        products.add(new Product("Электронная книга PocketBook 740", 9,
                "Диагональ экрана составляет 7,81'"));
        products.add(new Product("Ноутбук Acer Aspire A315-53-395T", 10,
                "15'6 диагональ экрана"));
        products.add(new Product("ПК DEXP Aquilon O203", 11,
                "Стационарный персональный компьютер"));
        products.add(new Product("Пылесос Bosch BWD41740",12,"Пропылесосит всё"));
        products.add(new Product("Телевизор LED LG 49SM8200", 13,
                "Крутой телевизор от именитого бренда"));
    }

    //Инициализация поля Picture всех экземпляров класса Product
    private void InitializeImages()
    {
        for (int i = 0; i < products.size(); i++)
        {
            InputStream inputStream = null;
            try
            {
                inputStream = getApplicationContext().getAssets().open(products.get(i).getName() + ".jpg");
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                products.get(i).setPicture(bitmap);
            }
            catch (IOException exp)
            {
                exp.printStackTrace();
//                Log.d("Err InitializeImage",exp + "");
            }
        }
    }

}
