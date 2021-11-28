package BusinessLayer;

import java.io.Serializable;

public class User {
    private String username, password;
    private Type type;
    private int id;


    public User(String username, String password, Type type, int id){
        this.username = username;
        this.password = password;
        this.type = type;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
