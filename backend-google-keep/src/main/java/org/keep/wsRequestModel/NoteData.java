package org.keep.wsRequestModel;

import lombok.Getter;
import lombok.Setter;
import org.keep.entity.Note;

@Getter
@Setter
public class NoteData {
    private String noteId;
    private String title;
    private String content; // Json parsed string

    public NoteData() {
    }

    public NoteData(Note note) {
        this.noteId = note.getNoteId();
        this.title = note.getTitle();
        this.content = note.getNoteContent();
    }
}
