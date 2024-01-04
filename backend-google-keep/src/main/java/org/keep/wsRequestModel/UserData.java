package org.keep.wsRequestModel;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserData {
    private String emailId;
    private String name;
    private String password;
    private List<NoteData> notes;
}
