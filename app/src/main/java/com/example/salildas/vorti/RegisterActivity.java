package com.example.salildas.vorti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_ROLL = "roll";
    private static final String KEY_ROLE = "role";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_EMPTY = "";

    Constants constants = new Constants();

    private PinView pinView;
    private TextView topText,textU,isAccount;
    private LinearLayout first;
    private ConstraintLayout second;

    private EditText etFullName;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private EditText etEmail;
    private EditText etRoll;
    private RadioGroup radioRoleGroup;
    private RadioButton radioRoleButton;
    private RadioGroup radioGenderGroup;
    private RadioButton radioGenderButton;
    private String fullName;
    private String phone;
    private String password;
    private String confirmPassword;
    private String email;
    private String roll;
    private String role;
    private String gender;
    private ProgressDialog pDialog;
    public String register_url = constants.baseURL+"member/register.php";
    Uri imageUri;

    private static final int PICK_IMAGE = 100;
    private ImageView imageView;
    Button buttonChoose;

    public String generateOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        topText = findViewById(R.id.topText);
        pinView = findViewById(R.id.pinView);
        isAccount = findViewById(R.id.isAccount);
        first = findViewById(R.id.first_step);
        second = findViewById(R.id.secondStep);
        textU = findViewById(R.id.textView_noti);
        first.setVisibility(View.VISIBLE);

        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etEmail = findViewById(R.id.etEmail);
        etRoll = findViewById(R.id.etRoll);
        radioRoleGroup = findViewById(R.id.radioRole);
        radioGenderGroup = findViewById(R.id.radioGender);

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);


        final Button login = findViewById(R.id.btnRegisterLogin);
        final Button register = findViewById(R.id.btnRegister);

        //Launch Login screen when Login Button is clicked
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrieve the data entered in the edit texts
                fullName = etFullName.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                roll = etRoll.getText().toString().trim();

                //for User Role
                int selectedId = radioRoleGroup.getCheckedRadioButtonId();
                radioRoleButton = (RadioButton) findViewById(selectedId);
                role = radioRoleButton.getText().toString().trim();

                //for User Gender
                int genderSelectedId = radioGenderGroup.getCheckedRadioButtonId();
                radioGenderButton = (RadioButton) findViewById(genderSelectedId);
                gender = radioGenderButton.getText().toString().trim();



                if (validateInputs()) {

                    if (register.getText().equals("Register")) {

                        generateOTP = generateOTP();
                        Log.d("newOTP",generateOTP);


                        register.setText("Verify");
                        first.setVisibility(View.GONE);
                        isAccount.setVisibility(View.GONE);
                        login.setVisibility(View.GONE);
                        second.setVisibility(View.VISIBLE);
                        topText.setText("I Still don't trust you.\nTell me something that only two of us know.");

                    } else if (register.getText().equals("Verify")) {
                        String OTP = pinView.getText().toString();
                        if (OTP.equals(generateOTP)) {
                            pinView.setLineColor(Color.GREEN);
                            textU.setText("OTP Verified");
                            textU.setTextColor(Color.GREEN);
                            register.setText("Next");
                            if (register.getText().equals("Next")) {
                                registerUser();
                            }
                        } else {
                            pinView.setLineColor(Color.RED);
                            textU.setText("X Incorrect OTP");
                            textU.setTextColor(Color.RED);
                        }
                    }
                    //registerUser();
                }

            }
        });

    }

    //Random number Generator
    public static String generateOTP() {
        int randomPin   =(int)(Math.random()*9000)+1000;
        String otp  =String.valueOf(randomPin);
        return otp;
    }

    //Image Picker
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
        pDialog = new ProgressDialog(RegisterActivity.this);
        pDialog.setMessage("Signing Up.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    /**
     * Launch Dashboard Activity on Successful Sign Up
     */
    private void loadProfile() {
        Toast.makeText(getApplicationContext(),
                "Your phone number is verified now. Waiting for admin approval", Toast.LENGTH_LONG).show();
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();

    }

    private void registerUser() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            //Populate the request parameters
            request.put(KEY_FULL_NAME, fullName);
            request.put(KEY_PHONE, phone);
            request.put(KEY_PASSWORD, password);
            request.put(KEY_EMAIL, email);
            request.put(KEY_ROLL, roll);
            request.put(KEY_ROLE, role);
            request.put(KEY_GENDER, gender);
            request.put(KEY_IMAGE, imageUri);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, register_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            //Check if user got registered successfully
                            if (response.getInt(KEY_STATUS) == 0) {
                                loadProfile();

                            } else if (response.getInt(KEY_STATUS) == 1) {
                                //Display error message if number is already existsing
                                Toast.makeText(getApplicationContext(),
                                        "Phone number already taken!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(RegisterActivity.this, RegisterActivity.class);
                                startActivity(i);

                            }else {
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

    /**
     * Validates inputs and shows error if any
     *
     * @return
     */
    private boolean validateInputs() {
        if (KEY_EMPTY.equals(fullName)) {
            etFullName.setError("Full Name cannot be empty");
            etFullName.requestFocus();
            return false;

        }

        if (KEY_EMPTY.equals(phone)) {
            etPhone.setError("Phone number cannot be empty");
            etPhone.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(password)) {
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(confirmPassword)) {
            etConfirmPassword.setError("Confirm Password cannot be empty");
            etConfirmPassword.requestFocus();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Password and Confirm Password does not match");
            etConfirmPassword.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(email)) {
            etEmail.setError("Email cannot be empty");
            etEmail.requestFocus();
            return false;
        }

        if (KEY_EMPTY.equals(roll)) {
            etRoll.setError("Roll cannot be empty");
            etRoll.requestFocus();
            return false;
        }

        return true;
    }
}
