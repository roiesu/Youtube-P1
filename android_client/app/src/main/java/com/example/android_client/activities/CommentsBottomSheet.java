package com.example.android_client.activities;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.example.android_client.ContextApplication;
import com.example.android_client.DataManager;
import com.example.android_client.R;
import com.example.android_client.adapters.CommentAdapter;
import com.example.android_client.view_models.CommentListViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CommentsBottomSheet extends BottomSheetDialogFragment {
    private Context context;
    private RecyclerView commentsRecyclerView;
    private String videoUploader;
    private String videoId;
    private MutableLiveData<Integer> commentListSize;
    private CommentListViewModel commentListViewModel;
    private TextView commentsHeader;
    private EditText commentInput;

    public CommentsBottomSheet(Context context, String videoUploader, String videoId) {
        this.context = context;
        this.videoUploader = videoUploader;
        this.videoId = videoId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comments_list, container, false);
        Button commentButton = view.findViewById(R.id.commentButton);
        commentInput = view.findViewById(R.id.commentInput);
        commentsRecyclerView = view.findViewById(R.id.commentsList);


        commentsHeader = view.findViewById(R.id.commentsTitle);
        ImageView goBack = view.findViewById(R.id.backButton);
        goBack.setOnClickListener(l -> dismissAllowingStateLoss());

        commentListSize = new MutableLiveData<>(0);
        commentListViewModel = new CommentListViewModel();
        CommentAdapter adapter = new CommentAdapter(context, new ArrayList<>(), this, videoUploader, commentListSize);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        commentsRecyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(context, R.drawable.divider);
        dividerItemDecoration.setDrawable(dividerDrawable);
        commentsRecyclerView.addItemDecoration(dividerItemDecoration);
        commentListViewModel.getComments().observe(this, commentsList -> {
            if (commentsList == null) {
                return;
            }
            adapter.setComments(commentsList);
            adapter.notifyDataSetChanged();
            commentListSize.setValue(commentsList.size());
            commentButton.setOnClickListener(l -> addComment(adapter));

            commentListViewModel.getComments().setValue(null);
        });
        commentListSize.observe(this, count -> {
            commentsHeader.setText(count + " Comments");
        });
        commentListViewModel.getCommentsByVideo(videoId);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            View bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);

            if (bottomSheet != null) {
                bottomSheet.getLayoutParams().height = 1800;
                bottomSheet.setLayoutParams(bottomSheet.getLayoutParams());
            }
        });

        return dialog;
    }

    private void addComment(CommentAdapter adapter) {
        if (DataManager.getCurrentUsername() == null) {
            ContextApplication.showToast("Can't comment without login");
            return;
        }
        adapter.addComment(commentInput, videoId, commentsRecyclerView);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentInput.getWindowToken(), 0);
    }

    public void show(FragmentManager fragmentManager) {
        this.show(fragmentManager, "CommentsBottomSheet");
    }
}
