package com.example.friendscollectibles.user;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendscollectibles.MainActivity;
import com.example.friendscollectibles.R;
import com.example.friendscollectibles.admin.ViewUsers;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.EditItem;
import com.example.friendscollectibles.item.ViewItems;

public class UserActivity extends AppCompatActivity {

  Button viewStore, logout, changePassword, purchaseHistory, deleteAccount;

  TextView welcome;

  Integer userID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);

    userID = loggedInPreferences.getInt("userID",1);
    setItemById();

    welcome.setText("Welcome " + loggedInPreferences.getString("Username","Default Value"));


    logout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openMainActivity();
      }
    });

    viewStore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openViewItems();
      }
    });

    changePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openPasswordActivity();
      }
    });

    purchaseHistory.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        showPurchaseHistory(userID);
      }
    });

    deleteAccount.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deleteTheAccount(userID);
      }
    });
  }

  private void setItemById() {

    welcome = findViewById(R.id.textViewWelcome);
    logout = findViewById(R.id.logout);
    viewStore = findViewById(R.id.ViewStore);
    changePassword = findViewById(R.id.changePassword);
    purchaseHistory = findViewById(R.id.purchaseHistory);
    deleteAccount = findViewById(R.id.deleteAccount);
  }

  private void showPurchaseHistory(Integer userID) {
    Intent i = new Intent(this, PurchaseHistory.class);
    i.putExtra("userID",userID);
    startActivity(i);
  }

  private void openMainActivity() {

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    SharedPreferences.Editor edit = loggedInPreferences.edit();
    edit.putBoolean("Logged In", false);
    edit.putBoolean("Admin", false);
    edit.apply();
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);
  }

  private void openViewItems() {

    Intent intent = new Intent(this, ViewItems.class);
    startActivity(intent);
  }

  private void openPasswordActivity() {

    Intent intent = new Intent(this, Password.class);
    startActivity(intent);
  }

  private void deleteTheAccount(Integer userID) {

    new AlertDialog.Builder(this)
      .setMessage("Are you sure you want to Delete your Account?")
      .setTitle("Delete Account!!")
      .setPositiveButton("Delete Now", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {

          AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
          FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

          User user = friendsCollectiblesDAO.getUser(userID);
          friendsCollectiblesDAO.deleteUser(user);

          SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
          SharedPreferences.Editor edit = loggedInPreferences.edit();
          edit.putBoolean("Logged In", false);
          edit.putBoolean("Admin", false);
          edit.apply();

          openMainActivity();
        }
      })
      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int id) {

        }
      })
        .show();
  }
}