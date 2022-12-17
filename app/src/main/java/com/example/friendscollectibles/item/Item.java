package com.example.friendscollectibles.item;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Item implements Parcelable {

  @PrimaryKey(autoGenerate = true)
  Integer itemID;

  @ColumnInfo(name = "prodName")
  String prodName;

  @ColumnInfo(name = "resID")
  Integer resID;

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

  public Integer getResID() {
    return resID;
  }

  public void setResID(Integer resID) {
    this.resID = resID;
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

  public Item() {

  }

  public Item(Parcel in ) {
    readFromParcel( in );
  }

  public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

    public Item createFromParcel(Parcel in ) {
      return new Item( in );
    }

    public Item[] newArray(int size) {
      return new Item[size];
    }
  };


  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {

    dest.writeInt(itemID);
    dest.writeString(prodName);
    dest.writeString(description);
    dest.writeDouble(price);
    dest.writeInt(qty);
  }

  private void readFromParcel(Parcel in ) {

    itemID = in .readInt();
    prodName = in .readString();
    description = in .readString();
    price = in .readDouble();
    qty = in .readInt();
  }
}
