package com.example.friendscollectibles.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendscollectibles.R;
import com.example.friendscollectibles.admin.UsersSelectListener;
import com.example.friendscollectibles.admin.ViewUsersViewHolder;
import com.example.friendscollectibles.db.AppDatabase;
import com.example.friendscollectibles.db.FriendsCollectiblesDAO;
import com.example.friendscollectibles.item.CustomViewHolder;
import com.example.friendscollectibles.item.SelectListener;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<ViewUsersViewHolder> {

  public TextView username;
  public Button deleteUser;
  ImageView isAdmin;
  private List<User> mUsers;
  private Context context;
  private UsersSelectListener listener;

  public UsersAdapter(List<User> users, Context context, UsersSelectListener listener) {
    mUsers = users;
    this.context = context;
    this.listener = listener;
  }

  @NonNull
  @Override
  public ViewUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    View usersView = inflater.inflate(R.layout.users_layout, parent, false);

    ViewUsersViewHolder viewHolder = new ViewUsersViewHolder(usersView);
    return viewHolder;
  }

  // Involves populating data into the item through holder
  @Override
  public void onBindViewHolder(@NonNull ViewUsersViewHolder holder, int position) {

    User user = mUsers.get(position);
    ImageView isAdmin = holder.isAdmin;

    if (user.getAdmin()) {
      isAdmin.setImageResource(R.drawable.ic_baseline_admin_panel_settings_24);
    } else {
      isAdmin.setImageResource(R.drawable.ic_baseline_person_24);
    }

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

    holder.cardView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        listener.onUserClicked(mUsers.get(holder.getAdapterPosition()));
      }
    });
  }

  @Override
  public int getItemCount() {
    return mUsers.size();
  }

}
