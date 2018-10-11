package gh.com.payswitch.thetellerandroid.data;

public class SavedPhone implements ItemModel{
    private String phoneNumber;
    private String network;
    private int networkImage;

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getNetwork() { return network; }

    public void setNetwork(String network) { this.network = network; }

    public int getNetworkImage() {
        return networkImage;
    }

    public void setNetworkImage(int networkImage) {
        this.networkImage = networkImage;
    }

    @Override
    public int getListItemType() {
        return ItemModel.TYPE_B;
    }
}
