package gh.com.payswitch.thetellerandroid.card;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.data.SavedCard;

import java.util.List;

public interface CardContract {

    interface View {
        void showProgressIndicator(boolean active);
        void onPaymentError(String message);
        void showToast(String message);
        void onVBVAuthModelUsed(String authUrlCrude, String responseAsJSONString, String txRef);
        void onPaymentSuccessful(String status, String responseAsString);
        void onPaymentFailed(String status, String responseAsString);
        void showSavedCards(List<SavedCard> cards);
}

    interface UserActionsListener {
        void chargeCard(Payload payload, String secretKey);
        void savePotentialCardDets(String cardFirst6, String cardLast4);
        void onSavedCardsClicked(String email);
        void saveThisCard(String email, String apikey, String cardNo, String expiryMonth, String expiryYear, String cardType);
        List<SavedCard> checkForSavedCards(String email);
        void onAttachView(CardContract.View view);
        void onDetachView();
    }
}
