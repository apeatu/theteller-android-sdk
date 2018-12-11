package gh.com.payswitch.thetellerandroid.data;

import gh.com.payswitch.thetellerandroid.ghmobilemoney.ChargeRequestBody;

import java.util.List;

public interface DataRequest {

    interface NetworkRequest {
        void chargeCard(gh.com.payswitch.thetellerandroid.card.ChargeRequestBody chargeRequestBody, Callbacks.OnChargeRequestComplete callback);
        void chargeMomo(ChargeRequestBody chargeRequestBody, Callbacks.OnChargeRequestComplete callback);
//        void validateChargeCard(ValidateChargeBody cardRequestBody, Callbacks.OnValidateChargeCardRequestComplete callback);
//        void validateAccountCard(ValidateChargeBody cardRequestBody, Callbacks.OnValidateChargeCardRequestComplete callback);
//        void queryTx(ChargeRequestBody chargeRequestBody, Callbacks.OnRequeryRequestComplete callback);
//        void requeryTx(RequeryRequestBody requeryRequestBody, Callbacks.OnRequeryRequestComplete callback);
//        void requeryTxv2(RequeryRequestBodyv2 requeryRequestBody, Callbacks.OnRequeryRequestv2Complete callback);
//        void getBanks(Callbacks.OnGetBanksRequestComplete callback);
//        void chargeAccount(ChargeRequestBody accountRequestBody, Callbacks.OnChargeRequestComplete callback);
//        void chargeToken(Payload payload, Callbacks.OnChargeRequestComplete callback);
//        void getFee(FeeCheckRequestBody body, Callbacks.OnGetFeeRequestComplete callback);
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
