package com.example.atividade4;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyDialog.OnAddListener {

    private List<Integer> selected = new ArrayList<Integer>();
    private ListViewAdapter listViewAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listViewAdapter = new ListViewAdapter(this);

        listView.setAdapter(listViewAdapter);

        //listView.setOnItemClickListener(this);
        //listView.setOnItemLongClickListener(this);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);

        listViewAdapter.addItem("Teste 1");
        listViewAdapter.addItem("Teste 2");
    }

    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.actions_delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add) {
            MyDialog dialog = new MyDialog();
            dialog.show(getSupportFragmentManager(), "dialog");
            return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
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
            for(Integer index: selected){
                listViewAdapter.removeItem(index);
            }
            actionMode.finish();
            return true;
        }

        if (menuItem.getItemId() == R.id.action_edit) {
            MyDialog dialog = new MyDialog();
            dialog.show(getSupportFragmentManager(), "dialog");
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
    }*/

    @Override
    public void onAdd(String item) {
        listViewAdapter.addItem(item);
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        view = listView.getChildAt(position);

        view.setBackgroundColor(Color.GRAY);

        Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();
    }

    /*@Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        view = listView.getChildAt(position);

        int index = selected.indexOf(position);

        if (index == -1) {

            selected.add(position);
            view.setBackgroundColor(Color.BLUE);
        } else {
            view.setBackgroundColor(Color.TRANSPARENT);
            selected.remove(position);
        }

        Toast.makeText(this, position, Toast.LENGTH_SHORT).show();
        /*View view = listView.getChildAt(i);

        if(b){
            view.setBackgroundColor(Color.BLUE);
            selected.add(i);
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT);
            selected.remove(i);
        }*/

        //return false;
    //}

    /*@Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

    }*/
}