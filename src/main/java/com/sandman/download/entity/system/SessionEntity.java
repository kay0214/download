package com.sandman.download.entity.system;

public class SessionEntity {
    private String id;
    private String sessionStr;

    public SessionEntity() {
    }

    public SessionEntity(String id) {
        this.id = id;
    }

    public SessionEntity(String id, String sessionStr) {
        this.id = id;
        this.sessionStr = sessionStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionStr() {
        return sessionStr;
    }

    public void setSessionStr(String sessionStr) {
        this.sessionStr = sessionStr;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id='" + id + '\'' +
                ", sessionStr='" + sessionStr + '\'' +
                '}';
    }
}
