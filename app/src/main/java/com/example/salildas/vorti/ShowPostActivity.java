package com.example.salildas.vorti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ShowPostActivity extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_STREET_NO = "street_no";
    private static final String KEY_STREET_NAME = "street_name";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_SEAT = "seat";
    private static final String KEY_BATH = "bath";
    private static final String KEY_PRICE = "price";
    private static final String KEY_ACC_STATUS = "acc_status";

    private String userPhone;

    private SessionHandler session;

    Constants constants = new Constants();
    public String delete_renter_post_url = constants.baseURL+"member/delete_renter_post.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        userPhone = user.getPhone();

        Intent intent = getIntent();

        int streetNo = intent.getIntExtra(KEY_STREET_NO, 0);
        String streetName = intent.getStringExtra(KEY_STREET_NAME);
        String city = intent.getStringExtra(KEY_CITY);
        String state = intent.getStringExtra(KEY_STATE);
        int seat = intent.getIntExtra(KEY_SEAT,0);
        int bathroom = intent.getIntExtra(KEY_BATH,0);
        double price = intent.getDoubleExtra(KEY_PRICE,0);
        String accStatus = intent.getStringExtra(KEY_ACC_STATUS);

        TextView tvStreetNo = findViewById(R.id.streetNo);
        TextView tvStreetName = findViewById(R.id.streetName);
        TextView tvCity = findViewById(R.id.city);
        TextView tvState = findViewById(R.id.state);
        TextView tvSeat = findViewById(R.id.seat);
        TextView tvBathroom = findViewById(R.id.bathroom);
        TextView tvPrice = findViewById(R.id.price);
        TextView tvAccStatus = findViewById(R.id.accStatus);

        tvStreetNo.setText(""+streetNo);
        tvStreetName.setText(""+streetName);
        tvCity.setText(""+city);
        tvState.setText(""+state);
        tvSeat.setText(""+seat);
        tvBathroom.setText(""+bathroom);
        tvPrice.setText(""+price);
        tvAccStatus.setText(""+accStatus);

        Button btnDelete = findViewById(R.id.btnDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDelete();
            }
        });

    }

    private void postDelete(){

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_PHONE, userPhone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, delete_renter_post_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {

                                Intent intent = new Intent(ShowPostActivity.this, ProfileActivity.class);
                                startActivity(intent);

                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }

}
