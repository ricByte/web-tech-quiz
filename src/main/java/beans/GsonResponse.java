package beans;

import java.util.Map;

public class GsonResponse {
    private String status;
    private int code;
    private Map content;

    public GsonResponse() {
    }

    public GsonResponse(String status, int code, Map content) {
        this.status = status;
        this.code = code;
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map getContent() {
        return content;
    }

    public void setContent(Map content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GsonResponse{" +
                "status:'" + status + '\'' +
                ", code:" + code +
                ", content:" + content +
                '}';
    }
}
