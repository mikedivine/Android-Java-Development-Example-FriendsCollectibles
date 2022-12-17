package com.example.friendscollectibles.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;

public class Password extends AppCompatActivity {

  EditText password;
  Button goBack, changePassword;
  Integer userID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_password);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    userID = loggedInPreferences.getInt("userID",1);

    password = findViewById(R.id.editTextPassword);
    goBack = findViewById(R.id.cancelButton);
    changePassword = findViewById(R.id.changePassword);

    goBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    changePassword.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

        User user = friendsCollectiblesDAO.getUser(userID);
        user.setPassword(password.getText().toString());
        friendsCollectiblesDAO.updateUser(user);
        Toast.makeText(getApplicationContext(), "Password has been updated.", Toast.LENGTH_SHORT).show();
        finish();
      }
    });
  }
}