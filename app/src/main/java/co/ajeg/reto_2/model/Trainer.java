package co.ajeg.reto_2.model;

import java.io.Serializable;

public class Trainer implements Serializable {

    private String id;
    private String userName;

    public Trainer() {
    }

    public Trainer(String id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
