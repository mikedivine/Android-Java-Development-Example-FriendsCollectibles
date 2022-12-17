package com.example.friendscollectibles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.user.User;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

  EditText userName, password;

  Button register, cancel;

  CheckBox makeAdmin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    userName = findViewById(R.id.editTextUsername);
    password = findViewById(R.id.editTextPassword);

    cancel = findViewById(R.id.cancelButton);
    register = findViewById(R.id.registerNewUser);

    makeAdmin = findViewById(R.id.makeAdmin);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    SharedPreferences.Editor edit = loggedInPreferences.edit();

    if (loggedInPreferences.getBoolean("Admin", true)) {
      makeAdmin.setVisibility(View.VISIBLE);
    } else {
      makeAdmin.setVisibility(View.GONE);
    }

    cancel.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {
        finish();
      }
    });

    register.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View view) {

        ArrayList<Integer> itemIds = new ArrayList<>();
        ArrayList<Integer> itemQty = new ArrayList<>();
        itemIds.add(0);
        itemQty.add(0);

        final User friendsCollectibles = new User();
        friendsCollectibles.setUsername(userName.getText().toString());
        friendsCollectibles.setPassword(password.getText().toString());
        friendsCollectibles.setAdmin(makeAdmin.isChecked());
        friendsCollectibles.setItems(itemIds);
        friendsCollectibles.setItemQty(itemQty);


        if (checkInput(friendsCollectibles)) {
          AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
          FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();
          friendsCollectiblesDAO.insertUser(friendsCollectibles);
          Toast.makeText(getApplicationContext(),"User " + friendsCollectibles.getUsername() + " successfully registered.", Toast.LENGTH_SHORT).show();
          finish();

        } else {
          Toast.makeText(getApplicationContext(),"Make sure username and password have been entered.", Toast.LENGTH_SHORT).show();
        }
      }
    });

  }

  private Boolean checkInput(User friendsCollectibles) {

    if (friendsCollectibles.getUsername().isEmpty() || friendsCollectibles.getPassword().isEmpty()) {
      return false;

    } else {
      return true;
    }
  }

}