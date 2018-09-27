package gh.com.payswitch.thetellerandroid.data;

public class Bank {

    String bankname;
    String bankcode;
    boolean internetbanking;

    public String getBankname() {
        return bankname;
    }

    public String getBankcode() {
        return bankcode;
    }

    public boolean isInternetbanking() {
        return internetbanking;
    }

}