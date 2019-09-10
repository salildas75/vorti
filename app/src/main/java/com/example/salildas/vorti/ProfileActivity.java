package com.example.salildas.vorti;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    private SessionHandler session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        session = new SessionHandler(getApplicationContext());
        User user = session.getUserDetails();

        TextView fullName = findViewById(R.id.fullName);
        TextView phone = findViewById(R.id.phone);
        TextView email = findViewById(R.id.email);
        TextView roll = findViewById(R.id.roll);
        TextView role = findViewById(R.id.role);

        fullName.setText(user.getFullName());
        phone.setText(user.getPhone());
        email.setText(user.getEmail());
        roll.setText(user.getRoll());
        role.setText(user.getRole());

        Button logoutBtn = findViewById(R.id.btnLogout);
        Button newPostBtn = findViewById(R.id.btnNewPost);

        if(user.getRole().equals("Regular")){
            newPostBtn.setVisibility(View.GONE);
        }

        newPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfileActivity.this, NewPostActivity.class);
                startActivity(i);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.logoutUser();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

}
