package com.example.foodgame.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodgame.R;

/**
 * Class for registration of a new user
 */
public class Registration extends AppCompatActivity {
    /**
     * Username of the new player
     */
    private EditText username;

    /**
     * Password of the new player
     */
    private EditText password;

    /**
     * Name of the new player
     */
    private EditText name;

    /**
     * Email of the new player
     */
    private EditText email;

    /**
     * The onCreate method that initiates the game with a statistics writer and a context.
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username = findViewById(R.id.tvusrname);
        password = findViewById(R.id.tvpassword);
        name = findViewById(R.id.tvname);
        email = findViewById(R.id.tvemail);
        Button register = findViewById(R.id.btnregister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUser = username.getText().toString();
                String newName = name.getText().toString();
                String newPassword = password.getText().toString();
                String newEmail = email.getText().toString();
                SharedPreferences preferences = getSharedPreferences("DATA", MODE_PRIVATE);

                if (newName.isEmpty()) {
                    name.setHint("Please enter a name");
                    name.setHintTextColor(Color.RED);
                } else if (newEmail.isEmpty()) {
                    email.setHint("Please enter an email");
                    email.setHintTextColor(Color.RED);
                } else if (newPassword.isEmpty()) {
                    password.setHint("Please enter a password");
                    password.setHintTextColor(Color.RED);
                } else if (newUser.isEmpty()) {
                    username.setHint("Please enter a Username");
                    username.setHintTextColor(Color.RED);
                } else if (preferences.contains(newUser)) {
                    username.setText("");
                    username.setHint("Username has been taken");
                    username.setHintTextColor(Color.RED);
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(newUser, newPassword);
                    editor.apply();
                    Intent login = new Intent(Registration.this, Login.class);
                    startActivity(login);
                }
            }
        });
    }
}
