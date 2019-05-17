package com.lab.myapplication;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    private Context mContext;
    private List<Note> notesList = new ArrayList<>();

    public NoteAdapter(@NonNull Context context, @LayoutRes ArrayList<Note> list) {
        super(context, 0 , list);
        mContext = context;
        notesList = list;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Note currentNote = notesList.get(position);
        TextView id = (TextView) listItem.findViewById(R.id.idItem) ;
        id.setText(""+currentNote.getId());
        TextView note = (TextView)listItem.findViewById(R.id.itemNote);
        note.setText(currentNote.getNote());
        TextView create = (TextView) listItem.findViewById(R.id.createNote);
        create.setText("Создано: " + currentNote.getCreate());
        TextView update = (TextView) listItem.findViewById(R.id.updateNote);
        if (currentNote.getUpdate() != null)
            update.setText("Обновлено: " + currentNote.getUpdate());
        else
            update.setText("");

        return listItem;
    }
}