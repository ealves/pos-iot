package com.example.trabalhofinal.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.data.Music;
import com.example.trabalhofinal.data.MusicDAO;

public class EditMusicActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtNome;
    private EditText edtValor;
    private Button btnProcessar;
    private Button btnCancelar;
    private Music Music;
    private MusicDAO MusicDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_editar_);

        edtNome = findViewById(R.id.edt_nome);
        edtValor = findViewById(R.id.edt_valor);
        btnProcessar = findViewById(R.id.btnProcessar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnProcessar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        MusicDAO = MusicDAO.getInstance(this);

        Music = (Music) getIntent().getSerializableExtra("Music");

        if (Music != null){
            edtNome.setText(Music.getNome());
            edtValor.setText(String.valueOf(Music.getValor()));
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnProcessar){
            String nome = edtNome.getText().toString();
            double valor = Double.parseDouble(edtValor.getText().toString());
            String msg;

            if (Music == null) {
                Music Music = new Music(nome, valor);
                MusicDAO.save(Music);
                msg = "Music gravado com ID = " + Music.getId();

            } else {
                Music.setNome(nome);
                Music.setValor(valor);
                MusicDAO.update(Music);
                msg = "Music atualizado com ID = " + Music.getId();
            }

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        }
        else if (view.getId() == R.id.btnCancelar){
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}