package gh.com.payswitch.thetellerandroid.data;

import gh.com.payswitch.thetellerandroid.Utils;

public class SavedCard implements ItemModel{

    private String first6;
    private String last4;
    private String pan;
    private String maskedPan;
    private String expiryYear;
    private String expiryMonth;
    private int cardType;

    public String getFirst6() {
        return first6;
    }

    public void setFirst6(String first6) {
        this.first6 = first6;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String first6, String last4) {
        this.maskedPan = Utils.spacifyCardNumber(Utils.obfuscateCardNumber(first6, last4));
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public String getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(String expiryYear) {
        this.expiryYear = expiryYear;
    }

    public int getCardType() {
        return cardType;
    }

    public void setCardType(int cardType) {
        this.cardType = cardType;
    }

    @Override
    public int getListItemType() {
        return ItemModel.TYPE_A;
    }
}
