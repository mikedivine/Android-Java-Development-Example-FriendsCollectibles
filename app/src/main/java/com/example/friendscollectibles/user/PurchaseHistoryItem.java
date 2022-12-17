package com.example.friendscollectibles.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.Item;

import java.util.ArrayList;

public class PurchaseHistoryItem extends AppCompatActivity {

  TextView itemTitle, itemDescription, itemPrice, itemQty, itemTotal;
  ImageView itemImage, upQty, downQty;
  Button goBack, returnAllQtyItem;
  Item userItem = null;
  Integer userID;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_purchase_history_item);

    Bundle extras = getIntent().getExtras();
    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    userID = loggedInPreferences.getInt("userID",1);

    if (extras != null) {
      userItem = extras.getParcelable("userItem");
    }

    setItemById();

    if (userItem != null) {
      setItemInfo(userItem);
    }

    goBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    returnAllQtyItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (userItem != null) {
          returnTheItem(userItem);
        }
        finish();
      }
    });

    upQty.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        addAQty(userItem);
      }
    });

    downQty.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        subtractAQty(userItem);
      }
    });
  }

  private void addAQty(Item userItem) {

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    User user = friendsCollectiblesDAO.getUser(userID);
    Item storeItem = friendsCollectiblesDAO.getItem(userItem.getItemID());


    ArrayList<Integer> itemIds = user.getItems();
    ArrayList<Integer> itemQty = user.getItemQty();

    if (storeItem.getQty() > 0) {

      storeItem.setQty(storeItem.getQty()-1);
      userItem.setQty(userItem.getQty()+1);

      for (int i = 0; i < itemIds.size(); i++) {

        if (itemIds.get(i) == userItem.getItemID()) {

          itemQty.set(i,itemQty.get(i)+1);
          break;
        }
      }

      user.setItems(itemIds);
      user.setItemQty(itemQty);

      friendsCollectiblesDAO.updateItem(storeItem);
      friendsCollectiblesDAO.updateUser(user);

      String itemQtyText = "Qty: " + Integer.toString(userItem.getQty());
      TextView theItemQty = findViewById(R.id.itemQty);
      theItemQty.setText(itemQtyText);
      String theItemTotal = "$" + Double.toString(userItem.getQty()*userItem.getPrice());

      itemTotal.setText(theItemTotal);
    } else {
      Toast.makeText(getApplicationContext(), "Sorry, there are no more of these items available.", Toast.LENGTH_SHORT).show();
    }


  }

  private void subtractAQty(Item userItem) {

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    User user = friendsCollectiblesDAO.getUser(userID);
    Item storeItem = friendsCollectiblesDAO.getItem(userItem.getItemID());

    ArrayList<Integer> itemIds = user.getItems();
    ArrayList<Integer> itemQty = user.getItemQty();

    storeItem.setQty(storeItem.getQty()+1);
    userItem.setQty(userItem.getQty()-1);

    for (int i = 0; i < itemIds.size(); i++) {

      if (itemIds.get(i) == userItem.getItemID()) {

        itemQty.set(i,itemQty.get(i)-1);

        if (itemQty.get(i) < 1) {
          itemIds.remove(i);
          itemQty.remove(i);
        }
        break;
      }
    }

    user.setItems(itemIds);
    user.setItemQty(itemQty);

    friendsCollectiblesDAO.updateItem(storeItem);
    friendsCollectiblesDAO.updateUser(user);

    String itemQtyText = "Qty: " + Integer.toString(userItem.getQty());
    TextView theItemQty = findViewById(R.id.itemQty);
    theItemQty.setText(itemQtyText);
    String theItemTotal = "$" + Double.toString(userItem.getQty()*userItem.getPrice());

    itemTotal.setText(theItemTotal);

    if (userItem.getQty() == 0) {

      finish();
    }
  }

  private void returnTheItem(Item userItem) {

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    User user = friendsCollectiblesDAO.getUser(userID);
    Item storeItem = friendsCollectiblesDAO.getItem(userItem.getItemID());

    ArrayList<Integer> itemIds = user.getItems();
    ArrayList<Integer> itemQty = user.getItemQty();

    int theSpot = itemIds.indexOf(userItem.getItemID());

    storeItem.setQty(userItem.getQty());

    itemIds.remove(userItem.getItemID());
    itemQty.remove(theSpot);

    user.setItems(itemIds);
    user.setItemQty(itemQty);

    friendsCollectiblesDAO.updateUser(user);
    friendsCollectiblesDAO.updateItem(storeItem);
    finish();

  }

  private void setItemById() {

    itemTitle = findViewById(R.id.itemTitle);
    itemDescription = findViewById(R.id.itemDescription);
    itemPrice = findViewById(R.id.itemPrice);
    itemQty = findViewById(R.id.itemQty);
    itemTotal = findViewById(R.id.itemTotal);

    itemImage = findViewById(R.id.itemImage);
    upQty = findViewById(R.id.upQty);
    downQty = findViewById(R.id.downQty);


    goBack = findViewById(R.id.goBack);
    returnAllQtyItem = findViewById(R.id.returnAllQtyItem);
  }

  private void setItemInfo(Item userItem) {

    String theItemPrice = "$" + Double.toString(userItem.getPrice());
    String theItemQty = "Qty: " + Integer.toString(userItem.getQty());
    String theItemTotal = "$" + Double.toString(userItem.getQty()*userItem.getPrice());

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    Item item = friendsCollectiblesDAO.getItem(userItem.getItemID());

    itemTitle.setText(userItem.getProdName().toString());
    itemDescription.setText(userItem.getDescription().toString());
    itemImage.setImageResource(item.getResID());
    itemPrice.setText(theItemPrice);
    itemQty.setText(theItemQty);
    itemTotal.setText(theItemTotal);
  }
}