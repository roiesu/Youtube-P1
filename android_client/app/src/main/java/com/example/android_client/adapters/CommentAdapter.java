package com.example.android_client.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.WatchingVideo;
import com.example.android_client.datatypes.CommentWithUser;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<CommentWithUser> comments;

    public CommentAdapter(Context context, List<CommentWithUser> comments) {
        this.context = context;
        this.comments = comments;
    }
    public CommentAdapter(Context context){
        this.context = context;
        this.comments = new ArrayList<>();
    }
    public void setComments(List<CommentWithUser> comments){
        this.comments=comments;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentWithUser comment = comments.get(position);
        holder.commentText.setText(comment.getText());
        holder.commentUser.setText(comment.getUser().getUsername());
        holder.commentDate.setText(Utilities.dateDiff(comment.getDate()) + (comment.isEdited() ? "( edited )" : ""));
        String currentUsername = DataManager.getCurrentUsername();
        if (currentUsername == null || !currentUsername.equals(comment.getUser().getUsername())) {
            holder.commentOptionOpener.setVisibility(View.GONE);
        }
        else {
            PopupMenu popupMenu = createOptionsMenu(holder.commentOptionOpener, position);
            holder.commentOptionOpener.setOnClickListener(view -> {
                popupMenu.show();
            });
        }
        Glide.with(context).load(comment.getUser().getImageFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(holder.profilePic);

        holder.profilePic.setOnClickListener(l -> {
            // Move to channel page
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public PopupMenu createOptionsMenu(View view, int commentPlace) {
        PopupMenu popupMenu = new PopupMenu(this.context, view);
        popupMenu.getMenuInflater().inflate(R.menu.comment_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            AlertDialog dialog = createInputDialog(view.getContext(), commentPlace);
            if (menuItem.getItemId() == R.id.deleteCommentOption) {
                comments.remove(commentPlace);
                notifyItemRemoved(commentPlace);
            } else if (menuItem.getItemId() == R.id.editCommentOption) {
                dialog.show();
            }
            return true;
        });
        return popupMenu;
    }

    public AlertDialog createInputDialog(Context context, int commentPlace) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Comment");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Edit", (dialog, which) -> {
//            comments.get(commentPlace).edit(input.getText().toString());
            this.notifyItemChanged(commentPlace);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        return builder.create();
    }
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentUser;
        TextView commentDate;
        TextView commentText;
        View commentOptionOpener;
        ImageView profilePic;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.uploaderImage);
            commentUser = itemView.findViewById(R.id.commentUser);
            commentDate = itemView.findViewById(R.id.commentDate);
            commentText = itemView.findViewById(R.id.commentText);
            commentOptionOpener = itemView.findViewById(R.id.commentOptionsOpener);
        }
    }
}


