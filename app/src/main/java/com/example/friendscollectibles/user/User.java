package com.example.friendscollectibles.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.friendscollectibles.db.AppDatabase;

/**
 * Title: Users
 * Abstract: Purpose of this program is to create an object for users to stored and accessed from the database.
 * Author: Mike Divine
 * Date: 12/2/2022
 */

@Entity(tableName = AppDatabase.USER_TABLE)
public class User {

  @PrimaryKey(autoGenerate = true)
  Integer userID;

  @ColumnInfo(name = "username")
  String username;

  @ColumnInfo(name = "password")
  String password;

  @ColumnInfo(name = "isAdmin")
  Boolean isAdmin;

  public Integer getUserID() {
    return userID;
  }

  public void setUserID(Integer userID) {
    this.userID = userID;
  }

  public Boolean getAdmin() {
    return isAdmin;
  }

  public void setAdmin(Boolean admin) {
    isAdmin = admin;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
