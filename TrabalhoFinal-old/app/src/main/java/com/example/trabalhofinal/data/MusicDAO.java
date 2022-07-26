package com.example.trabalhofinal.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MusicDAO {
    private static MusicDAO instance;

    private SQLiteDatabase db;

    private MusicDAO(Context context) {
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    //singleton
    public static MusicDAO getInstance(Context context) {
        if (instance == null) {
            instance = new MusicDAO(context.getApplicationContext());
        }
        return instance;
    }

    public List<Music> list() {

        String[] columns = {
                MusicsContract.Columns._ID,
                MusicsContract.Columns.NOME,
                MusicsContract.Columns.VALOR
        };

        List<Music> Musics = new ArrayList<>();

        try (Cursor c = db.query(MusicsContract.TABLE_NAME, columns, null, null, null, null, MusicsContract.Columns.NOME)) {
            if (c.moveToFirst()) {
                do {
                    Music p = MusicDAO.fromCursor(c);
                    Musics.add(p);
                } while (c.moveToNext());
            }

            return Musics;
        }

    }

    private static Music fromCursor(Cursor c) {
        @SuppressLint("Range") int id = c.getInt(c.getColumnIndex(MusicsContract.Columns._ID));
        @SuppressLint("Range") String nome = c.getString(c.getColumnIndex(MusicsContract.Columns.NOME));
        @SuppressLint("Range") double valor = c.getDouble(c.getColumnIndex(MusicsContract.Columns.VALOR));
        return new Music(id, nome, valor);
    }

    public void save(Music Music) {
        ContentValues values = new ContentValues();
        values.put(MusicsContract.Columns.NOME, Music.getNome());
        values.put(MusicsContract.Columns.VALOR, Music.getValor());
        long id = db.insert(MusicsContract.TABLE_NAME, null, values);
        Music.setId((int) id);
    }

    public void update(Music Music) {
        ContentValues values = new ContentValues();
        values.put(MusicsContract.Columns.NOME, Music.getNome());
        values.put(MusicsContract.Columns.VALOR, Music.getValor());
        db.update(MusicsContract.TABLE_NAME, values, MusicsContract.Columns._ID + " = ?", new String[]{ String.valueOf(Music.getId()) });
    }

    public void delete(Music Music) {
        db.delete(MusicsContract.TABLE_NAME, MusicsContract.Columns._ID + " = ?", new String[]{ String.valueOf(Music.getId()) });
    }
}