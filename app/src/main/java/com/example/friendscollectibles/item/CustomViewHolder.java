package com.example.friendscollectibles.item;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendscollectibles.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

  public ImageView itemImage;
  public TextView itemTitle;
  public CardView cardView;

  public CustomViewHolder(@NonNull View itemView) {

    super(itemView);

    itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
    itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
    cardView = itemView.findViewById(R.id.item_container);
  }
}
