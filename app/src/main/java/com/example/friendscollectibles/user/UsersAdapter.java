package com.example.friendscollectibles.user;

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

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

  public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView username;
    public Button deleteUser;
    ImageView isAdmin;

    // We also create a constructor that accepts the entire item row
    // and does the view lookups to find each subview
    public ViewHolder(View itemView) {
      // Stores the itemView in a public final member variable that can be used
      // to access the context from any ViewHolder instance.
      super(itemView);

      username = (TextView) itemView.findViewById(R.id.username);
      deleteUser = (Button) itemView.findViewById(R.id.deleteUser);
      isAdmin = (ImageView) itemView.findViewById(R.id.isAdmin);

    }
  }

  private List<User> mUsers;
  private Context context;

  // Pass in the contact array into the constructor
  public UsersAdapter(List<User> users, Context context) {
    mUsers = users;
    this.context = context;
  }

  @Override
  public UsersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate the custom layout
    View usersView = inflater.inflate(R.layout.users_layout, parent, false);

    // Return a new holder instance
    ViewHolder viewHolder = new ViewHolder(usersView);
    return viewHolder;
  }

  // Involves populating data into the item through holder
  @Override
  public void onBindViewHolder(UsersAdapter.ViewHolder holder, int position) {
    // Get the data model based on position
    User user = mUsers.get(position);
    ImageView isAdmin = holder.isAdmin;

    if (user.getAdmin()) {
      isAdmin.setImageResource(R.drawable.ic_baseline_admin_panel_settings_24);
    } else {
      isAdmin.setImageResource(R.drawable.ic_baseline_person_24);
    }
    // Set item views based on your views and data model
    TextView textView = holder.username;
    textView.setText(user.getUsername());
    Button button = holder.deleteUser;
    button.setText("Delete User");
    button.setEnabled(Boolean.TRUE);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        FriendsCollectiblesDAO friendsCollectiblesDAO = appDatabase.friendsCollectiblesDAO();
        friendsCollectiblesDAO.deleteUser(user); // remove user from database
        mUsers.remove(holder.getAdapterPosition()); // remove user from list of users
        notifyItemRemoved(holder.getAdapterPosition()); // update RecyclerView
      }
    });
  }

  // Returns the total count of items in the list
  @Override
  public int getItemCount() {
    return mUsers.size();
  }

}
