package com.example.friendscollectibles.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.user.User;

import java.util.List;

/**
 * Title: Data Access Object
 * Abstract: Purpose of this program is to create an interface to access the database.
 * Author: Mike Divine
 * Date: 12/2/2022
 */

@Dao
public interface FriendsCollectiblesDAO {

  @Insert
  void insertUser(User... user);

  @Update
  void updateUser(User... user);

  @Delete
  void deleteUser(User user);

  @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username=(:username) and password=(:password)")
  User getUser(String username, String password);

  @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
  List<User> getAllUsers();

  @Insert
  void insertItem(Item... item);

  @Update
  void updateItem(Item... item);

  @Delete
  void deleteItem(Item item);

  @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE prodName=(:prodName) and price=(:price)")
  Item getItem(String prodName, double price);

  @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE)
  List<Item> getAllItems();

}
