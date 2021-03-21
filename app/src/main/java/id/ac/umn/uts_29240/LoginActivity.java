package id.ac.umn.uts_29240;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        this.setTitle("Login Page");

        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);

        DialogFragment fail = new LoginFailureDialog();

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String inputUsername = loginUsername.getText().toString();
                String inputPassword = loginPassword.getText().toString();
                if(inputUsername.equals("uasmobile") && inputPassword.equals("uasmobilegenap")){//launch main page activity
                    Intent intent = new Intent(LoginActivity.this, SongListActivity.class);
                    intent.putExtra("From", "login");
                    startActivity(intent);
                }
                else{// generate modal, notify failure
                    fail.show(getSupportFragmentManager(), "fail_tag");
                }
            }
        });


    }
}
