package gh.com.payswitch.thetellerandroid.card;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.thetellerInitializer;
import gh.com.payswitch.thetellerandroid.data.SavedCard;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponse;

import java.util.List;

public interface CardContract {

    interface View {
        void showProgressIndicator(boolean active);
        void onPaymentError(String message);

        void onPinAuthModelSuggested(Payload payload);

        void showToast(String message);

        void showOTPLayout(String flwRef, String chargeResponseMessage);

//        void onValidateSuccessful(String message, String responseAsString);

//        void onValidateError(String message);

        void onVBVAuthModelUsed(String authUrlCrude, String responseAsJSONString, String txRef);

        void onPaymentSuccessful(String status, String responseAsString);

        void onPaymentFailed(String status, String responseAsString);

        void showFullProgressIndicator(boolean active);

        void showSavedCards(List<SavedCard> cards);

//        void onTokenRetrieved(String flwRef, String cardBIN, String token);

//        void onTokenRetrievalError(String s);

//        void displayFee(String charge_amount, Payload payload, int why);

        void showFetchFeeFailed(String s);

        void hideSavedCardsButton();

//        void onChargeTokenComplete(ChargeResponse response);

//        void onChargeCardSuccessful(ChargeResponse response);

        void onAVS_VBVSECURECODEModelSuggested(Payload payload);

        void onAVSVBVSecureCodeModelUsed(String authurl, String flwRef, String txRef);

//        void onValidateCardChargeFailed(String flwRef, String responseAsJSON);

//        void onRequerySuccessful(RequeryResponse response, String responseAsJSONString, String flwRef);

        void onNoAuthInternationalSuggested(Payload payload);

        void onNoAuthUsed(String flwRef, String secretKey);
    }

    interface UserActionsListener {
        void chargeCard(Payload payload, String secretKey);

        void chargeCardWithSuggestedAuthModel(Payload payload, String zipOrPin, String authModel, String secretKey);

//        void validateCardCharge(String flwRef, String otp, String PBFPubKey);

//        void requeryTx(String flwRef, String SECKEY, boolean shouldISaveCard);

//        void requeryTxForToken(String flwRef, String SECKEY);

        void savePotentialCardDets(String cardFirst6, String cardLast4);

        void onSavedCardsClicked(String email);

//        void chargeToken(Payload payload);

        void saveThisCard(String email, String apikey, String cardNo, String expiryMonth, String expiryYear, String cardType);

//        void fetchFee(Payload payload, int reason);

        List<SavedCard> checkForSavedCards(String email);

        void onAttachView(CardContract.View view);

        void onDetachView();

//        void verifyRequeryResponse(RequeryResponse response, String responseAsJSONString, thetellerInitializer ravePayInitializer, String flwRef);

        void chargeCardWithAVSModel(Payload payload, String address, String city, String zipCode,
                                    String country, String state, String avsVbvsecurecode, String secretKey);
    }

}
