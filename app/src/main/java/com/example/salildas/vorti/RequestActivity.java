package com.example.salildas.vorti;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import static com.example.salildas.vorti.DetailsActivity.KEY_RENTER_PHONE;
import static com.example.salildas.vorti.DetailsActivity.KEY_ROOM_PRICE;


public class RequestActivity extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_REGULAR_PHONE = "regular_phone";
    private static final String KEY_START_DATE = "start_date";
    private static final String KEY_END_DATE = "end_date";
    private static final String KEY_COUNT_DAY = "count_day";
    private static final String KEY_TOTAL_PRICE = "total_price";

    private String renterUserPhone;
    private String regularUserPhone;
//    private String roomPricePerDay;
    public String startDate;
    public String endDate;

    int sDay,eDay,countDay,sMonth,eMonth,sYear,eYear;
    double totalPrice;

    private SessionHandler session;
    private ProgressDialog pDialog;

    Constants constants = new Constants();
    public String rent_request_url = constants.baseURL+"member/rent_request.php";

    private TextView tvStartDate;
    private TextView tvEndDate;
    private DatePickerDialog.OnDateSetListener startDateSetListener;
    private DatePickerDialog.OnDateSetListener endDateSetListener;

    Button btnReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();

        Intent intent = getIntent();
        final int userContact = intent.getIntExtra(KEY_RENTER_PHONE,0);
        final double roomPrice = intent.getDoubleExtra(KEY_ROOM_PRICE,0);

        btnReq = (Button) findViewById(R.id.btnRequest);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);


        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RequestActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RequestActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("date", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                startDate = month + "/" + day + "/" + year;
                sDay = day;
                sMonth = month;
                sYear = year;
                tvStartDate.setText(startDate);
            }
        };

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("date", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                endDate = month + "/" + day + "/" + year;
                eDay = day;
                eMonth = month;
                eYear = year;
                tvEndDate.setText(endDate);
            }
        };



        btnReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sDay>0 && eDay>0) {
                    if (sDay <= eDay && sMonth <= eMonth && sYear <= eYear) {
                        //Retrieve the data entered in the edit texts
                        renterUserPhone = Integer.toString(userContact);
                        regularUserPhone = user.getPhone();
//                roomPricePerDay = Double.toString(roomPrice);
                        countDay = eDay - sDay;
                        totalPrice = roomPrice * countDay;

                        rentReq();

//                        Toast.makeText(RequestActivity.this, "Your request is being processed for renter approval.\n"+"Total price:-" + totalPrice, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RequestActivity.this,
                                "Invalid Date", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(RequestActivity.this,
                            "Please put date", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /**
     * Display Progress bar while Logging in
     */

    private void displayLoader() {
        pDialog = new ProgressDialog(RequestActivity.this);
        pDialog.setMessage("Sending Request.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void rentReq() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_RENTER_PHONE, renterUserPhone);
            request.put(KEY_REGULAR_PHONE, regularUserPhone);
            request.put(KEY_START_DATE, startDate);
            request.put(KEY_END_DATE, endDate);
            request.put(KEY_COUNT_DAY, countDay);
            request.put(KEY_TOTAL_PRICE, totalPrice);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, rent_request_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got logged in successfully

                            if (response.getInt(KEY_STATUS) == 0) {

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
