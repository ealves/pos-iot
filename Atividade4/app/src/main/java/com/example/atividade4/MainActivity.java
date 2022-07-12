package com.example.atividade4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener, AdapterView.OnItemClickListener {

    private List<String> selected = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //listItems = new ArrayList<>();
        arrayAdapter.add("Teste");
        arrayAdapter.add("Teste 2");

        listView.setAdapter(arrayAdapter);

        listView.setMultiChoiceModeListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            MyDialog dialog = new MyDialog();
            dialog.show(getSupportFragmentManager(), "dialog");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.actions_delete, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_delete){
            for(String s: selected){
                arrayAdapter.remove(s);
            }
            actionMode.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        int count = listView.getChildCount();

        for(int i=0; i<count; i++){
            View view = listView.getChildAt(i);
            view.setBackgroundColor(Color.TRANSPARENT);
        }
        selected.clear();
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
        String s = arrayAdapter.getItem(i);
        View view = listView.getChildAt(i);

        if(b){
            view.setBackgroundColor(Color.BLUE);
            selected.add(s);
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT);
            selected.remove(s);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}