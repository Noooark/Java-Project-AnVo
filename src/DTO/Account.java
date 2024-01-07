package DTO;

import javax.swing.text.Position;

public class Account {
    private String Username, Password, Position;
    public Account(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }
    public Account(String Username, String Password, String Position){
        this.Username = Username;
        this.Password = Password;
        this.Position = Position;
    }
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }
}
