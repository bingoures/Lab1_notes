package com.lab.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "MyDB";
    public static final String TABLE_NAME = "Notes";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    private static final int DB_VERSION = 2;



    public DbHelper(Context context){
        super(context, DB_NAME,null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        String query = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s VARCHAR(250) NOT NULL, %s VARCHAR(250));", TABLE_NAME, COLUMN_ID, COLUMN_NOTE, COLUMN_CREATED_AT, COLUMN_UPDATED_AT);
        db.execSQL(query);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String query = String.format("DELETE TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewItem(Note item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE, item.getNote());
        values.put(COLUMN_CREATED_AT, DateFormater.getDateNow());
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public ArrayList<Note> getNotes() {
        ArrayList<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NOTE, COLUMN_CREATED_AT, COLUMN_UPDATED_AT}, null, null,null,null, null);

        while (c.moveToNext()){
            Note note = new Note();
            int index;
            index = c.getColumnIndex(COLUMN_NOTE);
            note.setNote(c.getString(index));
            index = c.getColumnIndex(COLUMN_CREATED_AT);
            note.setCreate(c.getString(index));
            index = c.getColumnIndex(COLUMN_UPDATED_AT);
            note.setUpdate(c.getString(index));
            index = c.getColumnIndex(COLUMN_ID);
            note.setId(c.getInt(index));
            notesList.add(note);
        }
        c.close();
        db.close();
        return notesList;
    }

    public ArrayList<Note> searchNotes(String keyValue) {
        ArrayList<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NOTE, COLUMN_CREATED_AT, COLUMN_UPDATED_AT}, COLUMN_NOTE + " LIKE ?", new String[] {"%" + keyValue + "%"},null,null, null);
        while (c.moveToNext()){
            Note note = new Note();
            int index;
            index = c.getColumnIndex(COLUMN_NOTE);
            note.setNote(c.getString(index));
            index = c.getColumnIndex(COLUMN_CREATED_AT);
            note.setCreate(c.getString(index));
            index = c.getColumnIndex(COLUMN_UPDATED_AT);
            note.setUpdate(c.getString(index));
            index = c.getColumnIndex(COLUMN_ID);
            note.setId(c.getInt(index));
            notesList.add(note);
        }
        c.close();
        db.close();
        return notesList;
    }

    public Note getNoteById(int id){
        Note note = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NOTE, COLUMN_CREATED_AT, COLUMN_UPDATED_AT}, COLUMN_ID + " = ?", new String[] {""+id},null,null, null);
        while (c.moveToNext()){
            note = new Note();
            int index;
            index = c.getColumnIndex(COLUMN_NOTE);
            note.setNote(c.getString(index));
            index = c.getColumnIndex(COLUMN_CREATED_AT);
            note.setCreate(c.getString(index));
            index = c.getColumnIndex(COLUMN_UPDATED_AT);
            note.setUpdate(c.getString(index));
            index = c.getColumnIndex(COLUMN_ID);
            note.setId(c.getInt(index));
            break;
        }

        return note;
    }

    public void deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{""+id});
    }

    public void updateNote(Note note) throws ParseException {
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        cv.put("note", note.getNote());
        cv.put("updated_at", DateFormater.getDateNow());
        db.update(TABLE_NAME, cv, COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
    }
}