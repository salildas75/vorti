package com.example.salildas.vorti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class NewPostActivity extends AppCompatActivity{

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_STREET_NO = "street_no";
    private static final String KEY_STREET_NAME = "street_name";
    private static final String KEY_CITY = "city";
    private static final String KEY_STATE = "state";
    private static final String KEY_SEAT = "seat";
    private static final String KEY_BATH = "bath";
    private static final String KEY_PRICE = "price";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_EMPTY = "";
    private EditText etStreetNo;
    private EditText etStreetName;
    private EditText etCity;
    private EditText etState;
    private EditText etSeat;
    private EditText etBathroom;
    private EditText etPrice;

    private String streetNo;
    private String streetName;
    private String city;
    private String state;
    private String seat;
    private String bathroom;
    private String price;
    private String userPhone;
    private ProgressDialog pDialog;
    public String add_post_url = "http://192.168.0.102/vorti_php/member/add_post.php";
    private SessionHandler session;
    Uri imageUri;

    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    Button pickImageButton;

    private Button btnPostSave;  // The save button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        etStreetNo = findViewById(R.id.etStreetNo);
        etStreetName = findViewById(R.id.etStreetName);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etSeat = findViewById(R.id.etSeat);
        etBathroom = findViewById(R.id.etBathroom);
        etPrice = findViewById(R.id.etPrice);

        //phone no
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();


        btnPostSave = findViewById(R.id.btnPostSave);

        // Set button's onClick listener object.

        btnPostSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView = (ImageView) findViewById(R.id.image_view);
                pickImageButton = (Button) findViewById(R.id.pick_image_button);

                streetNo = etStreetNo.getText().toString().trim();
                streetName = etStreetName.getText().toString().trim();
                city = etCity.getText().toString().trim();
                state = etState.getText().toString().trim();
                seat = etSeat.getText().toString().trim();
                bathroom = etBathroom.getText().toString().trim();
                price = etPrice.getText().toString().trim();
                userPhone = user.getPhone();
                pickImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openGallery();
                    }
                });


                if (validateInputs()) {
                    addPost();
                }
            }
        });

    }
    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


    /**
     * Display Progress bar while registering
     */
    private void displayLoader() {
        pDialog = new ProgressDialog(NewPostActivity.this);
        pDialog.setMessage("Saving.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void addPost() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_STREET_NO, streetNo);
            request.put(KEY_STREET_NAME, streetName);
            request.put(KEY_CITY, city);
            request.put(KEY_STATE, state);
            request.put(KEY_SEAT, seat);
            request.put(KEY_BATH, bathroom);
            request.put(KEY_PRICE, price);
            request.put(KEY_PHONE, userPhone);
            request.put(KEY_IMAGE, imageUri);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, add_post_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
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



    private boolean validateInputs() {
        if (KEY_EMPTY.equals(streetNo)) {
            etStreetNo.setError("Street no cannot be empty");
            etStreetNo.requestFocus();
            return false;

        }

        if (KEY_EMPTY.equals(streetName)) {
            etStreetName.setError("Street name cannot be empty");
            etStreetName.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(city)) {
            etCity.setError("City cannot be empty");
            etCity.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(state)) {
            etState.setError("State cannot be empty");
            etState.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(seat)) {
            etSeat.setError("Seat cannot be empty");
            etSeat.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(bathroom)) {
            etBathroom.setError("Bath cannot be empty");
            etBathroom.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(price)) {
            etPrice.setError("Price cannot be empty");
            etPrice.requestFocus();
            return false;
        }

        return true;
    }
}
