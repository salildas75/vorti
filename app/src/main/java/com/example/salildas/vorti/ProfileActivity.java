package com.example.salildas.vorti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_REGULAR_PHONE = "regular_phone";
    private static final String KEY_RENTER_PHONE = "renter_phone";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_TOTAL_PRICE = "total_price";
    private static final String KEY_COUNT_DAY = "count_day";
    private static final String KEY_REQUEST_STATUS = "request_status";
    private static final String KEY_REQUEST_CREATED_AT = "request_created_at";

    private String regularPhone;

    private SessionHandler session;
    private ProgressDialog pDialog;

    Constants constants = new Constants();
    public String show_request_url = constants.baseURL+"member/show_request.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        regularPhone = user.getPhone();

        TextView fullName = findViewById(R.id.fullName);
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        TextView roll = findViewById(R.id.roll);
        TextView role = findViewById(R.id.role);

        fullName.setText(user.getFullName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        roll.setText(user.getRoll());
        role.setText(user.getRole());

        Button logoutBtn = findViewById(R.id.btnLogout);
        Button newPostBtn = findViewById(R.id.btnNewPost);
        LinearLayout requestTv = findViewById(R.id.tvRequest);

        if(user.getRole().equals("Regular")){
            newPostBtn.setVisibility(View.GONE);
        }

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, NewPostActivity.class);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

        requestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showRequest();

            }
        });


    }


    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(ProfileActivity.this);
        pDialog.setMessage("Sending Request.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void showRequest() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_REGULAR_PHONE, regularPhone);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, show_request_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check Request
                            if(response.getString(KEY_REQUEST_STATUS).equals("pending")){

                                if (response.getInt(KEY_STATUS) == 0) {

                                    Intent intent = new Intent(ProfileActivity.this, RequestShowActivity.class);

                                    intent.putExtra(KEY_RENTER_PHONE,response.getInt(KEY_RENTER_PHONE));
                                    intent.putExtra(KEY_START_DATE,response.getString(KEY_START_DATE));
                                    intent.putExtra(KEY_END_DATE,response.getString(KEY_END_DATE));
                                    intent.putExtra(KEY_TOTAL_PRICE,response.getDouble(KEY_TOTAL_PRICE));
                                    intent.putExtra(KEY_COUNT_DAY,response.getInt(KEY_COUNT_DAY));
                                    intent.putExtra(KEY_REQUEST_STATUS,response.getString(KEY_REQUEST_STATUS));
                                    intent.putExtra(KEY_REQUEST_CREATED_AT,response.getString(KEY_REQUEST_CREATED_AT));

                                    startActivity(intent);

                                    Toast.makeText(getApplicationContext(),
                                            response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(),
                                        "Your request has been accepted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }

}
