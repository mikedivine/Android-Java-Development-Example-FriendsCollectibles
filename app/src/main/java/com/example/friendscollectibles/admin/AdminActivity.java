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
import com.example.friendscollectibles.item.ViewItems;

public class AdminActivity extends AppCompatActivity {

  Button viewStore, viewUsers, editItems, logout, salesReport, itemsReport;
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

    setItemById();

    welcome.setText("Welcome " + loggedInPreferences.getString("Username","Default Value"));

    viewUsers.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openViewUsersActivity();
      }
    });

    editItems.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openViewItemsActivity();
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

    salesReport.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        runSalesReport();
      }
    });

    itemsReport.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        runItemsReport();
      }
    });
  }

  private void runItemsReport() {

    Intent intent = new Intent(this, ItemsReport.class);
    startActivity(intent);
//    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
//    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();
//
//    List<Item> allItems = friendsCollectiblesDAO.getAllItems();
//
//    for (int i = 0; i < allItems.size(); i++) {
//
//
//    }
  }

  private void runSalesReport() {

    Intent intent = new Intent(this, SalesReport.class);
    startActivity(intent);
  }

  private void setItemById() {

    viewUsers = findViewById(R.id.viewUsers);
    editItems = findViewById(R.id.editItems);
    welcome = findViewById(R.id.textViewWelcome);
    logout = findViewById(R.id.logout);
    salesReport = findViewById(R.id.salesReport);
    itemsReport = findViewById(R.id.itemsReport);
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

  private void openViewItemsActivity() {
    Intent intent = new Intent(this, ViewItems.class);
    startActivity(intent);
  }
}