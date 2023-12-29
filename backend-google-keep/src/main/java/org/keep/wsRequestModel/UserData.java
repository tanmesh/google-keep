package org.keep.wsRequestModel;

import java.util.List;

public class UserData {
    private String emailId;
    private String name;
    private List<NoteData> notes;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NoteData> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteData> notes) {
        this.notes = notes;
    }
}
