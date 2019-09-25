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

    public static final String KEY_STREET_NO = "streetNo";
    public static final String KEY_STREET_NAME = "streetName";
    public static final String KEY_CITY = "city";
    public static final String KEY_STATE = "state";
    public static final String KEY_USER_CONTACT = "userContact";
    public static final String KEY_RATING = "rating";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICE = "price";
    public static final String KEY_SEAT = "seat";
    public static final String KEY_BATHROOM = "bathroom";

    private static final String KEY_EMPTY = "";

    SessionHandler session;
    Constants constants = new Constants();

    private EditText etCity;
    private String city;
    private String gender;
//    For this List View
    private String accommodationURL = constants.baseURL+"member/booking_all.php";
    private final int jsoncode = 1;
    private ListView listView;
    ArrayList<Property> propertyArrayList;
    private PropertyAdapter propertyAdapter;
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        gender = user.getGender();

        listView = findViewById(R.id.accommodationListView);

        fetchJSON();



        etCity = findViewById(R.id.etSearchCity);

    }

    public void BookingSearch(View view) {
        city = etCity.getText().toString().trim();

        if(validateInputs()) {
            Intent i = new Intent(getApplicationContext(), AccommodationActivity.class);
            i.putExtra("SEARCH_CITY", city);
            i.putExtra("SEARCH_GENDER", gender);
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
                    HttpRequest req = new HttpRequest(accommodationURL+"?gender="+gender);
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
                            Intent intent = new Intent(BookingActivity.this, DetailsActivity.class);
                            Property clickedItem = propertyArrayList.get(position);

                            intent.putExtra(KEY_STREET_NO,clickedItem.getStreetNumber());
                            intent.putExtra(KEY_STREET_NAME,clickedItem.getStreetName());
                            intent.putExtra(KEY_CITY,clickedItem.getCity());
                            intent.putExtra(KEY_STATE,clickedItem.getState());
                            intent.putExtra(KEY_USER_CONTACT,clickedItem.getContact());
                            intent.putExtra(KEY_RATING,clickedItem.getRating());
                            intent.putExtra(KEY_IMAGE,clickedItem.getImage());
                            intent.putExtra(KEY_PRICE,clickedItem.getPrice());
                            intent.putExtra(KEY_SEAT,clickedItem.getSeats());
                            intent.putExtra(KEY_BATHROOM,clickedItem.getBathrooms());

                            startActivity(intent);
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();

        return "No Accommodation Found";
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
        return true;
    }
}
