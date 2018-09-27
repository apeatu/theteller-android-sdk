package gh.com.payswitch.thetellerandroid.ghmobilemoney;

import java.util.List;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.data.SavedPhone;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponse;
import gh.com.payswitch.thetellerandroid.thetellerInitializer;

public interface GhMobileMoneyContract {

    interface View {
        void showProgressIndicator(boolean active);
        void showPollingIndicator(boolean active);
//        void onPollingRoundComplete(String code, Payload payload);
        void onPaymentError(String message);
        void showToast(String message);
        void onPaymentSuccessful(String status, String responseAsString);
        void displayFee(String charge_amount, Payload payload);
        void showFetchFeeFailed(String s);
        void onPaymentFailed(String message, String responseAsJSONString);
//        void showFullProgressIndicator(boolean active);
        void showSavedPhoneList(List<SavedPhone> savedPhoneList);
//        void onChargePhoneNumberSuccessful(ChargeResponse response);
    }

    interface UserActionsListener {
//        void fetchFee(Payload payload);
        void chargeGhMobileMoney(Payload payload, String apiKey);
//        void queryTx(String code, String txRef, String apiKey);
//        void requeryTxv2(String flwRef, String txRef, String apiKey);
        void onSavedPhonesClicked(String phoneNumber);
        List<SavedPhone> checkForSavedGHMobileMoney(String email);
        void saveThisPhone(String phoneNumber, String apiKey, String phoneNumeber, String network);
//        void verifyRequeryResponse(RequeryResponse response, String responseAsJSONString, thetellerInitializer ravePayInitializer, String flwRef);
    }
}
