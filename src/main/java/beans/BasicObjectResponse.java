package beans;

public class BasicObjectResponse {
    public BasicObjectResponse() {
        this.setStatus(false);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Boolean status;
    private String message;

}
