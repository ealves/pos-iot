package com.example.atividade4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment implements DialogInterface.OnClickListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.app_name)
                .setMessage(R.string.sair)
                .setPositiveButton(R.string.sim, this)
                .setNegativeButton(R.string.nao, this);
        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if (i == DialogInterface.BUTTON_POSITIVE){
            Toast.makeText(getActivity(), R.string.thanks, Toast.LENGTH_SHORT).show();
            //listener.onExit();
        }
        else if (i == DialogInterface.BUTTON_NEGATIVE){
            Toast.makeText(getActivity(), R.string.thanks, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}