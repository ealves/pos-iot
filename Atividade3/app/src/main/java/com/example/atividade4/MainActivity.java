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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, MyDialog.OnSaveListener {

    private ListViewAdapter listViewAdapter;
    private ListView listView;
    ActionMode mActionModeCallback = null;
    Integer itemSelected = -1;
    Boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listViewAdapter = new ListViewAdapter(this);

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(this);

        //listViewAdapter.addItem("Teste 1");
        //listViewAdapter.addItem("Teste 2");
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
            switch (item.getItemId()) {
                case R.id.action_delete:
                    listViewAdapter.removeItem(itemSelected);
                    mode.finish();
                    return true;
                case R.id.action_edit:
                    isEditing = true;
                    MyDialog dialog = new MyDialog();
                    Bundle args = new Bundle();
                    String itemText = listViewAdapter.getItem(itemSelected);
                    args.putString("text", itemText);
                    dialog.setArguments(args);
                    dialog.show(getSupportFragmentManager(), "dialog");
                    mode.finish();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            View view = listView.getChildAt(itemSelected);
            view.setBackgroundColor(Color.TRANSPARENT);

            if (!isEditing) itemSelected = -1;
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.action_add) {
            MyDialog dialog = new MyDialog();
            Bundle args = new Bundle();
            args.putString("text", "");
            dialog.setArguments(args);
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

    @Override
    public void onSave(String item) {

        if (itemSelected != -1) {
            listViewAdapter.editItem(item, itemSelected);
            itemSelected = -1;
            isEditing = false;
        } else {
            listViewAdapter.addItem(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (itemSelected == -1) {

            itemSelected = position;

            mActionModeCallback = startActionMode(ContextualActionModeCallback);

            view = listView.getChildAt(position);

            view.setBackgroundColor(Color.GRAY);

        } else {
            Toast.makeText(MainActivity.this, "Selecione somente um item", Toast.LENGTH_SHORT).show();
        }
    }
}