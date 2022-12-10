package com.example.friendscollectibles.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.RegisterActivity;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.user.User;
import com.example.friendscollectibles.user.UsersAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewUsers extends AppCompatActivity {

  Button goBack;
  FloatingActionButton addUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_users);

    goBack = findViewById(R.id.goBack);
    addUser = findViewById(R.id.addUser);

    goBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    addUser.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        openRegisterActivity();
      }
    });

    RecyclerView rvUsers = findViewById(R.id.users);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<User> allUsers = friendsCollectiblesDAO.getAllUsers();
    UsersAdapter adapter = new UsersAdapter(allUsers,this);

    RecyclerView.ItemDecoration itemDecoration = new
    DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    rvUsers.addItemDecoration(itemDecoration);
    rvUsers.setAdapter(adapter);
    rvUsers.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onResume(){
    super.onResume();
    RecyclerView rvUsers = findViewById(R.id.users);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<User> allUsers = friendsCollectiblesDAO.getAllUsers();
    UsersAdapter adapter = new UsersAdapter(allUsers,this);

    RecyclerView.ItemDecoration itemDecoration = new
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    rvUsers.addItemDecoration(itemDecoration);
    rvUsers.setAdapter(adapter);
    rvUsers.setLayoutManager(new LinearLayoutManager(this));

  }

  private void openRegisterActivity() {
    Intent intent = new Intent(this, RegisterActivity.class);
    startActivity(intent);
  }
}