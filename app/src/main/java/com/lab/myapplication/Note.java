package com.lab.myapplication;

import java.util.Date;

public class Note {

    private String note;
    private String create;
    private String update;

    private int id;

    public Note(){}

    public Note(String note, String create, String update) {
        this.note = note;
        this.create = create;
        this.update = update;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
