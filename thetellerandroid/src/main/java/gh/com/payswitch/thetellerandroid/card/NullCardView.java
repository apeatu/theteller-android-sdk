package gh.com.payswitch.thetellerandroid.card;

import android.support.v4.app.Fragment;
import android.view.View;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.data.SavedCard;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponse;

import java.util.List;

public class NullCardView extends Fragment implements View.OnClickListener, CardContract.View {

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgressIndicator(boolean active) {

    }

    @Override
    public void onNoAuthUsed(String flwRef, String secretKey) {

    }

    @Override
    public void onNoAuthInternationalSuggested(Payload payload) {

    }

    @Override
    public void onPaymentError(String message) {

    }

    @Override
    public void onPinAuthModelSuggested(Payload payload) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showOTPLayout(String flwRef, String chargeResponseMessage) {

    }

//    @Override
//    public void onValidateSuccessful(String message, String responseAsString) {
//
//    }
//
//    @Override
//    public void onValidateError(String message) {
//
//    }

    @Override
    public void onVBVAuthModelUsed(String authUrlCrude) {

    }

    @Override
    public void onPaymentSuccessful(String status, String responseAsString) {

    }

    @Override
    public void onPaymentFailed(String status, String responseAsString) {

    }

    @Override
    public void showFullProgressIndicator(boolean active) {

    }

    @Override
    public void showSavedCards(List<SavedCard> cards) {

    }

//    @Override
//    public void onTokenRetrieved(String flwRef, String cardBIN, String token) {
//
//    }
//
//    @Override
//    public void onTokenRetrievalError(String s) {
//
//    }
//
//    @Override
//    public void displayFee(String charge_amount, Payload payload, int why) {
//
//    }

    @Override
    public void showFetchFeeFailed(String s) {

    }

    @Override
    public void hideSavedCardsButton() {

    }

//    @Override
//    public void onChargeTokenComplete(ChargeResponse response) {
//
//    }
//
//    @Override
//    public void onChargeCardSuccessful(ChargeResponse response) {
//
//    }

    @Override
    public void onAVS_VBVSECURECODEModelSuggested(Payload payload) {

    }

    @Override
    public void onAVSVBVSecureCodeModelUsed(String authurl, String flwRef) {

    }

//    @Override
//    public void onValidateCardChargeFailed(String flwRef, String responseAsJSON) {
//
//    }
//
//    @Override
//    public void onRequerySuccessful(RequeryResponse response, String responseAsJSONString, String flwRef) {
//
//    }
}
