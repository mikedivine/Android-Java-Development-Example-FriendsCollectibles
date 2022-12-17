package com.example.friendscollectibles.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.RegisterActivity;
import com.example.friendscollectibles.admin.ViewUsers;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.user.User;
import com.example.friendscollectibles.user.UsersAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewItems extends AppCompatActivity implements SelectListener {

  Button goBack;
  FloatingActionButton addItem;
  RecyclerView rvItems;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_items);

    goBack = findViewById(R.id.goBack);
    addItem = findViewById(R.id.addItem);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);

      if (loggedInPreferences.getBoolean("Admin", false)) {
        addItem.setVisibility(View.VISIBLE);
      } else {
        addItem.setVisibility(View.GONE);
      }


    goBack.setOnClickListener(view -> finish());

    addItem.setOnClickListener(view -> {
      openAddItemActivity();
    });

    rvItems = findViewById(R.id.items);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<Item> allItems = friendsCollectiblesDAO.getAllItems();
    ItemsAdapter adapter = new ItemsAdapter(allItems,this,this);

    rvItems.setAdapter(adapter);

    StaggeredGridLayoutManager gridLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    rvItems.setLayoutManager(gridLayoutManager);
  }

  @Override
  public void onResume(){
    super.onResume();

    rvItems = findViewById(R.id.items);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<Item> allItems = friendsCollectiblesDAO.getAllItems();
    ItemsAdapter adapter = new ItemsAdapter(allItems,this,this);

    rvItems.setAdapter(adapter);

    StaggeredGridLayoutManager gridLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

    rvItems.setLayoutManager(gridLayoutManager);
  }

  @Override
  public void onItemClicked(Item item) {

    openEditItem(item);
  }

  private void openEditItem(Item item) {

    Integer itemID = item.getItemID();
    Intent i = new Intent(ViewItems.this, EditItem.class);
    i.putExtra("itemID",itemID);
    startActivity(i);
  }

  private void openAddItemActivity() {
    Intent intent = new Intent(this, AddItem.class);
    startActivity(intent);
  }
}