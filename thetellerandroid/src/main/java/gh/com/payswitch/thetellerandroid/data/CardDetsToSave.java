package gh.com.payswitch.thetellerandroid.data;

public class CardDetsToSave {
    private String first6;

    public String getFirst6() {
        return first6;
    }

    public String getLast4() {
        return last4;
    }

    private String last4;

    public CardDetsToSave(String first6, String last4) {
        this.first6 = first6;
        this.last4 = last4;
    }



}
