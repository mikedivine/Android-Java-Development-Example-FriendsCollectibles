package com.example.friendscollectibles.item;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendscollectibles.R;

import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.user.User;

import java.io.IOException;
import java.util.ArrayList;


public class EditItem extends AppCompatActivity {

  TextView itemTitle, itemDescription, itemPrice, itemQty;
  EditText editItemTitle, editItemDescription, editItemPrice, editItemQty;
  Button goBack, goBack2, purchaseItem, submitChanges, deleteItem;
  ImageView itemImage, itemImage2;
  String imageURI = null;
  Integer userID = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_item);

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);
    userID = loggedInPreferences.getInt("userID",1);

    Bundle extras = getIntent().getExtras();
    Integer itemID = null;
    if (extras != null) {
      itemID = extras.getInt("itemID");
    }

    setItemById();

    goBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    goBack2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    itemImage2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        imageChooser();
      }
    });

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();
    Item item = friendsCollectiblesDAO.getItem(itemID);

    setItemFields(item);
    setItemVisibility();

    submitChanges.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        updateItem(item);
        finish();
      }
    });

    deleteItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        deleteTheItem(item);
        finish();
      }
    });

    purchaseItem.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        purchaseTheItem(item);
      }
    });

  }

  private void deleteTheItem(Item item) {
    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();
    friendsCollectiblesDAO.deleteItem(item);
  }

  private void purchaseTheItem(Item item) {

    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    User user = friendsCollectiblesDAO.getUser(userID);

    ArrayList<Integer> itemIds = user.getItems();
    ArrayList<Integer> itemQty = user.getItemQty();

    boolean found = false;

    for (int i = 0; i < itemIds.size(); i++) {

      if (itemIds.get(i) == item.getItemID()) {
        found = true;
        itemQty.set(i,itemQty.get(i)+1);
        break;
      }
    }

    if (!found) {
      itemIds.add(item.getItemID());
      itemQty.add(1);
    }

    user.setItems(itemIds);
    user.setItemQty(itemQty);

    if (item.getQty() > 0) {

      item.setQty(item.getQty()-1);
      friendsCollectiblesDAO.updateItem(item);
      friendsCollectiblesDAO.updateUser(user);
      String itemQtyText = "Qty: " + Integer.toString(item.getQty());
      TextView theItemQty = findViewById(R.id.itemQty);
      theItemQty.setText(itemQtyText);
      Toast.makeText(getApplicationContext(), "Item " + item.getProdName() + " has been ordered.", Toast.LENGTH_SHORT).show();
    } else {

      Toast.makeText(getApplicationContext(), "Sorry, We are out of this item.", Toast.LENGTH_SHORT).show();
    }
  }

  private void setItemById() {
    itemTitle = findViewById(R.id.itemTitle);
    itemDescription = findViewById(R.id.itemDescription);
    itemPrice = findViewById(R.id.itemPrice);
    itemQty = findViewById(R.id.itemQty);

    itemImage = findViewById(R.id.itemImage);
    itemImage2 = findViewById(R.id.itemImage2);

    editItemTitle = findViewById(R.id.editItemTitle);
    editItemDescription = findViewById(R.id.editItemDescription);
    editItemPrice = findViewById(R.id.editItemPrice);
    editItemQty = findViewById(R.id.editItemQty);

    goBack = findViewById(R.id.goBack);
    purchaseItem = findViewById(R.id.purchaseItem);
    goBack2 = findViewById(R.id.goBack2);
    submitChanges = findViewById(R.id.submitChanges);
    deleteItem = findViewById(R.id.deleteItem);
  }

  private void setItemFields(Item item) {

    itemTitle.setText(item.getProdName());
    itemImage.setImageResource(item.getResID());
    itemDescription.setText(item.getDescription());
    itemPrice.setText(String.format("$" + item.getPrice()));
    itemQty.setText(String.format("Qty: " + item.getQty()));
    goBack.setVisibility(View.GONE);
    purchaseItem.setVisibility(View.GONE);

    editItemTitle.setText(item.getProdName());
    itemImage2.setImageResource(item.getResID());
    editItemDescription.setText(item.getDescription());
    editItemPrice.setText(String.format("" + item.getPrice()));
    editItemQty.setText(String.format("" + item.getQty()));
    goBack2.setVisibility(View.VISIBLE);
    submitChanges.setVisibility(View.VISIBLE);
  }

  private void setItemVisibility() {

    SharedPreferences loggedInPreferences = getSharedPreferences("Logged In", MODE_PRIVATE);

    if (loggedInPreferences.getBoolean("Admin", true)) {

      itemTitle.setVisibility(View.GONE);
      itemImage.setVisibility(View.GONE);
      itemDescription.setVisibility(View.GONE);
      itemPrice.setVisibility(View.GONE);
      itemQty.setVisibility(View.GONE);
      goBack.setVisibility(View.GONE);
      purchaseItem.setVisibility(View.GONE);

      editItemTitle.setVisibility(View.VISIBLE);
      itemImage2.setVisibility(View.VISIBLE);
      editItemDescription.setVisibility(View.VISIBLE);
      editItemPrice.setVisibility(View.VISIBLE);
      editItemQty.setVisibility(View.VISIBLE);
      goBack2.setVisibility(View.VISIBLE);
      submitChanges.setVisibility(View.VISIBLE);
      deleteItem.setVisibility(View.VISIBLE);

    } else {

      editItemTitle.setVisibility(View.GONE);
      itemImage2.setVisibility(View.GONE);
      editItemDescription.setVisibility(View.GONE);
      editItemPrice.setVisibility(View.GONE);
      editItemQty.setVisibility(View.GONE);
      goBack2.setVisibility(View.GONE);
      submitChanges.setVisibility(View.GONE);
      deleteItem.setVisibility(View.GONE);

      itemTitle.setVisibility(View.VISIBLE);
      itemImage.setVisibility(View.VISIBLE);
      itemDescription.setVisibility(View.VISIBLE);
      itemPrice.setVisibility(View.VISIBLE);
      itemQty.setVisibility(View.VISIBLE);
      goBack.setVisibility(View.VISIBLE);
      purchaseItem.setVisibility(View.VISIBLE);
    }
  }

  private void updateItem(Item item) {
    AppDatabase appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    item.setProdName(editItemTitle.getText().toString());
    //item.setResID(0);
    item.setDescription(editItemTitle.getText().toString());
    item.setPrice(Double.parseDouble(editItemPrice.getText().toString()));
    item.setQty(Integer.parseInt(editItemQty.getText().toString()));

    friendsCollectiblesDAO.updateItem(item);
  }

  void imageChooser() {

    Intent i = new Intent();
    i.setType("image/*");
    i.setAction(Intent.ACTION_GET_CONTENT);

    launchGetImageActivity.launch(i);
  }

  ActivityResultLauncher<Intent> launchGetImageActivity = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      result -> {
        if (result.getResultCode()
            == Activity.RESULT_OK) {
          Intent data = result.getData();
          // do your operation from here....
          if (data != null
              && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imageURI = selectedImageUri.toString();
            Bitmap selectedImageBitmap = null;
            try {
              selectedImageBitmap
                  = MediaStore.Images.Media.getBitmap(
                  this.getContentResolver(),
                  selectedImageUri);
            }
            catch (IOException e) {
              e.printStackTrace();
            }
            itemImage2.setImageBitmap(
                selectedImageBitmap);
          }
        }
      }
  );

  ActivityResultLauncher<Intent> pullImageURI = registerForActivityResult(
      new ActivityResultContracts.StartActivityForResult(),
      new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
          if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
          }
        }
      }
  );
}