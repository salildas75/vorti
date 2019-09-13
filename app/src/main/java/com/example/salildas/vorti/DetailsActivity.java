package com.example.salildas.vorti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.salildas.vorti.BookingActivity.KEY_BATHROOM;
import static com.example.salildas.vorti.BookingActivity.KEY_CITY;
import static com.example.salildas.vorti.BookingActivity.KEY_IMAGE;
import static com.example.salildas.vorti.BookingActivity.KEY_PRICE;
import static com.example.salildas.vorti.BookingActivity.KEY_RATING;
import static com.example.salildas.vorti.BookingActivity.KEY_SEAT;
import static com.example.salildas.vorti.BookingActivity.KEY_STATE;
import static com.example.salildas.vorti.BookingActivity.KEY_STREET_NAME;
import static com.example.salildas.vorti.BookingActivity.KEY_STREET_NO;
import static com.example.salildas.vorti.BookingActivity.KEY_USER_CONTACT;

public class DetailsActivity extends AppCompatActivity {


    TextView tvAddress, tvContact, tvRating, tvPrice, tvSeat, tvBathroom, tvDistance;
    ImageView ivImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        int streetNo = intent.getIntExtra(KEY_STREET_NO, 0);
        String streetName = intent.getStringExtra(KEY_STREET_NAME);
        String city = intent.getStringExtra(KEY_CITY);
        String state = intent.getStringExtra(KEY_STATE);
        int userContact = intent.getIntExtra(KEY_USER_CONTACT,0);
        double rating = intent.getDoubleExtra(KEY_RATING,0.0);
        String image = intent.getStringExtra(KEY_IMAGE);
        double price = intent.getDoubleExtra(KEY_PRICE,0.0);
        int seat = intent.getIntExtra(KEY_SEAT,0);
        int bathroom = intent.getIntExtra(KEY_BATHROOM,0);

        ivImage = (ImageView) findViewById(R.id.image);
        tvAddress = (TextView) findViewById(R.id.address);
        tvContact = (TextView) findViewById(R.id.contactNumber);
        tvRating = (TextView) findViewById(R.id.rating);
        tvPrice = (TextView) findViewById(R.id.price);
        tvSeat = (TextView) findViewById(R.id.seat);
        tvBathroom = (TextView) findViewById(R.id.bathroom);

        Picasso.get().load(image).into(ivImage);
        tvAddress.setText("Address: "+streetNo + ", " + streetName + ", " + city + ", " + state);
        tvContact.setText("Contact: "+userContact);
        tvRating.setText("Rating: "+rating);
        tvPrice.setText("Price: "+price);
        tvSeat.setText("Seat: "+seat);
        tvBathroom.setText("Bathroom: "+bathroom);


    }
}
