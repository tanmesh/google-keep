package org.keep.entity;

import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;

public class User {
    @Id
    private String emailId;
    private String password;
    private String name;
    private List<Note> noteIds = new ArrayList<>();

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNoteIds() {
        return noteIds;
    }

    public void setNoteIds(List<Note> noteIds) {
        this.noteIds = noteIds;
    }
}
