package com.tivit.inventariodmt;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ApresentacaoActivity extends AppCompatActivity implements Runnable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apresentacao);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }

    public void run(){
        startActivity(new Intent(this, MenuActivity.class));
        finish();
    }
}
