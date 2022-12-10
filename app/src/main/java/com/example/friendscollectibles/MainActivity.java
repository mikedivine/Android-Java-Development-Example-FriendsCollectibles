package com.example.friendscollectibles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.friendscollectibles.admin.AdminActivity;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.user.UserActivity;
import com.example.friendscollectibles.user.User;

public class MainActivity extends AppCompatActivity {

  EditText username, password;

  Button login, register;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SharedPreferences firstPreferences = getSharedPreferences("First Run", MODE_PRIVATE);
    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);

    if (firstPreferences.getBoolean("First Run", true)) {

      initializeUsers();
      initializeItems();
      SharedPreferences.Editor edit = firstPreferences.edit();
      edit.putBoolean("First Run", false);
      edit.apply();
    }

    if (loggedInPreferences.getBoolean("Logged In", true)) {

      if (loggedInPreferences.getBoolean("Admin", true)) {
        openAdminActivity();
      } else {
        openUserActivity();
      }
    }

    SharedPreferences.Editor edit = loggedInPreferences.edit();
    edit.putBoolean("Logged In", false);
    edit.putBoolean("Admin", false);
    edit.apply();

    username = findViewById(R.id.editTextUsername);
    password = findViewById(R.id.editTextPassword);

    login = findViewById(R.id.login);
    register = findViewById(R.id.register);

    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openRegisterActivity();
      }
    });

    login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if (checkInput(username.getText().toString(),password.getText().toString())) {
          AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
          FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

          User FClogin = friendsCollectiblesDAO.getUser(username.getText().toString(),password.getText().toString());

          if (FClogin == null) {
            Toast.makeText(getApplicationContext(), "Incorrect username and password have been entered.", Toast.LENGTH_SHORT).show();
          } else {

            SharedPreferences.Editor edit = loggedInPreferences.edit();
            edit.putString("Username", username.getText().toString());
            edit.apply();

            if (FClogin.getAdmin()) {
              Toast.makeText(getApplicationContext(), "Admin is TRUE", Toast.LENGTH_SHORT).show();
              openAdminActivity();
            } else {
              Toast.makeText(getApplicationContext(), "Admin is FALSE", Toast.LENGTH_SHORT).show();
              openUserActivity();
            }
          }

        } else {
          Toast.makeText(getApplicationContext(), "Make sure username and password are not blank.", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private Boolean checkInput(String username, String password) {

    if (username.isEmpty() || password.isEmpty()) {
      return false;

    } else {
      return true;
    }
  }

  private void openRegisterActivity() {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  private void openUserActivity() {
    Intent intent = new Intent(this, UserActivity.class);
    startActivity(intent);
  }

  private void openAdminActivity() {
    Intent intent = new Intent(this, AdminActivity.class);
    startActivity(intent);
  }

  private void initializeUsers() {

    final User users1 = new User();
    final User users2 = new User();

    users1.setUsername("testuser1");
    users1.setPassword("testuser1");
    users1.setAdmin(Boolean.FALSE);
    users2.setUsername("admin2");
    users2.setPassword("admin2");
    users2.setAdmin(Boolean.TRUE);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    friendsCollectiblesDAO.insertUser(users1);
    friendsCollectiblesDAO.insertUser(users2);

  }

  private void initializeItems() {

    final Item item = new Item();
    final Item item2 = new Item();

    item.setProdName("TestItem 1");
    item.setDescription("This is test item 1's initial description.");
    item.setPrice(14.99);
    item.setQty(5);

    item2.setProdName("TestItem 2");
    item2.setDescription("This is test item 2's initial description.");
    item2.setPrice(9.99);
    item2.setQty(3);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    friendsCollectiblesDAO.insertItem(item);
    friendsCollectiblesDAO.insertItem(item2);


  }
}