package org.keep.entity;

import org.bson.types.ObjectId;
import org.keep.enums.NoteType;
import org.mongodb.morphia.annotations.Id;

public class Note {
    @Id
    private ObjectId noteId;
    private String title;
    private String noteContent; // Json parsed string
    private String emailId;

    public ObjectId getNoteId() {
        return noteId;
    }

    public void setNoteId(ObjectId noteId) {
        this.noteId = noteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
