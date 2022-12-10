package com.example.friendscollectibles.item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.user.User;
import com.example.friendscollectibles.user.UsersAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ViewItems extends AppCompatActivity {

  Button goBack;
  FloatingActionButton addItem;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_items);

    goBack = findViewById(R.id.goBack);
    addItem = findViewById(R.id.addItem);

    goBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //openRegisterActivity();
      }
    });

    RecyclerView rvItems = findViewById(R.id.items);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<Item> allItems = friendsCollectiblesDAO.getAllItems();
    ItemsAdapter adapter = new ItemsAdapter(allItems,this);

    RecyclerView.ItemDecoration itemDecoration = new
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

    rvItems.addItemDecoration(itemDecoration);
    rvItems.setAdapter(adapter);

    StaggeredGridLayoutManager gridLayoutManager =
        new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
// Attach the layout manager to the recycler view
    rvItems.setLayoutManager(gridLayoutManager);
  }
}