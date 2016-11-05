package beans;

public class RegisterObject {
    private User User;
    private Boolean RegisterStatus;

    public void RegisterObject(){
        this.User = null;
        this.RegisterStatus = false;
    }

    public void RegisterObject(User user, Boolean registerStatus){
        this.User = user;
        this.RegisterStatus = registerStatus;
    }

    public Boolean getRegisterStatus() {
        return RegisterStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        RegisterStatus = registerStatus;
    }

    public beans.User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }
}
