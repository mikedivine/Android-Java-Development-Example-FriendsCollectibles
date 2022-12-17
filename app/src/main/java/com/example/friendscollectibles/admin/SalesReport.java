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
import java.util.HashMap;
import java.util.List;

public class SalesReport extends AppCompatActivity implements SelectListener {

  Button goBack;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sales_report);

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

    RecyclerView itemReport = findViewById(R.id.salesReport);

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    List<Item> allItems = friendsCollectiblesDAO.getAllItems();
    List<User> allUsers = friendsCollectiblesDAO.getAllUsers();
    HashMap<Item,List<Integer>> itemsWithSales = new HashMap<>();
    List<Integer> userIds = new ArrayList<Integer>();

    ArrayList<Integer> userItems;
    ArrayList<Integer> userQtys;

    for (int i = 0; i < allItems.size(); i++) {

      for (int i2 = 0; i2 < allUsers.size(); i2++) {

        userItems = allUsers.get(i2).getItems();
        userItems.remove((Integer)0);
        userQtys = allUsers.get(i2).getItemQty();
        userQtys.remove((Integer)0);

        for (int i3 = 0; i3 < userItems.size(); i3++) {

          if (userItems.get(i3) == allItems.get(i).getItemID()) {

            if (!itemsWithSales.containsKey(allItems.get(i))) {

              userIds.add(allUsers.get(i2).getUserID());
              List<Integer> clonedList = new ArrayList<Integer>(userIds);
              itemsWithSales.put(allItems.get(i),clonedList);

            } else {

              userIds = itemsWithSales.get(allItems.get(i));
              if (!userIds.contains(allUsers.get(i2).getUserID())) {
                userIds.add(allUsers.get(i2).getUserID());
                List<Integer> clonedList = new ArrayList<Integer>(userIds);
                itemsWithSales.replace(allItems.get(i),clonedList);
              }
            }
          }
        }
        userIds.clear();
      }
    }

    SalesAdapter adapter = new SalesAdapter(itemsWithSales,this);

    itemReport.setAdapter(adapter);
    itemReport.setLayoutManager(new LinearLayoutManager(this));
  }

  @Override
  public void onItemClicked(Item item) {

    //do nothing here
  }

}