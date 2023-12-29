package org.keep.configuration;

public class MongoDBConfig {
    private String dbName;
    private int port;
    private String host;
    private String user;
    private String password;

    public MongoDBConfig(String dbName, int port, String host) {
        this.dbName = dbName;
        this.port = port;
        this.host = host;
    }

    public MongoDBConfig() {
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
