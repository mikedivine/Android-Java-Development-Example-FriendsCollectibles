package com.example.friendscollectibles.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendscollectibles.R;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<CustomViewHolder> {

  private List<Item> mItems;
  private Context context;
  private SelectListener listener;

  public ItemsAdapter(List<Item> items, Context context, SelectListener listener) {
    mItems = items;
    this.context = context;
    this.listener = listener;
  }

  @NonNull
  @Override
  public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    View itemsView = inflater.inflate(R.layout.items_layout, parent, false);

    CustomViewHolder viewHolder = new CustomViewHolder(itemsView);
    return viewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

    holder.itemTitle.setText(mItems.get(holder.getAdapterPosition()).getProdName());
    holder.itemImage.setImageResource(mItems.get(holder.getAdapterPosition()).getResID());
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

