package com.example.trabalhofinal.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.trabalhofinal.R;
import com.example.trabalhofinal.adapter.MusicAdapter;
import com.example.trabalhofinal.data.Music;
import com.example.trabalhofinal.data.MusicDAO;
import com.example.trabalhofinal.databinding.ActivityMainBinding;
import com.example.trabalhofinal.dialog.DeleteDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, DeleteDialog.OnDeleteListener {

    private ListView list;
    private MusicAdapter adapter;
    private MusicDAO musicDAO;
    private static final int REQ_EDIT = 100;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = findViewById(R.id.list);

        adapter = new MusicAdapter(this);

        list.setAdapter(adapter);

        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);

        musicDAO = MusicDAO.getInstance(this);

        //setSupportActionBar(binding.toolbar);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditMusicActivity.class);
                startActivityForResult(intent, REQ_EDIT);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), EditarMusicActivity.class);
            startActivityForResult(intent, REQ_EDIT);
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK) {
            updateList();
        }
    }

    private void updateList() {
        List<Music> musics = musicDAO.list();
        adapter.setItems(musics);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getApplicationContext(), EditMusicActivity.class);
        intent.putExtra("music", adapter.getItem(i));
        startActivityForResult(intent, REQ_EDIT);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Music music = adapter.getItem(i);

        DeleteDialog dialog = new DeleteDialog();
        dialog.setMusic(music);
        dialog.show(getSupportFragmentManager(), "deleteDialog");
        return true;
    }

    @Override
    public void onDelete(Music music) {
        musicDAO.delete(music);
        updateList();
    }
}