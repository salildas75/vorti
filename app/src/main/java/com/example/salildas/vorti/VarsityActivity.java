package com.example.salildas.vorti;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

public class VarsityActivity extends AppCompatActivity {

    Constants constants = new Constants();

    private String universityURL = constants.baseURL+"member/university_all.php";
    private final int jsoncode = 1;
    private ListView listView;
    ArrayList<University> universityArrayList;
    private UniversityAdapter universityAdapter;
    private static ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varsity);

        listView = findViewById(R.id.universityListView);

        fetchJSON();

    }

    @SuppressLint("StaticFieldLeak")
    private void fetchJSON(){

        new AsyncTask<Void, Void, String>(){
            protected String doInBackground(Void[] params) {
                String response="";
                HashMap<String, String> map=new HashMap<>();
                try {
                    HttpRequest req = new HttpRequest(universityURL);
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
                    universityArrayList = getInfo(response);
                    universityAdapter = new UniversityAdapter(this,universityArrayList);
                    listView.setAdapter(universityAdapter);

                }else {
                    Toast.makeText(VarsityActivity.this, getErrorCode(), Toast.LENGTH_SHORT).show();
                }
        }
    }

    public ArrayList<University> getInfo(String response) {
        ArrayList<University> universityArrayList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.isNull(response)) {

                JSONArray dataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {

                    University university = new University();
                    JSONObject dataobj = dataArray.getJSONObject(i);

                    university.setVarsityName(dataobj.getString("university_name"));
                    university.setVarsityType(dataobj.getString("university_type"));
                    university.setGetVarsityDesc(dataobj.getString("university_description"));
                    university.setVarsityImage(dataobj.getString("university_name"));

                    universityArrayList.add(university);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return universityArrayList;
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


}
