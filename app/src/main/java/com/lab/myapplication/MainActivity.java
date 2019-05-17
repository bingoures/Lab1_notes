package com.lab.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText search;
    private ListView listView;
    private NoteAdapter mAdapter;
    private static DbHelper dbHelper;

    private static  final int REQUEST_ACCESS_TYPE=1;

    public void updateNote(View view){
        View parent = (View) view.getParent();
        TextView itemTextView = (TextView) findViewById(R.id.itemNote);
        String item = String.valueOf(itemTextView.getText());
        Toast.makeText(
                MainActivity.this, item,
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);
        listView = (ListView) findViewById(R.id.movies_list);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(R.id.idItem);
                showUpdateNote(tv.getText().toString());
            }
        });

        showItemList();
        eventsCreate();
    }

    public void showUpdateNote(String id){
        Intent intent = new Intent(this, update_note.class);
        intent.putExtra("id", id);

        startActivityForResult(intent, REQUEST_ACCESS_TYPE);
    }

    public static DbHelper getDbHelper() {
        return dbHelper;
    }

    public void eventsCreate(){
        search = (EditText) findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchItemList(search.getText().toString());
            }
        });
    }

    private void showItemList() {
        ArrayList<Note> moviesList = dbHelper.getNotes();
        mAdapter = new NoteAdapter(this, moviesList);
        listView.setAdapter(mAdapter);
    }

    private void searchItemList(String value) {
        ArrayList<Note> moviesList = dbHelper.searchNotes(value);
        mAdapter = new NoteAdapter(this, moviesList);
        listView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.addTask:

                Intent intent = new Intent(this, AddNote.class);
                startActivityForResult(intent, REQUEST_ACCESS_TYPE);
                /*
                final EditText itemEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add new item")
                        .setMessage("What`s next?")
                        .setView(itemEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String item = String.valueOf(itemEditText.getText());
                                dbHelper.insertNewItem(item);
                                showItemList();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                 */
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        showItemList();
    }
}
