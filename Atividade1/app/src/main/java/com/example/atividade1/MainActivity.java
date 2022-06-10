package com.example.atividade1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private int actualFontSize = 0;
    private String actualText = "";
    private int actualColor = R.color.black;

    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUpperCase = false;

    private TextView textView;
    private EditText editText;
    private TextView textView5;
    private Button btnSendText;
    private SeekBar seekBar;

    private CheckBox checkBox;
    private CheckBox checkBox2;
    private CheckBox checkBox3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        btnSendText = findViewById(R.id.imageButton);
        textView = findViewById(R.id.textView);
        textView5 = findViewById(R.id.textView5);
        seekBar = findViewById(R.id.seekBar);

        checkBox = findViewById(R.id.checkBox);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);

        btnSendText.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imageButton) {

            actualText = editText.getText().toString();
            textView.setText(editText.getText().toString());

        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.seekBar) {
            actualFontSize = progress;
            textView.setTextSize(actualFontSize);

            textView5.setText(Integer.toString(actualFontSize) + "sp");
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

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox:
                isBold = checked;
                break;
            case R.id.checkBox2:
                isItalic = checked;
                break;
            case R.id.checkBox3:
                if (checked) {
                    actualText = actualText.toUpperCase();
                } else {
                    actualText = actualText.toLowerCase();
                }

                textView.setText(actualText);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
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
}