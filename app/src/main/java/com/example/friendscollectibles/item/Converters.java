package com.example.friendscollectibles.item;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

  @TypeConverter
  public ArrayList<Integer> fromString(String value) {
    ArrayList<Integer> list = new ArrayList<>();

    String[] array = value.split(",");

    for (String s : array) {
      if (!s.isEmpty()) {
        list.add(Integer.parseInt(s));
      }
    }
    return list;
  }

  @TypeConverter
  public String fromArrayList(ArrayList<Integer> list) {
    String genreIds = "";
    for (int i : list) {
      genreIds += "," + i;
    }
    return genreIds;
  }
}
