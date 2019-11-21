package gh.com.payswitch.thetellerandroid.data;

public class ErrorBody {
    public ErrorBody(String status, String message) {
        this.status = status;
        this.message = message;
    }

    private String status;

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

    private String message;

    public Data getData() {
        return data;
    }

    Data data;


//    String data;

    public static class Data {
        boolean is_error;

        public String getCode() {
            return code;
        }

        String code;
    }
}

