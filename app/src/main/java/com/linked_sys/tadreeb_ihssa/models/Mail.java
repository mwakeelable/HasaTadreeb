package com.linked_sys.tadreeb_ihssa.models;

/**
 * Created by wakeel on 26/09/17.
 */

public class Mail {
    private String id;
    private String title;
    private String body;
    private String isRead;
    private String userType;

    public Mail() {

    }

    public Mail(String id, String title, String body, String isRead, String userType) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.isRead = isRead;
        this.userType = userType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
