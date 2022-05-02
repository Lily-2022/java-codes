package register.server;

public class HeartbeatResponse {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
