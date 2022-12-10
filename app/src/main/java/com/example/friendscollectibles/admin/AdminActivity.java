package com.example.friendscollectibles.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.friendscollectibles.MainActivity;
import com.example.friendscollectibles.R;
import com.example.friendscollectibles.RegisterActivity;

public class AdminActivity extends AppCompatActivity {

  Button viewStore, viewUsers, editItems, logout;
  TextView welcome;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    SharedPreferences.Editor edit = loggedInPreferences.edit();
    edit.putBoolean("Logged In", true);
    edit.putBoolean("Admin", true);
    edit.apply();

    viewUsers = findViewById(R.id.viewUsers);
    editItems = findViewById(R.id.editItems);
    welcome = findViewById(R.id.textViewWelcome);
    logout = findViewById(R.id.logout);
    
    welcome.setText("Welcome " + loggedInPreferences.getString("Username","Default Value"));

    viewUsers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openViewUsersActivity();
      }
    });

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

  private void openRegisterActivity() {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  private void openViewUsersActivity() {
    Intent intent = new Intent(this, ViewUsers.class);
    startActivity(intent);
  }
}