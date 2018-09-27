package gh.com.payswitch.thetellerandroid.data;


import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.responses.FeeCheckResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponsev2;

import java.util.List;

public class Callbacks {

    public interface OnChargeRequestComplete {
        void onSuccess(ChargeResponse response, String responseAsJSONString);
        void onError(String message, String responseAsJSONString);
    }

    public interface OnValidateChargeCardRequestComplete {
        void onSuccess(ChargeResponse response, String responseAsJSONString);
        void onError(String message, String responseAsJSONString);
    }

    public interface OnRequeryRequestComplete {
        void onSuccess(RequeryResponse response, String responseAsJSONString);
        void onError(String message, String responseAsJSONString);
    }

    public interface OnRequeryRequestv2Complete {
        void onSuccess(RequeryResponsev2 response, String responseAsJSONString);
        void onError(String message, String responseAsJSONString);
    }

    public interface OnGetBanksRequestComplete {
        void onSuccess(List<Bank> banks);
        void onError(String message);
    }

    public interface BankSelectedListener {
        void onBankSelected(Bank b);
    }

    public interface SavedCardSelectedListener {
        void onCardSelected(SavedCard savedCard);
    }

    public interface SavedPhoneSelectedListener {
        void onPhoneSelected(SavedPhone savedPhone);
    }

    public interface OnGetFeeRequestComplete {
        void onSuccess(FeeCheckResponse response);
        void onError(String message);
    }
}
