package com.example.trabalhofinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.data.Music;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MusicAdapter extends BaseAdapter {
    private Context context;
    private static final NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("PT","BR"));
    private List<Music> musics = new ArrayList<>();

    public MusicAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Music getItem(int i) {
        return musics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return musics.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_list_musics, viewGroup, false);

        TextView txtNome = v.findViewById(R.id.txt_nome);
        TextView txtValor = v.findViewById(R.id.txt_valor);

        Music music = musics.get(i);
        txtNome.setText(music.getNome());
        txtValor.setText(nf.format(music.getValor()));

        return v;
    }

    public void setItems(List<Music> musics) {
        this.musics = musics;
        notifyDataSetChanged();
    }
}