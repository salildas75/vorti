package com.example.salildas.vorti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static final String KEY_RENTER_PHONE = "renter_phone";
    public static final String KEY_ROOM_PRICE = "price";

    private SessionHandler session;

    private int renterUserPhone;
    private int regularUserPhone;
    private double roomPricePerDay;

    TextView tvAddress, tvContact, tvRating, tvPrice, tvSeat, tvBathroom, tvDistance;
    ImageView ivImage;
    Button btnProcess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        regularUserPhone = Integer.parseInt(user.getPhone());

        Intent intent = getIntent();
        int streetNo = intent.getIntExtra(KEY_STREET_NO, 0);
        String streetName = intent.getStringExtra(KEY_STREET_NAME);
        String city = intent.getStringExtra(KEY_CITY);
        String state = intent.getStringExtra(KEY_STATE);
        final int userContact = intent.getIntExtra(KEY_USER_CONTACT,0);
        double rating = intent.getDoubleExtra(KEY_RATING,0.0);
        String image = intent.getStringExtra(KEY_IMAGE);
        final double price = intent.getDoubleExtra(KEY_PRICE,0.0);
        int seat = intent.getIntExtra(KEY_SEAT,0);
        int bathroom = intent.getIntExtra(KEY_BATHROOM,0);

        ivImage = (ImageView) findViewById(R.id.image);
        tvAddress = (TextView) findViewById(R.id.address);
        tvContact = (TextView) findViewById(R.id.contactNumber);
        tvRating = (TextView) findViewById(R.id.rating);
        tvPrice = (TextView) findViewById(R.id.price);
        tvSeat = (TextView) findViewById(R.id.seat);
        tvBathroom = (TextView) findViewById(R.id.bathroom);

        btnProcess = (Button) findViewById(R.id.btnProcess);

        Picasso.get().load(image).into(ivImage);
        tvAddress.setText("Address: "+streetNo + ", " + streetName + ", " + city + ", " + state);
        tvContact.setText("Contact: "+userContact);
        tvRating.setText("Rating: "+rating);
        tvPrice.setText("Price: "+price);
        tvSeat.setText("Seat: "+seat);
        tvBathroom.setText("Bathroom: "+bathroom);

        btnProcess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrieve the data entered in the edit texts
                renterUserPhone = userContact;
                roomPricePerDay = price;

                if(renterUserPhone!=regularUserPhone) {

                    Intent intent = new Intent(DetailsActivity.this, RequestActivity.class);
                    intent.putExtra(KEY_RENTER_PHONE, renterUserPhone);
                    intent.putExtra(KEY_ROOM_PRICE, roomPricePerDay);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),
                            "This is your accommodation", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

}
