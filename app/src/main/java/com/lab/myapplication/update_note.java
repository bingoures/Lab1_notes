package com.lab.myapplication;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Date;

public class update_note extends AppCompatActivity {

    public int id;

    public Note note;

    EditText editText;
    TextView createNote;
    TextView updateNote;
    Button button;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);

        this.id = Integer.valueOf(getIntent().getStringExtra("id"));

        Toast.makeText(
                update_note.this, String.valueOf(this.id),
                Toast.LENGTH_SHORT
        ).show();

        editText = (EditText) findViewById(R.id.editText);
        createNote = (TextView) findViewById(R.id.createNote);
        updateNote = (TextView) findViewById(R.id.updateNote);

        note = MainActivity.getDbHelper().getNoteById(id);

        editText.setText(note.getNote());
        createNote.setText(note.getCreate());
        updateNote.setText( note.getUpdate() != null ? note.getUpdate() : "");

        button = (Button) findViewById(R.id.delete);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MainActivity.getDbHelper().deleteNote(id);
                Toast.makeText(
                        update_note.this, "Заметка удалена",
                        Toast.LENGTH_SHORT
                ).show();
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        eventsCreate();
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void eventsCreate(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setNote(editText.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_update_item, menu);

        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.update:
                if (editText.getText().length() > 0){
                    try {
                        MainActivity.getDbHelper().updateNote(note);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(
                            update_note.this, "Заметка обновлена",
                            Toast.LENGTH_SHORT
                    ).show();
                    setResult(RESULT_CANCELED);
                    finish();
                } else {
                    Toast.makeText(
                            update_note.this, "Нет текста заметки",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
