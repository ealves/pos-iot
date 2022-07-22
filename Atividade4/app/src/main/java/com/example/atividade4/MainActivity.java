package com.example.atividade4;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ActionMode;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, MyDialog.OnAddListener {

    private ListViewAdapter listViewAdapter;
    private ListView listView;
    ActionMode mActionModeCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listViewAdapter = new ListViewAdapter(this);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(this);
        //listView.setOnItemLongClickListener(this);
        //listView.setMultiChoiceModeListener(mActionModeCallback);
        //listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);

        listViewAdapter.addItem("Teste 1");
        listViewAdapter.addItem("Teste 2");
    }

    ActionMode.Callback ContextualActionModeCallback = new ActionMode.Callback() {
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mActionModeCallback == null) {

            mActionModeCallback = startActionMode(ContextualActionModeCallback);
        }

        view = listView.getChildAt(position);

        view.setBackgroundColor(Color.GRAY);

        Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();
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
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.action_delete){
            /*for(Integer index: selected){
                listViewAdapter.removeItem(index);
            }
            mode.finish();
            return true;
        }

        if (item.getItemId() == R.id.action_edit) {
            MyDialog dialog = new MyDialog();
            dialog.show(getSupportFragmentManager(), "dialog");
            return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        View view = listView.getChildAt(position);

        if(checked){
            view.setBackgroundColor(Color.GRAY);
        }
        else{
            view.setBackgroundColor(Color.TRANSPARENT);
        }
    }*/

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
}