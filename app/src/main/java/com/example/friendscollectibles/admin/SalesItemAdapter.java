package com.example.friendscollectibles.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.friendscollectibles.R;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.Item;
import com.example.friendscollectibles.user.User;

import java.text.DecimalFormat;
import java.util.List;

public class SalesItemAdapter extends RecyclerView.Adapter<SalesItemAdapter.MyViewHolder> {

  public Item item;
  public Context context;
  public List<Integer> usersThatPurchased;

  public static class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView username;
    public TextView userItemQty;
    public TextView userItemTotal;

    public MyViewHolder(View itemView) {
      super(itemView);

      username = itemView.findViewById(R.id.username);
      userItemQty = itemView.findViewById(R.id.userItemQty);
      userItemTotal = itemView.findViewById(R.id.userItemTotal);
    }
  }

  public SalesItemAdapter(Item item, List<Integer> usersThatPurchased, Context context) {
    this.item = item;
    this.usersThatPurchased = usersThatPurchased;
    this.context = context;
  }

  @Override
  public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_history_item, parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MyViewHolder holder, int position) {

    AppDatabase appDatabase = AppDatabase.getAppDatabase(context.getApplicationContext());
    FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();

    User user = friendsCollectiblesDAO.getUser(usersThatPurchased.get(position));
    int itemQty = 0;

    for (int i = 0; i < user.getItems().size(); i++) {
      if (user.getItems().get(i) == item.getItemID()) {
        itemQty = user.getItemQty().get(i);
        break;
      }
    }

    holder.username.setText(user.getUsername());
    String theItemQty = "Qty: " + itemQty;
    holder.userItemQty.setText(theItemQty);
    DecimalFormat df = new DecimalFormat("#.##");
    String theUserItemTotal = "Total: $" + df.format(item.getPrice() * itemQty);
    holder.userItemTotal.setText(theUserItemTotal);

  }

  @Override
  public int getItemCount() {
    return usersThatPurchased.size();
  }
}