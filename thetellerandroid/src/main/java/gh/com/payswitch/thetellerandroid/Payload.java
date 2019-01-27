package gh.com.payswitch.thetellerandroid;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Payload
{
    public String getCardBIN() {
        return cardBIN;
    }

    private String cardBIN;

    private String payment_plan;

    private String merchant_id;

    private String terminal_id;

    private String voucher_code;

    private String d_response_url;

    public String getIs_mpesa() {
        return is_mpesa;
    }

    public void setIs_mpesa(String is_mpesa) {
        this.is_mpesa = is_mpesa;
    }

    public String getIs_mpesa_lipa() {
        return is_mpesa_lipa;
    }

    public void setIs_mpesa_lipa(String is_mpesa_lipa) {
        this.is_mpesa_lipa = is_mpesa_lipa;
    }

    private String is_mpesa;

    private String is_mpesa_lipa;

    public Payload(List<Meta> meta, String narration,
                   String expirymonth, String apiUser, String PBFPubKey, String IP, String lastname,
                   String firstname, String currency, String merchant_id, String terminal_id,
                   String voucher_code, String d_response_url, String amount, String email, String expiryyear, String cvv, String device_fingerprint,
                   String cardno, String txRef, String cardType) {
        this.narration = narration;
        this.expirymonth = expirymonth;
        this.apiUser = apiUser;
        this.apiKey = PBFPubKey;
        this.IP = IP;
        this.lastname = lastname;
        this.firstname = firstname;
        this.currency = currency;
        this.merchant_id = merchant_id;
        this.terminal_id = terminal_id;
        this.voucher_code = voucher_code;
        this.amount = amount;
        this.email = email;
        this.d_response_url = d_response_url;
        this.expiryyear = expiryyear;
        this.cvv = cvv;
        this.device_fingerprint = device_fingerprint;
        this.cardno = cardno;
        this.txRef = txRef;
        this.cardType = cardType;

        if (meta == null) {
            meta = new ArrayList<>();
        }

        meta.add(new Meta("sdk", "android"));
        this.meta = meta;

    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }

    public String getD_response_url() {
        return d_response_url;
    }

    public void setD_response_url() {
        this.d_response_url = d_response_url;
    }

    public String getVoucherCode() {
        return voucher_code;
    }

    public void setVoucherCode(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public String getBillingcity() {
        return billingcity;
    }

    public void setBillingcity(String billingcity) {
        this.billingcity = billingcity;
    }

    public String getBillingaddress() {
        return billingaddress;
    }

    public void setBillingaddress(String billingaddress) {
        this.billingaddress = billingaddress;
    }

    public String getBillingstate() {
        return billingstate;
    }

    public void setBillingstate(String billingstate) {
        this.billingstate = billingstate;
    }

    public String getBillingcountry() {
        return billingcountry;
    }

    public void setBillingcountry(String billingcountry) {
        this.billingcountry = billingcountry;
    }

    private String billingcity;
    private String billingaddress;
    private String billingstate;
    private String billingcountry;

    public Payload(List<Meta> meta, String narration, String IP, String accountnumber, String accountbank,
                   String lastname, String firstname, String currency, String merchant_id, String terminal_id,
                   String voucher_code, String amount, String email, String device_fingerprint, String txRef, String apiUser, String PBFPubKey,
                   String billingaddress, String billingcity, String billingstate, String billingzip, String billingcountry) {
        this.narration = narration;
        this.IP = IP;
        this.accountnumber = accountnumber;
        this.accountbank = accountbank;
        this.lastname = lastname;
        this.firstname = firstname;
        this.currency = currency;
        this.merchant_id = merchant_id;
        this.terminal_id = terminal_id;
        this.voucher_code = voucher_code;
        this.amount = amount;
        this.email = email;
        this.d_response_url = d_response_url;
        this.device_fingerprint = device_fingerprint;
        this.txRef = txRef;
        this.apiUser = apiUser;
        this.apiKey = PBFPubKey;
        this.billingaddress = billingaddress;
        this.billingstate = billingstate;
        this.billingcity = billingcity;
        this.billingcountry = billingcountry;
        this.billingzip = billingzip;

        if (meta == null) {
            meta = new ArrayList<>();
        }

        meta.add(new Meta("sdk", "android"));
        this.meta = meta;

    }

    public Payload(String phonenumber,
                   List<Meta> meta,
                   String narration,
                   String IP,
                   String lastname,
                   String firstname,
                   String currency,
                   String merchant_id,
                   String terminal_id,
                   String voucher_code,
                   String amount,
                   String email,
                   String device_fingerprint,
                   String txRef,
                   String apiUser,
                   String PBFPubKey) {
        this.meta = meta;
        this.narration = narration;
        this.IP = IP;
        this.phonenumber = phonenumber;
        this.lastname = lastname;
        this.firstname = firstname;
        this.currency = currency;
        this.merchant_id = merchant_id;
        this.terminal_id = terminal_id;
        this.voucher_code = voucher_code;
        this.amount = amount;
        this.email = email;
        this.device_fingerprint = device_fingerprint;
        this.txRef = txRef;
        this.apiUser = apiUser;
        this.apiKey = PBFPubKey;

        if (meta == null) {
            meta = new ArrayList<>();
        }

        meta.add(new Meta("sdk", "android"));
        this.meta = meta;

    }

    public Payload(List<Meta> meta, String narration, String IP, String accountnumber, String accountbank,
                   String lastname, String firstname, String currency, String merchant_id, String terminal_id,
                   String voucher_code, String amount, String d_response_url,
                   String email, String device_fingerprint, String txRef, String apiUser, String PBFPubKey) {
        this.meta = meta;
        this.narration = narration;
        this.IP = IP;
        this.accountnumber = accountnumber;
        this.accountbank = accountbank;
        this.lastname = lastname;
        this.firstname = firstname;
        this.currency = currency;
        this.merchant_id = merchant_id;
        this.terminal_id = terminal_id;
        this.voucher_code = voucher_code;
        this.amount = amount;
        this.d_response_url = d_response_url;
        this.email = email;
        this.device_fingerprint = device_fingerprint;
        this.txRef = txRef;
        this.apiUser = apiUser;
        this.apiKey = PBFPubKey;

        if (meta == null) {
            meta = new ArrayList<>();
        }

        meta.add(new Meta("sdk", "android"));
        this.meta = meta;

    }

    public String getToken() {
        return token;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    private String passcode;

    public String getPayment_plan() {
        return payment_plan;
    }

    public void setPayment_plan(String payment_plan) {
        this.payment_plan = payment_plan;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String token;

    public List<Meta> getMeta() {
        return meta;
    }

    public void setMeta(List<Meta> meta) {
        this.meta = meta;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    private String pin;

    private String redirect_url = "https://23.239.22.186/loader.html";

    public String getPBFSecKey() {
        return PBFSecKey;
    }

    public void setPBFSecKey(String PBFSecKey) {
        this.PBFSecKey = PBFSecKey;
    }

    public void setSECKEY(String SECKEY) {
        this.SECKEY = SECKEY;
    }

    String SECKEY;

    private String PBFSecKey;

    @SerializedName("suggested_auth")
    private String suggestedAuth;

    private List<Meta> meta;

    public String getBillingzip() {
        return billingzip;
    }

    public void setBillingzip(String billingzip) {
        this.billingzip = billingzip;
    }

    private String billingzip;

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    private String payment_type;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    private String phonenumber;

    private String narration;

    public void setIs_internet_banking(String is_internet_banking) {
        this.is_internet_banking = is_internet_banking;
    }

    private String is_internet_banking;

    private String expirymonth;

    private String apiUser;
    private String apiKey;

    private String IP;

    private String accountnumber;

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccountbank() {
        return accountbank;
    }

    public void setAccountbank(String accountbank) {
        this.accountbank = accountbank;
    }

    private String accountbank;

    private String lastname;

    private String firstname;

    private String currency;

    private String amount;

    private String email;

    private String expiryyear;

    private String cvv;

    private String device_fingerprint;

    private String cardno;

    private String txRef;

    private String cardType;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpirymonth ()
    {
        return expirymonth;
    }

    public void setExpirymonth (String expirymonth)
    {
        this.expirymonth = expirymonth;
    }

    public String getApiUser()
    {
        return apiUser;
    }

    public void setApiUser(String apiUser)
    {
        this.apiUser = apiUser;
    }

    public String getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(String apiKey)
    {
        this.apiKey = apiKey;
    }

    public String getIP ()
    {
        return IP;
    }

    public void setIP (String IP)
    {
        this.IP = IP;
    }

    public String getLastname ()
    {
        return lastname;
    }

    public void setLastname (String lastname)
    {
        this.lastname = lastname;
    }

    public String getFirstname ()
    {
        return firstname;
    }

    public void setFirstname (String firstname)
    {
        this.firstname = firstname;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void set3dUrl(String d_response_url) {
        this.d_response_url = d_response_url;
    }

    public String get3dUrl() {
        return d_response_url;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getExpiryyear ()
    {
        return expiryyear;
    }

    public void setExpiryyear (String expiryyear)
    {
        this.expiryyear = expiryyear;
    }

    public String getCvv ()
    {
        return cvv;
    }

    public void setCvv (String cvv)
    {
        this.cvv = cvv;
    }

    public String getDevice_fingerprint ()
    {
        return device_fingerprint;
    }

    public void setDevice_fingerprint (String device_fingerprint)
    {
        this.device_fingerprint = device_fingerprint;
    }

    public String getCardno ()
    {
        return cardno;
    }

    public void setCardno (String cardno)
    {
        this.cardno = cardno;
    }

    public String getTxRef ()
    {
        return txRef;
    }

    public void setTxRef (String txRef)
    {
        this.txRef = txRef;
    }

    public void setSuggestedAuth(String suggestedAuth) {
        this.suggestedAuth = suggestedAuth;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [expirymonth = "+expirymonth+", apiUser = "+ apiUser +", apiKey = "+ apiKey +", IP = "+IP+", lastname = "+lastname+", firstname = "+firstname+", currency = "+currency+", narration"+narration+", merchant_id"+merchant_id+", terminal_id"+terminal_id+", voucher_code"+voucher_code+", amount = "+amount+", email = "+email+", expiryyear = "+expiryyear+", cvv = "+cvv+", device_fingerprint = "+device_fingerprint+", d_response_url"+d_response_url+", cardno = "+cardno+", txRef = "+txRef+","+cardType+"]";
    }

    public void setCardBIN(String cardBIN) {
        this.cardBIN = cardBIN;
    }

    public String is_mobile_money_gh;

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNetwork() { return network; }

    public String network;



    public void setIs_mobile_money_gh(String is_mobile_money_gh) {
        this.is_mobile_money_gh = is_mobile_money_gh;
    }
}

