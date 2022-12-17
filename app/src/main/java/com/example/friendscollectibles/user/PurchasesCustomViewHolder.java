package com.example.friendscollectibles.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendscollectibles.R;

public class PurchasesCustomViewHolder extends RecyclerView.ViewHolder {

  public ImageView itemImage;
  public TextView itemTitle, itemPrice, itemQty, itemTotal;
  public CardView cardView;

  public PurchasesCustomViewHolder(@NonNull View itemView) {

    super(itemView);

    itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
    itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
    itemPrice = (TextView) itemView.findViewById(R.id.itemAmount);
    itemQty = (TextView) itemView.findViewById(R.id.itemQty);
    itemTotal = (TextView) itemView.findViewById(R.id.itemTotal);
    cardView = itemView.findViewById(R.id.purchase_container);
  }
}
