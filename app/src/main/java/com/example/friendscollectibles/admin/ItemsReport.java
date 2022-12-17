package com.example.friendscollectibles.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.item.SelectListener;
import com.example.friendscollectibles.user.PurchaseAdapter;
import com.example.friendscollectibles.user.User;

import java.util.ArrayList;
import java.util.List;

public class ItemsReport extends AppCompatActivity implements SelectListener {

  Button goBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_item_report);

    goBack = findViewById(R.id.goBack);

    goBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    recycleTheView();
  }

  @Override
  public void onResume() {
    super.onResume();
    recycleTheView();
  }

  public void recycleTheView() {

    RecyclerView itemReport = findViewById(R.id.itemsReport);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<Item> allItems = friendsCollectiblesDAO.getAllItems();

    PurchaseAdapter adapter = new PurchaseAdapter(allItems,this,this);

    itemReport.setAdapter(adapter);
    itemReport.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onItemClicked(Item item) {

    //do nothing here
  }
}