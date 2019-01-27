package gh.com.payswitch.thetellerandroid;

import org.parceler.Parcel;

@Parcel
public class thetellerInitializer {
    String email;
    double amount;
    String apiKey;
    String apiUser;
    String txRef;
    String narration;
    String currency;
    String merchant_id;
    String terminal_id;
    String voucher_code;
    String fName;
    String lName;
    String meta;
    String d_response_url;
    String payment_plan;
    boolean withCard = true;
    boolean withGHMobileMoney = true;
    int theme;
    boolean allowSaveCard;
    boolean staging = true;

    public thetellerInitializer(String email, double amount, String apiKey, String apiUser, String txRef, String narration,
                                String currency, String merchant_id, String terminal_id, String voucher_code, String fName,
                                String lName, boolean withCard,
                                boolean withGHMobileMoney, String d_response_url,  int theme,
                                boolean staging, boolean allowSaveCard, String meta, String payment_plan) {
        this.email = email;
        this.amount = amount;
        this.apiKey = apiKey;
        this.apiUser = apiUser;
        this.txRef = txRef;
        this.narration = narration;
        this.currency = currency;
        this.merchant_id = merchant_id;
        this.terminal_id = terminal_id;
        this.voucher_code = voucher_code;
        this.fName = fName;
        this.lName = lName;
        this.withGHMobileMoney = withGHMobileMoney;
        this.withCard = withCard;
        this.d_response_url = d_response_url;
        this.theme = theme;
        this.staging = staging;
        this.allowSaveCard = allowSaveCard;
        this.meta = meta;
        this.payment_plan = payment_plan;
    }

    public thetellerInitializer() {
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public boolean isAllowSaveCard() {
        return allowSaveCard;
    }

    public void setAllowSaveCard(boolean allowSaveCard) {
        this.allowSaveCard = allowSaveCard;
    }

    public boolean isStaging() {
        return staging;
    }

    public void setStaging(boolean staging) {
        this.staging = staging;
    }

    public int getTheme() {
        return theme;
    }

    public void setTheme(int theme) {
        this.theme = theme;
    }

    public boolean isWithGHMobileMoney() {
        return withGHMobileMoney;
    }

    public void setWithGHMobileMoney(boolean withGHMobileMoney) {
        this.withGHMobileMoney = withGHMobileMoney;
    }

    public void set3dUrl(String d_response_url) {
        this.d_response_url = d_response_url;
    }

    public String get3dUrl() {
        return d_response_url;
    }

    public boolean isWithCard() {
        return withCard;
    }

    public void setWithCard(boolean withCard) {
        this.withCard = withCard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getApiKey() {
        return apiKey;
    }
    public String getApiUser() {
        return apiUser;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    public void setApiUser(String apiUser) {
        this.apiUser = apiUser;
    }

    public String getTxRef() {
        return txRef;
    }

    public void setTxRef(String txRef) {
        this.txRef = txRef;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getVoucher_code() {
        return voucher_code;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPayment_plan() {
        return payment_plan;
    }

    public void setPayment_plan(String payment_plan) {
        this.payment_plan = payment_plan;
    }
}
