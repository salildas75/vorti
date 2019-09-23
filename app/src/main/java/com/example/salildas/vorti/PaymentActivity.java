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

public class PaymentActivity extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_REGULAR_NANE = "regular_name";
    private static final String KEY_TOTAL_COST = "total_cost";
    private static final String KEY_REGULAR_PHONE = "regular_phone";
    int number;

    Constants constants = new Constants();
    public String paid_request_url = constants.baseURL+"member/paid_request.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Intent intent = getIntent();
        String fullName = intent.getStringExtra(KEY_REGULAR_NANE);
        double totalCost = intent.getDoubleExtra(KEY_TOTAL_COST,0);
        number = intent.getIntExtra(KEY_REGULAR_PHONE,0);

        TextView tvFullName  = findViewById(R.id.fullName);
        TextView tvTotalCost  = findViewById(R.id.totalPrice);
        TextView tvNumber  = findViewById(R.id.number);
        Button paidBtn = findViewById(R.id.paidBtn);

        tvFullName.setText(""+fullName);
        tvTotalCost.setText(""+(int) totalCost);
        tvNumber.setText(""+number);


        paidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paidRequest();
            }
        });


    }

    public void paidRequest(){

        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_REGULAR_PHONE, number);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, paid_request_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //Check Request

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

                        //Display error message whenever an error occurs
                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);

    }


}
