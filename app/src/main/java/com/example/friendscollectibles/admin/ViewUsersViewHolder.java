package com.example.friendscollectibles.admin;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.friendscollectibles.R;

public class ViewUsersViewHolder extends RecyclerView.ViewHolder {

    public ImageView isAdmin;
    public TextView username;
    public CardView cardView;
    public Button deleteUser;

    public ViewUsersViewHolder(@NonNull View itemView) {

        super(itemView);

        isAdmin = (ImageView) itemView.findViewById(R.id.isAdmin);
        username = (TextView) itemView.findViewById(R.id.username);
        cardView = itemView.findViewById(R.id.user_container);
        deleteUser = (Button) itemView.findViewById(R.id.deleteUser);
    }
}