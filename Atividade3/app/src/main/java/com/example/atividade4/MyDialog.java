package com.example.atividade4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private EditText editText;
    private OnSaveListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.item);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(layout);

        editText = layout.findViewById(R.id.itemEditText);

        editText.setText(getArguments().getString("text"));

        builder.setPositiveButton(R.string.ok, this);
        builder.setNegativeButton(R.string.cancel, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE) {

            String item = editText.getText().toString();
            listener.onSave(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof OnSaveListener)) {
            throw new RuntimeException("A activity deve implementar DialogFragment.OnAddListener");
        }

        this.listener = (OnSaveListener) context;
    }

    public interface OnSaveListener {
        public void onSave(String item);
    }
}