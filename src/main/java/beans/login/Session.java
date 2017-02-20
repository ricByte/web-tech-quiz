package beans.login;

import beans.User;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class Session {
    private int id;
    private User userId = null;
    private String session = null;
    private GregorianCalendar validUntil = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public GregorianCalendar getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(GregorianCalendar validUntil) {
        this.validUntil = validUntil;
    }

    public void setValidUntil(Timestamp timestamp) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(timestamp.getTime());
        this.validUntil = cal;
    }
}
