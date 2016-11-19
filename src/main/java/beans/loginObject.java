package beans;

public class loginObject {
    private User User;
    private String session;
    private Boolean loginStatus;


    public loginObject(){
        this.User = null;
        this.loginStatus = false;
        this.session = "";
    }

    public loginObject(User user, Boolean registerStatus){
        this.User = user;
        this.loginStatus = registerStatus;
    }

    public Boolean getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Boolean loginStatus) {
        this.loginStatus = loginStatus;
    }

    public beans.User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
