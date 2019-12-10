package com.cryptodev.cryptopedia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class FirstLayout extends AppCompatActivity   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_layout);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(FirstLayout.this, MainActivity.class);
                FirstLayout.this.startActivity(mInHome);
                FirstLayout.this.finish();
            }
        }, 2000);
    }

}
