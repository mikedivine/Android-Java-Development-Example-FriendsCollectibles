package com.example.friendscollectibles.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.friendscollectibles.MainActivity;
import com.example.friendscollectibles.R;

public class UserActivity extends AppCompatActivity {

  Button viewStore, logout;

  TextView welcome;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    SharedPreferences.Editor edit = loggedInPreferences.edit();
    edit.putBoolean("Logged In", true);
    edit.putBoolean("Admin", false);
    edit.apply();

    welcome = findViewById(R.id.textViewWelcome);
    welcome.setText("Welcome " + loggedInPreferences.getString("Username","Default Value"));

    logout = findViewById(R.id.logout);

    logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        edit.putBoolean("Logged In", false);
        edit.putBoolean("Admin", false);
        edit.apply();
        openMainActivity();
      }
    });
  }

  private void openMainActivity() {
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }
}