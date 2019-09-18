package com.example.salildas.vorti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ReceiveRequestShowActivity extends AppCompatActivity {

    Constants constants = new Constants();

    private String receive_request_url = constants.baseURL+"member/show_receive_request.php";
    private final int jsoncode = 1;
    private ListView receiveRequestListView;
    ArrayList<ReceiveRequest> receiveRequestArrayList;
    private ReceiveRequestAdapter receiveRequestAdapter;

    private String renterPhone;

    private SessionHandler session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_request_show);

        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();
        renterPhone = user.getPhone();

        receiveRequestListView = findViewById(R.id.receiveRequestShowListView);

        fetchJSON();

    }

    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(receive_request_url+"?renterPhone="+renterPhone);
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
                    receiveRequestArrayList = getInfo(response);
                    receiveRequestAdapter = new ReceiveRequestAdapter(this,receiveRequestArrayList);
                    receiveRequestListView.setAdapter(receiveRequestAdapter);

//                    receiveRequestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, final View view,
//                                                int position, long id) {
//
//                            ReceiveRequest clickedItem = receiveRequestArrayList.get(position);
//
//                            String btnAccept = clickedItem.;
//
//                        }
//
//                    });

                }else {
                    Toast.makeText(ReceiveRequestShowActivity.this, getErrorCode(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    public ArrayList<ReceiveRequest> getInfo(String response) {
        ArrayList<ReceiveRequest> receiveRequestArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.isNull(response)) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    ReceiveRequest receiveRequest = new ReceiveRequest();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    receiveRequest.setPhone(dataobj.getInt("req_regular_phone"));
                    receiveRequest.setStartDate(dataobj.getString("req_start_date"));
                    receiveRequest.setEndDate(dataobj.getString("req_end_date"));
                    receiveRequest.setTotalPrice(dataobj.getDouble("req_total_price"));
                    receiveRequest.setDayCount(dataobj.getInt("req_day_count"));
                    receiveRequest.setRegularFullName(dataobj.getString("user_full_name"));
                    receiveRequest.setReqDate(dataobj.getString("req_created_at"));

                    receiveRequestArrayList.add(receiveRequest);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return receiveRequestArrayList;
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

        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        finish();

        return "No Request Found";
    }


    public void AcceptButton(View view) {

        Toast.makeText(this, "Request Accepted",Toast.LENGTH_LONG).show();

    }

    public void CancelButton(View view) {

        Toast.makeText(this, "Request Cancel",Toast.LENGTH_LONG).show();

    }

    public void StartButton(View view) {

        Toast.makeText(this, "Share Start",Toast.LENGTH_LONG).show();

    }

    public void CloseButton(View view) {

        Toast.makeText(this, "Share Closed",Toast.LENGTH_LONG).show();

    }

}
