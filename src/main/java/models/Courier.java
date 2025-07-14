package models;

public class Courier {

    private String name;
    private String login;
    private String password;

    public Courier(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }

    public Courier(String login, String password) {
        this.login = login;
        this.password = password;

    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }
}
