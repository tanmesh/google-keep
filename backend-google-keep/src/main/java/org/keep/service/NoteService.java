package org.keep.service;

import org.keep.dao.NoteDao;
import org.keep.entity.Note;

import java.util.List;

public class NoteService implements INoteService {
    private NoteDao noteDao;

    public NoteService(NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    @Override
    public Object add(Note note) {
        System.out.println("Adding : " + note.getTitle() );

        return noteDao.addNote(note);
    }

    @Override
    public void delete(String noteId) {
        System.out.println("Deleting : " + noteId);

        noteDao.deleteNote(noteId);
    }

    @Override
    public void edit(Note note) throws Exception {
        System.out.println("Editing : " + note.getNoteId());

        noteDao.updateNote(note);
    }
}
