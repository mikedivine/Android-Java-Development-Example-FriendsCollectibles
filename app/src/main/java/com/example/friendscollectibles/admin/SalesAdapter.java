package com.example.friendscollectibles.admin;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.user.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.ViewHolder> {

  private HashMap<Item,List<Integer>> mSales;
  private Context context;

  public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView itemTitle, itemPrice;
    public ImageView itemImage;
    public RecyclerView rcSales;

    public ViewHolder(View itemView) {

      super(itemView);

      itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);
      itemPrice = (TextView) itemView.findViewById(R.id.itemPrice);
      itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
      rcSales = itemView.findViewById(R.id.salesItemReport);
    }
  }

  public SalesAdapter(HashMap<Item,List<Integer>> sales, Context context) {
    mSales = sales;
    this.context = context;
  }

  @Override
  public SalesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    View salesView = inflater.inflate(R.layout.sales_history_layout, parent, false);

    ViewHolder viewHolder = new ViewHolder(salesView);
    return viewHolder;
  }

  // Involves populating data into the item through holder
  @Override
  public void onBindViewHolder(SalesAdapter.ViewHolder holder, int position) {

    Item item = null;
    List<Integer> usersThatPurchased = null;
    int toPosition = 0;

    for (Map.Entry<Item,List<Integer>> mapElement : mSales.entrySet()) {

      if (toPosition == position) {

        item = mapElement.getKey();
        usersThatPurchased = (mapElement.getValue());
        break;
      }
      toPosition++;
    }

    holder.itemTitle.setText(item.getProdName());
    holder.itemImage.setImageResource(item.getResID());
    String setItemPrice = "$" + item.getPrice();
    holder.itemPrice.setText(setItemPrice);
    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
    holder.rcSales.setLayoutManager(layoutManager);

    SalesItemAdapter salesItemAdapter = new SalesItemAdapter(item, usersThatPurchased, holder.rcSales.getContext());
    holder.rcSales.setAdapter(salesItemAdapter);
  }

  @Override
  public int getItemCount() {
    return mSales.size();
  }



}
