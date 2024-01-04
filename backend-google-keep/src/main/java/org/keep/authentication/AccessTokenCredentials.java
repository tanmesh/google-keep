package org.keep.authentication;

public class AccessTokenCredentials {
    private String token;

    public AccessTokenCredentials(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

