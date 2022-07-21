package com.example.atividade4;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener, MyDialog.OnAddListener{

    private List<String> selected = new ArrayList<>();
    private ListViewAdapter listViewAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listViewAdapter = new ListViewAdapter(this);

        listView.setAdapter(listViewAdapter);

        listView.setMultiChoiceModeListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        listViewAdapter.addItem("Teste 1");
        listViewAdapter.addItem("Teste 2");
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK) {
            updateList();
        }
    }

    private void updateList() {
        List<String> items = listViewAdapter.getItems();
        listViewAdapter.setItems(items);
    }*/

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
        /*if (menuItem.getItemId() == R.id.action_delete){
            for(String s: selected){
                arrayAdapter.remove(s);
            }
            actionMode.finish();
            return true;
        }*/
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
        /*String s = arrayAdapter.getItem(i);
        View view = listView.getChildAt(i);

        if(b){
            view.setBackgroundColor(Color.BLUE);
            selected.add(s);
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT);
            selected.remove(s);
        }*/
    }

    @Override
    public void onAdd(String item) {
        listViewAdapter.addItem(item);
    }
}