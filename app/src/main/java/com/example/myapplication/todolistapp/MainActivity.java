package com.example.myapplication.todolistapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myapplication.todolistapp.adapter.TaskAdapter;
import com.example.myapplication.todolistapp.database.DatabaseHandler;
import com.example.myapplication.todolistapp.model.Task;

import java.util.List;

import static android.R.attr.colorBackgroundFloating;

public class MainActivity extends AppCompatActivity {
    Context context;
    SwipeMenuListView listView;
    SearchView searchView;
    List<Task>tasklist;
   public TaskAdapter madapter;
    DatabaseHandler db = new DatabaseHandler(this);
    String task,date,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.search_tab);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               searchByName(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               searchByName(newText);
              updateUI();
                return false;
            }
        });
        listView = (SwipeMenuListView) findViewById(R.id.listView);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "done" item
                SwipeMenuItem doneItem = new SwipeMenuItem(
                        getApplicationContext());
                doneItem.setWidth(150);
                doneItem.setIcon(R.drawable.ic_done);
                menu.addMenuItem(doneItem);

                // create "canle" item
                SwipeMenuItem cancleItem = new SwipeMenuItem(
                        getApplicationContext());
                cancleItem.setWidth(150);
                cancleItem.setIcon(R.drawable.ic_cancled);
                menu.addMenuItem(cancleItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setWidth(150);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        db.doneTask(tasklist.get(position));
                        updateUI();
                        break;
                    case 1:
                        db.cancleTask(tasklist.get(position));
                        updateUI();
                        break;
                    case 2:
                        db.deleteTask(tasklist.get(position));
                        updateUI();
                        break;
                }
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showChangeLangDialog(position);
            }
        });

        updateUI();
        addTask();
    }

    private void showChangeLangDialog(final int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter text below");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                db.updateTask(tasklist.get(position),edt.getText().toString());
                updateUI();

            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }



    private void searchByName(String newText) {
        tasklist= db.search(newText);
        madapter=new TaskAdapter(this,tasklist);
        listView.setAdapter(madapter);
    }




    private void addTask() {
        if( getIntent().getExtras() != null)
        {
            Intent intent=getIntent();
            task=intent.getStringExtra("taskname");
            date=intent.getStringExtra("date");
            time=intent.getStringExtra("time");
            db.addTask(new Task(task,date,time,""));
            updateUI();
        }
    }




    private void updateUI() {
        if (madapter==null){
            tasklist=db.getAllTasks();
            madapter=new TaskAdapter(this,tasklist);
            listView.setAdapter(madapter);
        }
        else {
            tasklist.clear();
            tasklist=db.getAllTasks();
            madapter=new TaskAdapter(this,tasklist);
            madapter.notifyDataSetChanged();
            listView.setAdapter(madapter);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.add:
                addNewTask();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewTask() {
        Intent intent=new Intent(MainActivity.this,AddTask.class);
        startActivity(intent);
    }

}
