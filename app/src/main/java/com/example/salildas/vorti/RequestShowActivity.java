package com.example.salildas.vorti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestShowActivity extends AppCompatActivity {

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

    private SessionHandler session;
    private ProgressDialog pDialog;
    private String regularPhone;

    Constants constants = new Constants();
    public String cancel_request_url = constants.baseURL+"member/cancel_request.php";

    TextView tvRenterPhone, tvStartDate, tvEndDate, tvTotalPrice, tvDayCount, tvRequestStatus, tvCreatedAt;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_show);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        regularPhone = user.getPhone();

        Intent intent = getIntent();
        int renterPhone = intent.getIntExtra(KEY_RENTER_PHONE, 0);
        String startDate = intent.getStringExtra(KEY_START_DATE);
        String endDate = intent.getStringExtra(KEY_END_DATE);
        double totalPrice = intent.getDoubleExtra(KEY_TOTAL_PRICE,0);
        int dayCount = intent.getIntExtra(KEY_COUNT_DAY,0);
        String requestStatus = intent.getStringExtra(KEY_REQUEST_STATUS);
        String createdAt = intent.getStringExtra(KEY_REQUEST_CREATED_AT);


        tvRenterPhone = (TextView) findViewById(R.id.renterPhone);
        tvStartDate = (TextView) findViewById(R.id.startDate);
        tvEndDate = (TextView) findViewById(R.id.endDate);
        tvTotalPrice = (TextView) findViewById(R.id.totalPrice);
        tvDayCount = (TextView) findViewById(R.id.dayCount);
        tvRequestStatus = (TextView) findViewById(R.id.requestStatus);
        tvCreatedAt = (TextView) findViewById(R.id.requestTime);

        btnCancel = (Button) findViewById(R.id.btnCancel);

        tvRenterPhone.setText(""+renterPhone);
        tvStartDate.setText(""+startDate);
        tvEndDate.setText(""+endDate);
        tvTotalPrice.setText(""+(int) totalPrice);
        tvDayCount.setText(""+dayCount);
        tvRequestStatus.setText(""+requestStatus);
        tvCreatedAt.setText(""+createdAt);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest();
            }
        });

    }

    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(RequestShowActivity.this);
        pDialog.setMessage("Sending Request.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void loadProfile() {
        Intent i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
        finish();

    }

    private void cancelRequest() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_REGULAR_PHONE, regularPhone);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, cancel_request_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check Request

                            if (response.getInt(KEY_STATUS) == 0) {

                                loadProfile();

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
