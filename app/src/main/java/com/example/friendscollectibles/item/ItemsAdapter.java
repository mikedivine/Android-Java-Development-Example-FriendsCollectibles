package com.example.friendscollectibles.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.user.User;
import com.example.friendscollectibles.user.UsersAdapter;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

  public class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemImage;
    public TextView itemTitle;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the context from any ViewHolder instance.
      super(itemView);

      itemImage = (ImageView) itemView.findViewById(R.id.itemImage);
      itemTitle = (TextView) itemView.findViewById(R.id.itemTitle);

    }
  }

  private List<Item> mItems;
  private Context context;

  // Pass in the contact array into the constructor
  public ItemsAdapter(List<Item> items, Context context) {
    mItems = items;
    this.context = context;
  }

  @Override
  public com.example.friendscollectibles.item.ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate the custom layout
    View itemsView = inflater.inflate(R.layout.users_layout, parent, false);

    // Return a new holder instance
    com.example.friendscollectibles.item.ItemsAdapter.ViewHolder viewHolder = new ItemsAdapter.ViewHolder(itemsView);
    return viewHolder;
  }

  // Involves populating data into the item through holder
  @Override
  public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
    // Get the data model based on position
    Item item = mItems.get(position);

    // Set item views based on your views and data model
    TextView textView = holder.itemTitle;
    textView.setText(item.getProdName());

//    button.setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View view) {
//        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
//        FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();
//        friendsCollectiblesDAO.deleteItem(item); // remove user from database
//        mItems.remove(holder.getAdapterPosition()); // remove user from list of users
//        notifyItemRemoved(holder.getAdapterPosition()); // update RecyclerView
//      }
//    });
  }

  // Returns the total count of items in the list
  @Override
  public int getItemCount() {
    return mItems.size();
  }

}

