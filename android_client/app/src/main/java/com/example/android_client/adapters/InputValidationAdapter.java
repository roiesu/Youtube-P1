package com.example.android_client.adapters;

import static android.text.InputType.TYPE_CLASS_TEXT;
import static android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD;
import static android.text.InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.TooltipCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android_client.R;
import com.example.android_client.entities.InputValidation;

import java.util.ArrayList;

public class InputValidationAdapter extends RecyclerView.Adapter<InputValidationAdapter.InputHolder> {

    private Context context;
    private ArrayList<InputValidation> inputs;
    private boolean showTooltips;

    public InputValidationAdapter(Context context, ArrayList<InputValidation> inputs, boolean showTooltips) {
        this.context = context;
        this.inputs = inputs;
        this.showTooltips = showTooltips;
    }

    @NonNull
    @Override
    public InputHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.input_validation, parent, false);
        return new InputHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InputHolder holder, int position) {
        InputValidation inputValidation = inputs.get(position);
        inputValidation.setInput(holder.input);
        holder.label.setText(inputValidation.getName());
        holder.input.setHint(inputValidation.getName());
        if (inputValidation.getName().matches(".*(p|P)assword.*")) {
            holder.input.setInputType(TYPE_CLASS_TEXT|TYPE_TEXT_VARIATION_PASSWORD
            );
        }
        if (showTooltips) {
            TooltipCompat.setTooltipText(holder.details, inputValidation.getReqs());
        } else {
            holder.details.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return inputs.size();
    }

    public class InputHolder extends RecyclerView.ViewHolder {
        private EditText input;
        private TextView label;
        private ImageView details;

        public InputHolder(@NonNull View itemView) {
            super(itemView);
            this.details = itemView.findViewById(R.id.inputDetails);
            this.label = itemView.findViewById(R.id.inputLabel);
            this.input = itemView.findViewById(R.id.editInput);
        }
    }
}
