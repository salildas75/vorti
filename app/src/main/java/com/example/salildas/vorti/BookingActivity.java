package com.example.salildas.vorti;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.salildas.vorti.AccommodationActivity.showSimpleProgressDialog;

public class BookingActivity extends AppCompatActivity {

    private static final String KEY_EMPTY = "";

    private EditText etCity;
    private EditText etSeat;
    private String city;
    private String seat;
//    For this List View
    private String accommodationURL = "http://192.168.0.104/vorti_php/member/booking_all.php";
    private final int jsoncode = 1;
    private ListView listView;
    ArrayList<Property> propertyArrayList;
    private PropertyAdapter propertyAdapter;
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        listView = findViewById(R.id.accommodationListView);

        fetchJSON();

        etCity = findViewById(R.id.etSearchCity);
        etSeat = findViewById(R.id.etSearchSeat);

    }

    public void BookingSearch(View view) {
        city = etCity.getText().toString().trim();
        seat = etSeat.getText().toString().trim();
        if(validateInputs()) {
            Intent i = new Intent(getApplicationContext(), AccommodationActivity.class);
            i.putExtra("SEARCH_CITY", city);
            i.putExtra("SEARCH_SEAT", seat);
            startActivity(i);
        }

    }


    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(accommodationURL);
                    response = req.prepare(HttpRequest.Method.POST).withData(map).sendAndReadString();
                } catch (Exception e) {
                    response=e.getMessage();
                }
                return response;
            }
            protected void onPostExecute(String result) {
                //do something with response
                Log.d("newwwss",result);
                onTaskCompleted(result,jsoncode);
            }
        }.execute();
    }

    public void onTaskCompleted(String response, int serviceCode) {
        Log.d("responsejson", response);
        switch (serviceCode) {
            case jsoncode:

                if (isSuccess(response)) {
                    removeSimpleProgressDialog();  //will remove progress dialog
                    propertyArrayList = getInfo(response);
                    propertyAdapter = new PropertyAdapter(this,propertyArrayList);
                    listView.setAdapter(propertyAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, final View view,
                                                int position, long id) {
                            Toast.makeText(getApplicationContext(),
                                    "Click ListItem Number " + position, Toast.LENGTH_LONG)
                                    .show();
                        }

                    });

                }else {
                    Toast.makeText(BookingActivity.this, getErrorCode(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    public ArrayList<Property> getInfo(String response) {
        ArrayList<Property> propertyArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.isNull(response)) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    Property property = new Property();
                    JSONObject dataobj = dataArray.getJSONObject(i);
                    property.setStreetNumber(dataobj.getInt("streetNumber"));
                    property.setStreetName(dataobj.getString("streetName"));
                    property.setCity(dataobj.getString("city"));
                    property.setState(dataobj.getString("state"));
                    property.setContact(dataobj.getInt("user_contact"));
                    property.setRating(dataobj.getDouble("rating"));
                    property.setImage(dataobj.getString("image"));
                    property.setPrice(dataobj.getDouble("price"));
                    property.setSeats(dataobj.getInt("seats"));
                    property.setBathrooms(dataobj.getInt("bathrooms"));
                    property.setDistance(dataobj.getDouble("distance"));
                    propertyArrayList.add(property);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return propertyArrayList;
    }

    public boolean isSuccess(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.isNull(response)) {
                return true;
            } else {

                return false;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getErrorCode() {

        removeSimpleProgressDialog();  //will remove progress dialog
        Intent i = new Intent(this, BookingActivity.class);
        startActivity(i);
        finish();

        return "Not Found";
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    private boolean validateInputs() {
        if (KEY_EMPTY.equals(city)) {
            etCity.setError("City cannot be empty");
            etCity.requestFocus();
            return false;
        }
        if (KEY_EMPTY.equals(seat)) {
            etSeat.setError("Seat cannot be empty");
            etSeat.requestFocus();
            return false;
        }
        return true;
    }
}
