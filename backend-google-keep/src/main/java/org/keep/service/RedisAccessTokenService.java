package org.keep.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.keep.authentication.UserSession;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashMap;
import java.util.Map;

public class RedisAccessTokenService implements AccessTokenService {
    /*
        un --> userName
        at --> accessToken
    */
    private ObjectMapper objectMapper;
    private JedisPool jedisPool;
    private Map<String, String> keyMap = new HashMap<String, String>();

    public RedisAccessTokenService() {
        this.objectMapper = new ObjectMapper();
        this.jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
    }

    @Override
    public boolean saveAccessToken(UserSession userSession) {
        boolean flag = false;
        int ttlInSeconds = 60 * 60 * 24; // 24 hr

        try (Jedis jedis = jedisPool.getResource()) {
            String agentJson = objectMapper.writeValueAsString(userSession);

            String accessToken = userSession.getAccessToken();
            jedis.setex(getAccessTokenKey(accessToken), ttlInSeconds, agentJson);       // {token, json}
            flag = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return flag;
    }

    private String getUserNameKey(String emailId) {
        return "unToAt:" + emailId;
    }

    private String getAccessTokenKey(String accessToken) {
        return "atToUn:" + accessToken;
    }

    @Override
    public UserSession getUserSessionFromAccessToken(String accessToken) {
        UserSession userSession = null;
        try (Jedis jedis = jedisPool.getResource()) {
            String agentJson = jedis.get(getAccessTokenKey(accessToken));
            userSession = objectMapper.readValue(agentJson, UserSession.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return userSession;
    }

    @Override
    public boolean isValidToken(String accessToken) {
        return getUserSessionFromAccessToken(accessToken) != null;
    }

    @Override
    public boolean removeAccessToken(String accessToken) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(getAccessTokenKey(accessToken));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    // TODO: remove user
    @Override
    public void removeUser(String userName) {

    }
}