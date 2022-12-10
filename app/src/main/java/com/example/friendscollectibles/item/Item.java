package com.example.friendscollectibles.item;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.friendscollectibles.db.AppDatabase;

/**
 * Title: Items
 * Abstract: Purpose of this program is to create an items for the store to stored and accessed from the database.
 * Author: Mike Divine
 * Date: 12/2/2022
 */

@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {

  @PrimaryKey(autoGenerate = true)
  Integer itemID;

  @ColumnInfo(name = "prodName")
  String prodName;

  @ColumnInfo(name = "description")
  String description;

  @ColumnInfo(name = "price")
  double price;

  @ColumnInfo(name = "qty")
  Integer qty;

  public Integer getItemID() {
    return itemID;
  }

  public void setItemID(Integer itemID) {
    this.itemID = itemID;
  }

  public String getProdName() {
    return prodName;
  }

  public void setProdName(String prodName) {
    this.prodName = prodName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }
}
