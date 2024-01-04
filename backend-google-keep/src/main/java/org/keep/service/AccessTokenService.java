package org.keep.service;

import org.keep.authentication.UserSession;

public interface AccessTokenService {
//    UserSession createAccessToken(UserSession userSession);

    boolean saveAccessToken(UserSession userSession);

    UserSession getUserSessionFromAccessToken(String accessToken);

    boolean isValidToken(String accessToken);

    boolean removeAccessToken(String accessToken);

    void removeUser(String userName);
}
