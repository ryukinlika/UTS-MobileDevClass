package id.ac.umn.uts_29240;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    Button profileButton, loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);

        profileButton = findViewById(R.id.profileButton);
        loginButton = findViewById(R.id.loginButton);


        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent profileIntent  = new Intent(LandingActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
            }
        });

    }
}