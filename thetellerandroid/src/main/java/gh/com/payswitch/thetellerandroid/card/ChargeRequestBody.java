package gh.com.payswitch.thetellerandroid.card;

public class ChargeRequestBody {
    String apiKey;
    String client;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}