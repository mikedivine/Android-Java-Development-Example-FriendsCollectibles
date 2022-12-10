package com.example.friendscollectibles.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.user.User;

@Database(entities = {User.class, Item.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public static final String DB_NAME = "FC_DATABASE";
  public static final String USER_TABLE = "FC_TABLE";
  public static final String ITEM_TABLE = "ITEM_TABLE";

  public static AppDatabase appDatabase;

  public static synchronized AppDatabase getAppDatabase(Context context) {

    if (appDatabase == null) {
      appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
          .fallbackToDestructiveMigration()
          .allowMainThreadQueries()
          .build();
    }

    return appDatabase;
  }

  public abstract FriendsCollectiblesDAO friendsCollectiblesDAO();
}
