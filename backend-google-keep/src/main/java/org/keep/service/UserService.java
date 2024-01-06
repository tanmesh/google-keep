package org.keep.service;

import org.keep.authentication.UserSession;
import org.keep.dao.NoteDao;
import org.keep.dao.UserDao;
import org.keep.entity.Note;
import org.keep.entity.User;
import org.keep.wsRequestModel.NoteData;
import org.keep.wsRequestModel.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService implements IUserService {
    private UserDao userDao;

    private NoteDao noteDao;

    private AccessTokenService accessTokenService;

    public UserService(UserDao userDao, NoteDao noteDao, AccessTokenService accessTokenService) {
        this.userDao = userDao;
        this.noteDao = noteDao;
        this.accessTokenService = accessTokenService;
    }

    @Override
    public void signup(String emailId, String password) throws Exception {
        userDao.addUser(emailId, password);
    }

    @Override
    public UserSession login(String emailId, String password) throws Exception {
        User user = userDao.getUser(emailId);

        if(user == null) {
            throw new Exception("Its a new user");
        }

        UserSession userSession;
        try {
            String existingPassword = user.getPassword();
            System.out.println("Input password: " + password);
            System.out.println("Existing password: " + existingPassword);
            if(!decrypt(password, existingPassword)) {
                throw new Exception("Password doesn't match");
            }

            String accessToken = UUID.randomUUID().toString();
            userSession = new UserSession(accessToken, emailId);

            if(!accessTokenService.saveAccessToken(userSession)) {
                throw new Exception("Unable to save access token");
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return userSession;
    }

    private String encrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean decrypt(String newPassword, String prevPassword) {
        return BCrypt.checkpw(newPassword, prevPassword);
    }
    @Override
    public UserData getUser(String emailId) throws Exception {
        User user = userDao.getUser(emailId);

        UserData userData = new UserData();
        userData.setName(user.getName());
        userData.setEmailId(user.getEmailId());

        List<NoteData> notes = new ArrayList<>();
        for (String noteId : noteDao.getNoteOfUser(emailId)) {
            System.out.println(noteId);
            Note note = noteDao.getNote(noteId);

            NoteData noteData = new NoteData();
            noteData.setContent(note.getNoteContent());
            noteData.setTitle(note.getTitle());
            noteData.setNoteId(note.getNoteId().toString());
            notes.add(noteData);
        }
        userData.setNotes(notes);
        return userData;
    }

    @Override
    public List<NoteData> getAllNotes(String emailId) {
        List<NoteData> notes = new ArrayList<>();

        List<String> noteIdsFromDB = noteDao.getNoteOfUser(emailId);

        for (String noteId : noteIdsFromDB) {
            System.out.println("Here: " + noteId);
            Note note = noteDao.getNote(noteId);
            System.out.println(note.getNoteContent());
            System.out.println(note.getEmailId());

            NoteData noteData = new NoteData(note);
            notes.add(noteData);
        }
        return notes;
    }
}
