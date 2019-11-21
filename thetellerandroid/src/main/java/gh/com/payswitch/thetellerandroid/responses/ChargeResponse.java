package gh.com.payswitch.thetellerandroid.responses;

public class ChargeResponse {
    private String status;
    private String code;
    private String reason;
    private String transaction_id;

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

    public String getTxRef() { return transaction_id; }

    public void setTxRef(String txRef) { this.transaction_id = txRef; }
}
