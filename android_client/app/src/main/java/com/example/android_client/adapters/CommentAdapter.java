package com.example.android_client.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.entities.Comment;
import com.example.android_client.entities.DataManager;
import com.example.android_client.entities.User;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private ArrayList<Comment> comments;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.comment_layout, parent, false);
        return new CommentViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        holder.commentText.setText(comment.getText());
        holder.commentUser.setText(comment.getUser());
        holder.commentDate.setText(Utilities.dateDiff(comment.getDate_time()) + (comment.isEdited() ? "( edited )" : ""));
        User currentUser = DataManager.getCurrentUser();
//        if (currentUser == null || currentUser.getUsername() != comment.getUser()) {
////             FIX
//            holder.commentOptionOpener.setVisibility(View.GONE);
//            return;
//        }
        PopupMenu popupMenu = createOptionsMenu(holder.commentOptionOpener, position);
        holder.commentOptionOpener.setOnClickListener(view -> {
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentUser;
        TextView commentDate;
        TextView commentText;
        View commentOptionOpener;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUser = itemView.findViewById(R.id.commentUser);
            commentDate = itemView.findViewById(R.id.commentDate);
            commentText = itemView.findViewById(R.id.commentText);
            commentOptionOpener = itemView.findViewById(R.id.commentOptionsOpener);
        }
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
            comments.get(commentPlace).edit(input.getText().toString());
            this.notifyItemChanged(commentPlace);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        return builder.create();
    }
}

