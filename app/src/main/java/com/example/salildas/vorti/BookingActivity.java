package com.example.salildas.vorti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
    }

    public void BookingSearch(View view) {
        Intent i = new Intent(getApplicationContext(), AccommodationActivity.class);
        startActivity(i);
        finish();
    }
}
