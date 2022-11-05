package com.example.atividade1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private int fontSize = 0;
    private String text = "";

    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUpperCase = false;

    private TextView textView;
    private EditText editText;
    private TextView textView5;
    private Button btnSendText;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnSendText = findViewById(R.id.imageButton);
        textView = findViewById(R.id.textView);
        textView5 = findViewById(R.id.textView5);
        seekBar = findViewById(R.id.seekBar);

        btnSendText.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageButton) {
            text = editText.getText().toString();
            textView.setText(editText.getText().toString());
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.seekBar) {
            fontSize = progress;
            textView.setTextSize(fontSize);
            textView5.setText(Integer.toString(fontSize) + "sp");
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox:
                isBold = checked;
                break;
            case R.id.checkBox2:
                isItalic = checked;
                break;
            case R.id.checkBox3:
                if (checked) {
                    text = text.toUpperCase();
                } else {
                    text = text.toLowerCase();
                }

                textView.setText(text);
                break;
        }

        if (isBold && !isItalic) {
            textView.setTypeface(null, Typeface.BOLD);
        }
        else if (isBold && isItalic) {
            textView.setTypeface(null, Typeface.BOLD_ITALIC);
        }
        else if (!isBold && !isItalic){
            textView.setTypeface(null, Typeface.NORMAL);
        }
        else {
            textView.setTypeface(null, Typeface.ITALIC);
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    textView.setTextColor(Color.RED);
                    break;
            case R.id.radioButton2:
                if (checked)
                    textView.setTextColor(Color.GREEN);
                    break;
            case R.id.radioButton3:
                if (checked)
                    textView.setTextColor(Color.BLUE);
                break;
        }
    }
}