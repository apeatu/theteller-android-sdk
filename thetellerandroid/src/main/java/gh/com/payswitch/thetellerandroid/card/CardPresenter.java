package gh.com.payswitch.thetellerandroid.card;

import android.app.Activity;
import android.content.Context;
import android.util.Log;


import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.R;
import gh.com.payswitch.thetellerandroid.Utils;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.CardDetsToSave;
import gh.com.payswitch.thetellerandroid.data.NetworkRequestImpl;
import gh.com.payswitch.thetellerandroid.data.SavedCard;
import gh.com.payswitch.thetellerandroid.data.SharedPrefsRequestImpl;
import gh.com.payswitch.thetellerandroid.card.ChargeRequestBody;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;

import java.util.Collections;
import java.util.List;

import static gh.com.payswitch.thetellerandroid.thetellerConstants.AVS_VBVSECURECODE;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.PIN;

public class CardPresenter implements CardContract.UserActionsListener {
    private Context context;
    private CardContract.View mView;

    public CardPresenter(Context context, CardContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

    @Override
    public void chargeCard(final Payload payload, final String secretKey) {

        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
//        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, secretKey).trim().replaceAll("\\n", "");


        ChargeRequestBody body = new ChargeRequestBody();
        body.setClient(Utils.minorUnitAmount(payload.getAmount()), "000000", payload.getTxRef(), payload.getNarration(), payload.getMerchant_id(), payload.getCardno(), payload.get3dUrl(), payload.getExpirymonth(), payload.getExpiryyear(), payload.getCvv(), payload.getCurrency(), payload.getFirstname()+" "+payload.getLastname(), payload.getEmail(), payload.getPhonenumber(), payload.getCardType());


        mView.showProgressIndicator(true);

        new NetworkRequestImpl().chargeCard(payload, body, new Callbacks.OnChargeRequestComplete() {
            @Override
            public void onSuccess(ChargeResponse response, String responseAsJSONString) {

                mView.showProgressIndicator(false);

                if (response != null) {

                    Log.d("resp", responseAsJSONString);

                    String status = response.getStatus();
                    String code = response.getCode();
                    String reason = response.getReason();
                    String txRef = response.getTxRef();

                    if (code.equals("000")) {
                        mView.onPaymentSuccessful(code, responseAsJSONString);
                    }else if(Integer.parseInt(code) == 200) {
                        String vbvUrl = response.getReason();
                        mView.onVBVAuthModelUsed(vbvUrl, responseAsJSONString, txRef);
                    }
                    else {
                        mView.showProgressIndicator(false);
                        mView.onPaymentFailed(status, responseAsJSONString);
                    }
                }
                else {
                    mView.onPaymentError("No response data was returned");
                }

            }

            @Override
            public void onError(String message, String responseAsJSONString) {
                mView.showProgressIndicator(false);
                mView.onPaymentError(message);
            }
        });
    }

    @Override
    public void savePotentialCardDets(String cardFirst6, String cardLast4) {
        new SharedPrefsRequestImpl(context).saveCardDetsToSave(new CardDetsToSave(cardFirst6, cardLast4));
    }

    @Override
    public void onSavedCardsClicked(String email) {

        SharedPrefsRequestImpl sharedMgr = new SharedPrefsRequestImpl(context);

        List<SavedCard> cards = sharedMgr.getSavedCards(email);

        mView.showSavedCards(cards);

    }

    @Override
    public void saveThisCard(String email, String apiKey, String cardNo, String month, String year, String cardType) {
        SharedPrefsRequestImpl sharedPrefsRequest = new SharedPrefsRequestImpl(context);
        CardDetsToSave cardDetsToSave = sharedPrefsRequest.retrieveCardDetsToSave();

        if (cardDetsToSave.getFirst6().length() == 6 && cardDetsToSave.getLast4().length() == 4) {
            try {
                SavedCard savedCard = new SavedCard();
                savedCard.setFirst6(cardDetsToSave.getFirst6());
                savedCard.setLast4(cardDetsToSave.getLast4());
                savedCard.setMaskedPan(cardDetsToSave.getFirst6(), cardDetsToSave.getLast4());
                savedCard.setPan(cardNo);
                savedCard.setExpiryMonth(month);
                savedCard.setExpiryYear(year);
//                savedCard.setCardType(cardType);
                switch (cardType){
                    case "VIS":
                        savedCard.setCardType(R.drawable.visa);
                        break;
                    case "MAS":
                        savedCard.setCardType(R.drawable.mastercard);
                        break;
                    case "VER":
                        savedCard.setCardType(R.drawable.verve);
                        break;
                    case "AME":
                        savedCard.setCardType(R.drawable.amex);
                        break;
                    default:
                        savedCard.setCardType(R.drawable.creditcard);
                        break;
                }
                sharedPrefsRequest.saveACard(savedCard, apiKey, email);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<SavedCard> checkForSavedCards(String email) {
        SharedPrefsRequestImpl sharedMgr = new SharedPrefsRequestImpl(context);

        List<SavedCard> cards = sharedMgr.getSavedCards(email);

        if (cards.size() == 0) {
            return Collections.emptyList();
        }else {
            return cards;
        }
    }


    @Override
    public void onDetachView() {
        this.mView = new NullCardView();
    }

    @Override
    public void onAttachView(CardContract.View view) {
        this.mView = view;
    }
}
