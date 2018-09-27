package gh.com.payswitch.thetellerandroid.responses;

public class ChargeResponse {
    String status;
    String code;
    String reason;
    String txRef;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTxRef() { return txRef; }

    public void setTxRef(String txRef) { this.txRef = txRef; }
}
