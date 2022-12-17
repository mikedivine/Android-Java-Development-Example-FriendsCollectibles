package com.example.friendscollectibles.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendscollectibles.R;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.item.SelectListener;

import java.text.DecimalFormat;
import java.util.List;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchasesCustomViewHolder> {

  private List<Item> mItems;
  private Context context;
  private SelectListener listener;

  public PurchaseAdapter(List<Item> items, Context context, SelectListener listener) {
    mItems = items;
    this.context = context;
    this.listener = listener;
  }

  @NonNull
  @Override
  public PurchasesCustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    View itemsView = inflater.inflate(R.layout.purchase_layout, parent, false);

    PurchasesCustomViewHolder viewHolder = new PurchasesCustomViewHolder(itemsView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull PurchasesCustomViewHolder holder, int position) {

    String itemPrice = "$" + mItems.get(holder.getAdapterPosition()).getPrice();
    Integer itemQty = mItems.get(holder.getAdapterPosition()).getQty();
    DecimalFormat df = new DecimalFormat("#.##");
    String itemTotal = "$" + df.format(mItems.get(holder.getAdapterPosition()).getPrice() * itemQty);

    holder.itemTitle.setText(mItems.get(holder.getAdapterPosition()).getProdName());
    holder.itemImage.setImageResource((mItems.get(holder.getAdapterPosition()).getResID()));
    holder.itemPrice.setText(String.valueOf(itemPrice));
    holder.itemQty.setText(String.valueOf(itemQty));
    holder.itemTotal.setText(String.valueOf(itemTotal));

    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        listener.onItemClicked(mItems.get(holder.getAdapterPosition()));
      }
    });
  }

  @Override
  public int getItemCount() {
    return mItems.size();
  }

}

