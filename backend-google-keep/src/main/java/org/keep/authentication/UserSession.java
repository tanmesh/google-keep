package org.keep.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSession {
    private String accessToken;
    private String emailId;

    public UserSession(String accessToken, String emailId) {
        this.accessToken = accessToken;
        this.emailId = emailId;
    }

    public UserSession() {
    }

    public UserSession(String emailId) {
        this.emailId = emailId;
    }
}
