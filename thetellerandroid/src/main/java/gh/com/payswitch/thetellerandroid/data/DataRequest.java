package gh.com.payswitch.thetellerandroid.data;

import android.app.Activity;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.ghmobilemoney.ChargeRequestBody;

import java.util.List;

public interface DataRequest {

    interface NetworkRequest {
        void chargeCard(Payload payload, gh.com.payswitch.thetellerandroid.card.ChargeRequestBody chargeRequestBody, Callbacks.OnChargeRequestComplete callback);
        void chargeMomo(Payload payload, ChargeRequestBody chargeRequestBody, Callbacks.OnChargeRequestComplete callback);
    }

    interface SharedPrefsRequest {
        void saveCardDetsToSave(CardDetsToSave cardDetsToSave);
        CardDetsToSave retrieveCardDetsToSave();
        void saveACard(SavedCard card, String SECKEY, String email);
        List<SavedCard> getSavedCards(String email);
        void saveAPhone(SavedPhone phone, String SECKEY, String email);
        List<SavedPhone> getSavedGHMobileMoney(String phoneNumber);
    }
}
