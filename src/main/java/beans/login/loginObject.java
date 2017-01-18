package beans.login;

import beans.User;
import beans.login.Session;

public class loginObject {

    private beans.User User = null;
    private Session Session = null;


    public loginObject() {}

    public loginObject(User user,Session session) {
        this.User = user;
        this.Session = session;
    }

    public beans.User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Session getSession() {
        return Session;
    }

    public void setSession(Session session) {
        this.Session = session;
    }

    public static loginObject createFailedLoginObject() {
        return new loginObject();
    }

    public static loginObject createSuccessfullLogin(User user,Session session) {
        session.setUserId(null);
        return new loginObject(user,session);
    }
}
