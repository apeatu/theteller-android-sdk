package gh.com.payswitch.thetellerandroid.data;

public class SavedPhone implements ItemModel{
    private String phoneNumber;
    private String network;

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    @Override
    public int getListItemType() {
        return ItemModel.TYPE_B;
    }
}
