package gh.com.payswitch.thetellerandroid;

import java.util.List;

public class PayloadBuilder {
    private String expirymonth;
    private String pbfPubKey;
    private String apiUser;
    private String ip;
    private String lastname;
    private String firstname;
    private String currency = "GHS";
    private String merchant_id;
    private String terminal_id;
    private String voucher_code;
    private String amount;
    private String email;
    private String d_response_url;
    private String expiryyear;
    private String cvv;
    private String device_fingerprint;
    private String cardno;
    private String payment_plan;
    private String network;
    private String cardType;

    public PayloadBuilder setIs_mobile_money_gh(String is_mobile_money_gh) {
        this.is_mobile_money_gh = is_mobile_money_gh;
        return this;
    }

    private String is_mobile_money_gh;

    private String phonenumber;

    private String txRef;
    private String meta = "";

    public PayloadBuilder setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
        return this;
    }

    private String customer_phone;
    private String narration;
    private String pin;
    private String accountbank;
    private String accountnumber;

    public PayloadBuilder setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
        return this;
    }

    public PayloadBuilder setAccountbank(String accountbank) {
        this.accountbank = accountbank;
        return this;
    }

    public PayloadBuilder setPin(String pin) {
        this.pin = pin;
        return this;
    }

    public PayloadBuilder setExpirymonth(String expirymonth) {
        this.expirymonth = expirymonth;
        return this;
    }

    public PayloadBuilder setApiUser(String apiUser) {
        this.apiUser = apiUser;
        return this;
    }

    public PayloadBuilder setApiKey(String pbfPubKey) {
        this.pbfPubKey = pbfPubKey;
        return this;
    }

    public PayloadBuilder setIP(String ip) {
        this.ip = ip;
        return this;
    }

    public PayloadBuilder set3dUrl(String d_response_url) {
        this.d_response_url = d_response_url;
        return this;
    }

    public PayloadBuilder setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public PayloadBuilder setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public PayloadBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public PayloadBuilder setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
        return this;
    }

    public PayloadBuilder setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
        return this;
    }

    public PayloadBuilder setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
        return this;
    }

    public PayloadBuilder setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    public PayloadBuilder setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public PayloadBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public PayloadBuilder setExpiryyear(String expiryyear) {
        this.expiryyear = expiryyear;
        return this;
    }

    public PayloadBuilder setCvv(String cvv) {
        this.cvv = cvv;
        return this;
    }

    public PayloadBuilder setPaymentPlan(String payment_plan) {
        this.payment_plan = payment_plan;
        return this;
    }

    public PayloadBuilder setDevice_fingerprint(String device_fingerprint) {
        this.device_fingerprint = device_fingerprint;
        return this;
    }

    public PayloadBuilder setCardno(String cardno) {
        this.cardno = cardno;
        return this;
    }

    public PayloadBuilder setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }

    public Payload createPayload() {
        List<Meta> metaObj = Utils.pojofyMetaString(meta);

        Payload payload = new Payload(metaObj, narration, expirymonth, apiUser,
                pbfPubKey, ip, lastname,
                firstname, currency, merchant_id, terminal_id,
                voucher_code, d_response_url,
                amount, email, expiryyear,
                cvv, device_fingerprint,
                cardno, txRef, cardType);

        if (payment_plan != null) {
            payload.setPayment_plan(payment_plan);
        }

        return payload;
    }

    public Payload createGhMobileMoneyPayload() {
        List<Meta> metaObj = Utils.pojofyMetaString(meta);
        Payload payload = new Payload(phonenumber, metaObj, narration, ip, lastname,
                firstname, currency, merchant_id, terminal_id, voucher_code, amount, email, device_fingerprint, txRef, apiUser, pbfPubKey);
        payload.setIs_mobile_money_gh("1");
        payload.setPayment_type("mobilemoneygh");
        payload.setNetwork(network);
        return payload;
    }

    public PayloadBuilder setMeta(String meta) {
        this.meta = meta;
        return this;
    }

    public PayloadBuilder setNarration(String narration) {
        this.narration = narration;
        return this;
    }

    public PayloadBuilder setNetwork(String network) {
        this.network = network;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}