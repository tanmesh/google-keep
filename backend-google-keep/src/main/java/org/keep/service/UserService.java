package org.keep.service;

import org.keep.dao.NoteDao;
import org.keep.dao.UserDao;
import org.keep.entity.Note;
import org.keep.entity.User;
import org.keep.wsRequestModel.NoteData;
import org.keep.wsRequestModel.UserData;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService {
    private UserDao userDao;

    private NoteDao noteDao;

    public UserService(UserDao userDao, NoteDao noteDao) {
        this.userDao = userDao;
        this.noteDao = noteDao;
    }

    @Override
    public void signup(String emailId, String password) throws Exception {
        userDao.addUser(emailId, password);
    }

    @Override
    public void login(String emailId, String password) throws Exception {
        userDao.verifyUser(emailId, password);
    }

    @Override
    public UserData getUser(String emailId) throws Exception {
        User user = userDao.getUser(emailId);

        UserData userData = new UserData();
        userData.setName(user.getName());
        userData.setEmailId(user.getEmailId());

        List<NoteData> notes = new ArrayList<>();
        for (Note note : user.getNoteIds()) {
            NoteData noteData = new NoteData();
            noteData.setNoteContent(note.getNoteContent());
            noteData.setTitle(note.getTitle());
            noteData.setEmailId(note.getEmailId());
            noteData.setNoteId(note.getNoteId().toString());
            notes.add(noteData);
        }
        userData.setNotes(notes);
        return userData;
    }

    @Override
    public List<NoteData> getAllNotes(String emailId) throws Exception {
        List<NoteData> notes = new ArrayList<>();
        for (Note note : userDao.getAllNotes(emailId)) {
            NoteData noteData = new NoteData();
            noteData.setNoteContent(note.getNoteContent());
            noteData.setTitle(note.getTitle());
            noteData.setEmailId(note.getEmailId());
            noteData.setNoteId(note.getNoteId().toString());
            notes.add(noteData);
        }

        return notes;
    }

    @Override
    public void addNote(String emailid, Object noteId) throws Exception {
        userDao.addNote(emailid, noteId);
    }
}
