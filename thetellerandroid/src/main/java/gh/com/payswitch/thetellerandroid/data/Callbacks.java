package gh.com.payswitch.thetellerandroid.data;


import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;

public class Callbacks {

    public interface OnChargeRequestComplete {
        void onSuccess(ChargeResponse response, String responseAsJSONString);
        void onError(String message, String responseAsJSONString);
    }

    public interface SavedCardSelectedListener {
        void onCardSelected(SavedCard savedCard);
    }

    public interface SavedPhoneSelectedListener {
        void onPhoneSelected(SavedPhone savedPhone);
    }

}
