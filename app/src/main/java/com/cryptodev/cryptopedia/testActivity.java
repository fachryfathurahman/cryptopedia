package com.cryptodev.cryptopedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        String name = getIntent().getStringExtra("name");
        String symbol = getIntent().getStringExtra("symbol");
        String latest_price = getIntent().getStringExtra("price");
        String id = getIntent().getStringExtra("imageName");
        int resId = getApplicationContext().getResources().getIdentifier(id,"drawable",getApplicationContext().getPackageName());








    }

}
