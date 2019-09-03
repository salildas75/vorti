package com.example.salildas.vorti;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class AccommodationActivity extends AppCompatActivity {

    private String accommodationURL = "http://192.168.0.103/vorti_php/member/accommodation.php";
    private final int jsoncode = 1;
    private ListView listView;
    ArrayList<Property> propertyArrayList;
    private PropertyAdapter propertyAdapter;
    private String city;
    private String seat;

    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation);

        listView = findViewById(R.id.accommodationListView);

        fetchJSON();

    }

    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        showSimpleProgressDialog(this, "Loading...","Fetching Json",false);

        city = getIntent().getStringExtra("SEARCH_CITY");
        seat = getIntent().getStringExtra("SEARCH_SEAT");

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(accommodationURL+"?city="+city+"&seat="+seat);
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

                }else {
                    Toast.makeText(AccommodationActivity.this, getErrorCode(), Toast.LENGTH_SHORT).show();
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

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

