package com.example.friendscollectibles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.friendscollectibles.admin.AdminActivity;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.user.UserActivity;
import com.example.friendscollectibles.user.User;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  EditText username, password;

  Button login, register;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MediaPlayer music = MediaPlayer.create(this, R.raw.friends);
    music.start();

    SharedPreferences firstPreferences = getSharedPreferences("First Run", MODE_PRIVATE);
    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);

    if (firstPreferences.getBoolean("First Run", true)) {

      initializeUsers();
      initializeItems();
      SharedPreferences.Editor edit = firstPreferences.edit();
      edit.putBoolean("First Run", false);
      edit.apply();
    }

    if (loggedInPreferences.getBoolean("Logged In", false)) {

      if (loggedInPreferences.getBoolean("Admin", false)) {
        openAdminActivity(getTheUser(loggedInPreferences.getInt("userID",1)),music);
      } else {
        openUserActivity(getTheUser(loggedInPreferences.getInt("userID",1)),music);
      }
    }

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

          User currentUser = getTheUser(0);

          if (currentUser == null) {
            Toast.makeText(getApplicationContext(), "Incorrect username and password have been entered.", Toast.LENGTH_SHORT).show();
          } else {

            if (currentUser.getAdmin()) {

              openAdminActivity(currentUser,music);

            } else {

              openUserActivity(currentUser,music);
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

  private User getTheUser(Integer userID) {
    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    if (userID == 0) {
      return friendsCollectiblesDAO.getUser(username.getText().toString(),password.getText().toString());
    } else {
      return friendsCollectiblesDAO.getUser(userID);
    }
  }

  private void openRegisterActivity() {

    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }

  private void openUserActivity(User user, MediaPlayer music) {

    music.stop();
    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    SharedPreferences.Editor edit = loggedInPreferences.edit();
    edit.putString("Username", user.getUsername().toString());
    edit.putInt("userID", user.getUserID());
    edit.putBoolean("Logged In", true);
    edit.putBoolean("Admin", false);
    edit.apply();

    Intent intent = new Intent(this, UserActivity.class);
    startActivity(intent);
  }

  private void openAdminActivity(User user, MediaPlayer music) {

    music.stop();
    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    SharedPreferences.Editor edit = loggedInPreferences.edit();
    edit.putString("Username", user.getUsername());
    edit.putInt("userID", user.getUserID());
    edit.putBoolean("Logged In", true);
    edit.putBoolean("Admin", true);
    edit.apply();

    Intent intent = new Intent(this, AdminActivity.class);
    startActivity(intent);
  }

  private void initializeUsers() {

    final User users1 = new User();
    final User users2 = new User();
    ArrayList<Integer> itemIds = new ArrayList<>();
    ArrayList<Integer> itemQty = new ArrayList<>();

    itemIds.add(0);
    itemQty.add(0);

    users1.setUsername("testuser1");
    users1.setPassword("testuser1");
    users1.setAdmin(Boolean.FALSE);
    users1.setItems(itemIds);
    users1.setItemQty(itemQty);
    users2.setUsername("admin2");
    users2.setPassword("admin2");
    users2.setAdmin(Boolean.TRUE);
    users2.setItems(itemIds);
    users2.setItemQty(itemQty);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    friendsCollectiblesDAO.insertUser(users1);
    friendsCollectiblesDAO.insertUser(users2);

  }

  private void initializeItems() {

    final Item item = new Item();
    final Item item2 = new Item();
    final Item item3 = new Item();
    final Item item4 = new Item();
    final Item item5 = new Item();

    String item1ResID = "friends_peephole_frame";
    int resID1 = getResources().getIdentifier(item1ResID , "drawable" ,getPackageName());
    String item2ResID = "friends_magnet";
    int resID2 = getResources().getIdentifier(item2ResID , "drawable" ,getPackageName());
    String item3ResID = "doormat";
    int resID3 = getResources().getIdentifier(item3ResID , "drawable" ,getPackageName());
    String item4ResID = "friends_snow_globe";
    int resID4 = getResources().getIdentifier(item4ResID , "drawable" ,getPackageName());
    String item5ResID = "friends_couch";
    int resID5 = getResources().getIdentifier(item5ResID , "drawable" ,getPackageName());


    item.setProdName("Friend's Peephole Frame");
    item.setDescription("Add a slice of true Friends nostalgia to your home with this super-fun Friends Peephole Picture Frame.");
    item.setPrice(24.99);
    item.setQty(5);
    item.setResID(resID1);

    item2.setProdName("Friend's TV Show Magnet");
    item2.setDescription("This magnet is around 2.5-3in and is 12mil thick which is perfect for fridges, lockers, filing cabinets, magnetic white boards, etc.");
    item2.setPrice(9.99);
    item2.setQty(12);
    item2.setResID(resID2);

    item3.setProdName("Were Ross And Rachel On A Break Doormat");
    item3.setDescription("Funny doormat decorations made in the USA.");
    item3.setPrice(9.99);
    item3.setQty(7);
    item3.setResID(resID3);

    item4.setProdName("Friends Snow Globe");
    item4.setDescription("Fall in love with winter in New York City and remember the best landmarks from television hit Friends with this collectible snow globe.");
    item4.setPrice(19.47);
    item4.setQty(3);
    item4.setResID(resID4);

    item5.setProdName("Friends Central Perk Coffee Shop 3-Seater Couch Replica.");
    item5.setDescription("There are few pieces of television furniture as iconic as the Friends Orange Central Perk Couch, and now you can own this piece of TV sitcom history");
    item5.setPrice(4599.99);
    item5.setQty(2);
    item5.setResID(resID5);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    friendsCollectiblesDAO.insertItem(item);
    friendsCollectiblesDAO.insertItem(item2);
    friendsCollectiblesDAO.insertItem(item3);
    friendsCollectiblesDAO.insertItem(item4);
    friendsCollectiblesDAO.insertItem(item5);
  }

}