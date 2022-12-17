package com.example.friendscollectibles.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.EditItem;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.item.SelectListener;
import com.example.friendscollectibles.item.ViewItems;

import java.util.ArrayList;
import java.util.List;

public class PurchaseHistory extends AppCompatActivity implements SelectListener {

  Integer userID;
  Button goBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_purchase_history);
    Intent intent = getIntent();
    userID = intent.getIntExtra("userID",0);

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
    RecyclerView purchaseHistory = findViewById(R.id.purchaseHistory);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    User user = friendsCollectiblesDAO.getUser(userID);
    List<Item> allItems = friendsCollectiblesDAO.getAllItems();
    ArrayList<Integer> userItems = user.getItems();
    ArrayList<Integer> userQtys = user.getItemQty();
    List<Item> onlyUsersItems = new ArrayList<Item>();
    Item tempItem;

    for (int i = 0; i < userItems.size(); i++) {

      for (int i2 = 0; i2 < allItems.size(); i2++) {

        if (userItems.get(i) == allItems.get(i2).getItemID()) {
          tempItem = allItems.get(i2);
          tempItem.setQty(userQtys.get(i));
          onlyUsersItems.add(tempItem);
        }
      }
    }

    PurchaseAdapter adapter = new PurchaseAdapter(onlyUsersItems,this,this);

    purchaseHistory.setAdapter(adapter);
    purchaseHistory.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onItemClicked(Item item) {

    openEditItem(item);
  }

  private void openEditItem(Item item) {

    Intent i = new Intent(PurchaseHistory.this, PurchaseHistoryItem.class);
    i.putExtra("userItem", item);
    startActivity(i);
  }
}