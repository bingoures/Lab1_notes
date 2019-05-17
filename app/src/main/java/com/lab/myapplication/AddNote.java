package com.lab.myapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class AddNote extends AppCompatActivity {

    EditText editText;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editText = (EditText) findViewById(R.id.editText);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_item, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.insert:
                if (editText.getText().length() > 0){
                    Note note = new Note();
                    note.setNote(editText.getText().toString());
                    note.setCreate(DateFormater.getDateNow());
                    if(MainActivity.getDbHelper() != null){
                        MainActivity.getDbHelper().insertNewItem(note);
                        Toast.makeText(
                                AddNote.this, "Заметка добавлена",
                                Toast.LENGTH_SHORT
                        ).show();
                        setResult(RESULT_CANCELED);
                        finish();
                    } else {
                        Toast.makeText(
                                AddNote.this, "Ошибка добавления",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                } else {
                    Toast.makeText(
                            AddNote.this, "Нет текста заметки",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
