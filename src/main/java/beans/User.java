package beans;

public class User {

    private String email;
    private String nickname;
    private String password;
    private int cleverness;
    private int typeOfPlayer;

    public User() {
    }

    public User(String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.cleverness = cleverness;
        this.typeOfPlayer = typeOfPlayer;
    }

    public User(String email, String nickname, String password, int cleverness, int typeOfPlayer) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.cleverness = cleverness;
        this.typeOfPlayer = typeOfPlayer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCleverness() {
        return cleverness;
    }

    public void setCleverness(int cleverness) {
        this.cleverness = cleverness;
    }

    public int getTypeOfPlayer() {
        return typeOfPlayer;
    }

    public void setTypeOfPlayer(int typeOfPlayer) {
        this.typeOfPlayer = typeOfPlayer;
    }
}
