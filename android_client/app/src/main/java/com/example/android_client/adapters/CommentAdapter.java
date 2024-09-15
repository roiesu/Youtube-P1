
package com.example.android_client.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.example.android_client.R;
import com.example.android_client.Utilities;
import com.example.android_client.activities.ChannelActivity;
import com.example.android_client.datatypes.CommentWithUser;
import com.example.android_client.DataManager;
import com.example.android_client.view_models.CommentViewModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<CommentWithUser> comments;
    private CommentViewModel commentViewModel;
    private LifecycleOwner owner;
    private String videoUploader;
    private MutableLiveData<Integer> commentListSize;

    public CommentAdapter(Context context, List<CommentWithUser> comments, LifecycleOwner owner, String videoUploader, MutableLiveData<Integer> commentListSize) {
        this.context = context;
        this.comments = comments;
        this.owner = owner;
        this.videoUploader = videoUploader;
        this.commentListSize = commentListSize;
        commentViewModel = new CommentViewModel(owner);
    }

    public void setComments(List<CommentWithUser> comments) {
        this.comments = comments;
        commentListSize.setValue(comments.size());
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
        holder.commentText.post(() -> {
            if (holder.commentText.getLineCount() > 3) {
                holder.readMore.setVisibility(View.VISIBLE);
            } else {
                holder.readMore.setVisibility(View.GONE);
            }
        });
        holder.commentUser.setText(comment.getUser().getUsername());
        holder.commentDate.setText(Utilities.dateDiff(comment.getDate()) + (comment.isEdited() ? "( edited )" : ""));
        String currentUsername = DataManager.getCurrentUsername();
        if (currentUsername == null || !currentUsername.equals(comment.getUser().getUsername())) {
            holder.commentOptionOpener.setVisibility(View.GONE);
        } else {
            PopupMenu popupMenu = createOptionsMenu(holder.commentOptionOpener, comment);
            holder.commentOptionOpener.setOnClickListener(view -> {
                popupMenu.show();
            });
        }
        Glide.with(context).load(comment.getUser().getImageFromServer()).signature(new ObjectKey(System.currentTimeMillis())).into(holder.profilePic);
        holder.profilePic.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChannelActivity.class);
            intent.putExtra("USER_ID", comment.getUserId());
            context.startActivity(intent);
        });
        holder.readMore.setOnClickListener(l -> {
            if (holder.expanded) {
                holder.commentText.setMaxLines(3);
                holder.readMore.setText("read more");
            } else {
                holder.commentText.setMaxLines(200);
                holder.readMore.setText("close");
            }
            holder.expanded = !holder.expanded;
        });

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void deleteComment(CommentWithUser comment) {
        MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
        int position = this.comments.indexOf(comment);
        finished.observe(owner, value -> {
            if (value) {
                this.comments.remove(position);
                this.notifyItemRemoved(position);
                this.commentListSize.setValue(comments.size());
                commentViewModel.getComment().removeObservers(owner);
                commentViewModel.getComment().setValue(null);
            }
        });
        commentViewModel.deleteComment(comments.get(position), videoUploader, finished);
    }

    public void editComment(CommentWithUser comment, String text) {
        MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
        int position = this.comments.indexOf(comment);
        finished.observe(owner, value -> {
            if (value) {
                this.comments.get(position).setEdited(true);
                this.comments.get(position).setText(text);
                this.notifyItemChanged(position);
                commentViewModel.getComment().removeObservers(owner);
                commentViewModel.getComment().setValue(null);
            }
        });
        commentViewModel.editComment(comments.get(position), text, videoUploader, finished);

    }

    public void addComment(EditText textInput, String videoId, RecyclerView commentList) {
        MutableLiveData<Boolean> finished = new MutableLiveData<>(false);
        finished.observe(owner, value -> {
            if (value) {
                this.comments.add(0, (CommentWithUser) commentViewModel.getComment().getValue());
                this.notifyItemInserted(0);
                commentListSize.setValue(commentListSize.getValue() + 1);
                commentList.getLayoutManager().scrollToPosition(0);
                commentViewModel.getComment().removeObservers(owner);
                commentViewModel.getComment().setValue(null);
            }
        });
        commentViewModel.addComment(textInput.getText().toString(), videoUploader, videoId, finished);
        textInput.setText("");
    }

    public PopupMenu createOptionsMenu(View view, CommentWithUser comment) {
        PopupMenu popupMenu = new PopupMenu(this.context, view);
        popupMenu.getMenuInflater().inflate(R.menu.comment_options, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            AlertDialog dialog = createInputDialog(view.getContext(), comment);
            if (menuItem.getItemId() == R.id.deleteCommentOption) {
                deleteComment(comment);
            } else if (menuItem.getItemId() == R.id.editCommentOption) {
                dialog.show();
            }
            return true;
        });
        return popupMenu;
    }

    public AlertDialog createInputDialog(Context context, CommentWithUser comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit Comment");
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Edit", (dialog, which) -> {
            editComment(comment, input.getText().toString());
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        return builder.create();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView commentUser;
        TextView commentDate;
        TextView commentText;
        TextView readMore;
        View commentOptionOpener;
        ImageView profilePic;
        boolean expanded;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.uploaderImage);
            commentUser = itemView.findViewById(R.id.commentUser);
            commentDate = itemView.findViewById(R.id.commentDate);
            commentText = itemView.findViewById(R.id.commentText);
            commentOptionOpener = itemView.findViewById(R.id.commentOptionsOpener);
            readMore = itemView.findViewById(R.id.read_more);
            expanded = false;
        }
    }
}


