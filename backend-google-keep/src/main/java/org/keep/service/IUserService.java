package org.keep.service;

import org.keep.entity.Note;
import org.keep.wsRequestModel.NoteData;
import org.keep.wsRequestModel.UserData;

import java.util.List;

public interface IUserService {
    void signup(String emailId, String password) throws Exception;

    void login(String emailId, String password) throws Exception;

    UserData getUser(String emailId) throws Exception;

    List<NoteData> getAllNotes(String emailId) throws Exception; // retrieve all notes from a particular user

    void addNote(String emailId, Object noteId) throws Exception;
}
