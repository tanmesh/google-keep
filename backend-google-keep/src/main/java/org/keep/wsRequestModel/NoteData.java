package org.keep.wsRequestModel;

import lombok.Getter;
import lombok.Setter;
import org.keep.entity.Note;

@Getter
@Setter
public class NoteData {
    private String noteId;
    private String title;
    private String noteContent; // Json parsed string
    private String emailId;

    public NoteData() {
    }

    public NoteData(Note note) {
        this.noteId = note.getNoteId();
        this.title = note.getTitle();
        this.noteContent = note.getNoteContent();
        this.emailId = note.getEmailId();
    }
}
