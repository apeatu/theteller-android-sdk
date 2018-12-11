package gh.com.payswitch.thetellerandroid.ghmobilemoney;

import com.google.gson.annotations.SerializedName;

public class ChargeRequestBody {
    private String amount;
    private String processing_code;
    private String transaction_id;
    private String desc;
    private String merchant_id;
    private String subscriber_number;
    @SerializedName("r-switch")
    private String rSwitch;
    private String voucher_code;

    public ChargeRequestBody getClient() {
        return this;
    }

    public void setClient(String amount, String processing_code, String transaction_id, String desc, String merchant_id,
                          String subscriber_number, String rSwitch, String voucher_code) {
        this.amount = amount;
        this.processing_code = processing_code;
        this.transaction_id = transaction_id;
        this.desc = desc;
        this.merchant_id = merchant_id;
        this.subscriber_number = subscriber_number;
        this.rSwitch = rSwitch;
        this.voucher_code = voucher_code;
    }

    public String getrSwitch() {
        return rSwitch;
    }

    public void setrSwitch(String rSwitch) {
        this.rSwitch = rSwitch;
    }
}