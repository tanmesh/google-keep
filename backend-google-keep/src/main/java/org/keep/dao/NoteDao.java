package org.keep.dao;

import org.keep.entity.Note;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import java.util.ArrayList;
import java.util.List;

public class NoteDao extends BasicDAO<Note, String> {
    public NoteDao(Datastore ds) {
        super(ds);
    }

    public String addNote(Note note) {
        return this.getDatastore().save(note).getId().toString();
    }

    public void deleteNote(String noteId) {
        this.getDatastore().delete(Note.class, noteId);
    }

    public void updateNote(Note note) throws Exception {
        String noteId = note.getNoteId();
        Note oldNote = this.getDatastore().createQuery(Note.class).filter("_id", noteId).get();

        if (oldNote == null) {
            throw new Exception("Note dosent exits");
        }

        oldNote.setNoteContent(note.getNoteContent());
        oldNote.setTitle(note.getTitle());

        this.getDatastore().save(oldNote);
    }

    public Note getNote(String noteId) {
        return this.getDatastore().createQuery(Note.class).filter("_id", noteId).get();
    }

    // TODO
    public List<String> getNoteOfUser(String emailId) {
        List<Note> notes = this.getDatastore().createQuery(Note.class).field("emailId").equal(emailId).asList();

        List<String> noteIds = new ArrayList<>();
        for(Note note: notes) {
            noteIds.add(note.getNoteId());
        }
        return noteIds;
    }
}
