package gh.com.payswitch.thetellerandroid;

import org.parceler.Parcel;

@Parcel
public class thetellerInitializer {
    String email;
    double amount;
    String apiKey;
    String txRef;
    String narration;
    String currency;
    String fName;
    String lName;
    String meta;
    String payment_plan;
    boolean withCard = true;
    boolean withGHMobileMoney = true;
    int theme;
    boolean allowSaveCard;
    boolean staging = true;

    public thetellerInitializer(String email, double amount, String apiKey, String txRef, String narration,
                                String currency, String fName,
                                String lName, boolean withCard,
                                boolean withGHMobileMoney, int theme,
                                boolean staging, boolean allowSaveCard, String meta, String payment_plan) {
        this.email = email;
        this.amount = amount;
        this.apiKey = apiKey;
        this.txRef = txRef;
        this.narration = narration;
        this.currency = currency;
        this.fName = fName;
        this.lName = lName;
        this.withGHMobileMoney = withGHMobileMoney;
        this.withCard = withCard;
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

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
