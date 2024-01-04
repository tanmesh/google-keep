package org.keep.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.keep.enums.NoteType;
import org.keep.wsRequestModel.NoteData;
import org.mongodb.morphia.annotations.Id;

@Getter
@Setter
public class Note {
    @Id
    private String noteId;
    private String title;
    private String noteContent; // Json parsed string
    private String emailId;

    public Note() {
    }

    public Note(NoteData noteData) {
        this.noteId = noteData.getNoteId();
        this.title = noteData.getTitle();
        this.noteContent = noteData.getNoteContent();
        this.emailId = noteData.getEmailId();
    }
}
