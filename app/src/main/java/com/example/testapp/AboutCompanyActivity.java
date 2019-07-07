package com.example.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutCompanyActivity extends AppCompatActivity {

    TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_company);
        setTitle(getString(R.string.about_company));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        link = findViewById(R.id.textViewLinkSiteCompany);

        //Слушатель на ссылку
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AboutCompanyActivity.this);
                builder.setMessage(R.string.openInBrowser);
                builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH, Uri.parse("https://github.com/kotoprizrak"));
//                        startActivity(intent);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.linkSiteCompany)));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }


    //Обеспечивает возврат в предыдущую активность
    @Override
    public boolean onSupportNavigateUp()
    {
        finish();
        return true;
    }
}
