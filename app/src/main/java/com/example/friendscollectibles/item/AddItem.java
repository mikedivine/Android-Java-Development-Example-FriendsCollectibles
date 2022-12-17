package com.example.friendscollectibles.item;

import androidx.appcompat.app.AppCompatActivity;

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

public class AddItem extends AppCompatActivity {

  EditText editItemTitle, editItemDescription, editItemPrice, editItemQty;
  Button goBack2, addItem;
  ImageView itemImage2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_item);

    setItemById();

    goBack2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    itemImage2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //imageChooser();
      }
    });

    addItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        addItemToDAO();
      }
    });
  }

  private void setItemById() {

    itemImage2 = findViewById(R.id.itemImage2);

    editItemTitle = findViewById(R.id.editItemTitle);
    editItemDescription = findViewById(R.id.editItemDescription);
    editItemPrice = findViewById(R.id.editItemPrice);
    editItemQty = findViewById(R.id.editItemQty);

    goBack2 = findViewById(R.id.goBack2);
    addItem = findViewById(R.id.addItem);
  }

  private void addItemToDAO() {

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    Item item = new Item();
    item.setProdName(editItemTitle.getText().toString());
    item.setResID(0);
    item.setDescription(editItemTitle.getText().toString());
    item.setPrice(Double.parseDouble(editItemPrice.getText().toString()));
    item.setQty(Integer.parseInt(editItemQty.getText().toString()));

    friendsCollectiblesDAO.insertItem(item);
    Toast.makeText(getApplicationContext(), "Item: " + item.getProdName() + " added.", Toast.LENGTH_SHORT).show();
    finish();
  }
}