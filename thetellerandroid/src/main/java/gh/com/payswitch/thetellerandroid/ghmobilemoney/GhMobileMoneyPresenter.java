package gh.com.payswitch.thetellerandroid.ghmobilemoney;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Collections;
import java.util.List;

import gh.com.payswitch.thetellerandroid.FeeCheckRequestBody;
import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.data.SavedPhone;
import gh.com.payswitch.thetellerandroid.data.SharedPrefsRequestImpl;
import gh.com.payswitch.thetellerandroid.thetellerConstants;
import gh.com.payswitch.thetellerandroid.Utils;
import gh.com.payswitch.thetellerandroid.card.ChargeRequestBody;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.NetworkRequestImpl;
import gh.com.payswitch.thetellerandroid.data.RequeryRequestBody;
import gh.com.payswitch.thetellerandroid.data.RequeryRequestBodyv2;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.responses.FeeCheckResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponse;
import gh.com.payswitch.thetellerandroid.responses.RequeryResponsev2;
import gh.com.payswitch.thetellerandroid.thetellerInitializer;

import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller;

public class GhMobileMoneyPresenter implements GhMobileMoneyContract.UserActionsListener {
    private Context context;
    private GhMobileMoneyContract.View mView;

    public GhMobileMoneyPresenter(Context context, GhMobileMoneyContract.View mView) {
        this.context = context;
        this.mView = mView;
    }

//    @Override
//    public void fetchFee(final Payload payload) {
//        FeeCheckRequestBody body = new FeeCheckRequestBody();
//        body.setAmount(payload.getAmount());
//        body.setCurrency(payload.getCurrency());
//        body.setPtype("3");
//        body.setPBFPubKey(payload.getApiKey());
//
//        mView.showProgressIndicator(true);
//
//        new NetworkRequestImpl().getFee(body, new Callbacks.OnGetFeeRequestComplete() {
//            @Override
//            public void onSuccess(FeeCheckResponse response) {
//                mView.showProgressIndicator(false);
//
//                try {
//                    mView.displayFee(response.getData().getCharge_amount(), payload);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                    mView.showFetchFeeFailed("An error occurred while retrieving transaction fee");
//                }
//            }
//
//            @Override
//            public void onError(String message) {
//                mView.showProgressIndicator(false);
//                Log.e(thetellerConstants.theteller, message);
//                mView.showFetchFeeFailed("An error occurred while retrieving transaction fee");
//            }
//        });
//    }

    @Override
    public void chargeGhMobileMoney(final Payload payload, final String apiKey) {
        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, apiKey).trim().replaceAll("\\n", "");

        Log.d("encrypted", encryptedCardRequestBody);

        ChargeRequestBody body = new ChargeRequestBody();
//        body.setAlg("3DES-24");
        body.setApiKey(payload.getApiKey());
        body.setClient(encryptedCardRequestBody);

        mView.showProgressIndicator(true);

        new NetworkRequestImpl().chargeCard(body, new Callbacks.OnChargeRequestComplete() {
            @Override
            public void onSuccess(ChargeResponse response, String responseAsJSONString) {

                mView.showPollingIndicator(false);
                mView.showProgressIndicator(false);

                if (response != null) {
                    Log.d("resp", responseAsJSONString);

                    String status = response.getStatus();
                    String code = response.getCode();
                    String reason = response.getReason();
                    String txRef = response.getTxRef();

                    if (code.equals("000")) {
                        mView.onPaymentSuccessful(code, responseAsJSONString);
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

//    @Override
//    public void requeryTxv2(final String flwRef, final String txRef, final String apiKey) {
//
//        RequeryRequestBodyv2 body = new RequeryRequestBodyv2();
//        body.setTxref(txRef);
//        body.setSECKEY(apiKey);
//
//        mView.showPollingIndicator(true);
//
//        new NetworkRequestImpl().requeryTxv2(body, new Callbacks.OnRequeryRequestv2Complete() {
//            @Override
//            public void onSuccess(RequeryResponsev2 response, String responseAsJSONString) {
//                if (response.getData() == null) {
//                    mView.onPaymentFailed(response.getStatus(), responseAsJSONString);
//                }
//                else if (response.getData().getChargecode().equals("02")){
//                    mView.onPollingRoundComplete(flwRef, txRef, apiKey);
//                }
//                else if (response.getData().getChargecode().equals("00")) {
//                    requeryTx(flwRef, apiKey, true);
//                }
//                else {
//                    mView.showProgressIndicator(false);
//                    mView.onPaymentFailed(response.getData().getStatus(), responseAsJSONString);
//                }
//            }
//
//            @Override
//            public void onError(String message, String responseAsJSONString) {
//                mView.onPaymentFailed(message, responseAsJSONString);
//            }
//        });
//
//    }

//    public void queryTx(String txRef, String apiKey) {
//        mView.showPollingIndicator(false);
//        mView.showProgressIndicator(true);
//
//        new NetworkRequestImpl().requeryTx(body, new Callbacks.OnRequeryRequestComplete() {
//            @Override
//            public void onSuccess(RequeryResponse response, String responseAsJSONString) {
//                Log.d("response", response.getData().toString());
//                mView.showProgressIndicator(false);
//                mView.onPaymentSuccessful(response.getData().getStatus(), flwRef, responseAsJSONString);
//            }
//
//            @Override
//            public void onError(String message, String responseAsJSONString) {
//                mView.showProgressIndicator(false);
//                mView.onPaymentSuccessful(message, flwRef, responseAsJSONString);
//            }
//        });
//    }

//    public void requeryTx(final String flwRef, final String SECKEY, final boolean shouldISaveNumber) {
//
//        RequeryRequestBody body = new RequeryRequestBody();
//        body.setFlw_ref(flwRef);
//        body.setSECKEY(SECKEY);
//
//        mView.showPollingIndicator(false);
//        mView.showProgressIndicator(true);
//
//        new NetworkRequestImpl().requeryTx(body, new Callbacks.OnRequeryRequestComplete() {
//            @Override
//            public void onSuccess(RequeryResponse response, String responseAsJSONString) {
//                Log.d("response", response.getData().toString());
//                mView.showProgressIndicator(false);
//                mView.onPaymentSuccessful(response.getData().getStatus(), flwRef, responseAsJSONString);
//            }
//
//            @Override
//            public void onError(String message, String responseAsJSONString) {
//                mView.showProgressIndicator(false);
//                mView.onPaymentSuccessful(message, flwRef, responseAsJSONString);
//            }
//        });
//    }


//    @Override
//    public void verifyRequeryResponse(RequeryResponse response, String responseAsJSONString, thetellerInitializer ravePayInitializer, String flwRef) {
//        mView.showProgressIndicator(true);
//        boolean wasTxSuccessful = Utils.wasTxSuccessful(ravePayInitializer, responseAsJSONString);
//        mView.showFullProgressIndicator(false);
//
//        if (wasTxSuccessful) {
//            mView.onPaymentSuccessful(response.getStatus(), flwRef, responseAsJSONString);
//        }
//        else {
//            mView.onPaymentFailed(response.getStatus(), responseAsJSONString);
//        }
//    }

    @Override
    public void onSavedPhonesClicked(String email) {

        SharedPrefsRequestImpl sharedMgr = new SharedPrefsRequestImpl(context);

        List<SavedPhone> phoneList = sharedMgr.getSavedGHMobileMoney(email);

        mView.showSavedPhoneList(phoneList);
    }

    @Override
    public List<SavedPhone> checkForSavedGHMobileMoney(String email) {
        SharedPrefsRequestImpl sharedMgr = new SharedPrefsRequestImpl(context);

        List<SavedPhone> savedGHMobileMoney = sharedMgr.getSavedGHMobileMoney(email);

        if (savedGHMobileMoney.size() == 0) {
            return Collections.emptyList();
        }else {
            return savedGHMobileMoney;
        }

    }

    @Override
    public void saveThisPhone(String email, String secretKey, String phoneNumber, String network) {
        SharedPrefsRequestImpl sharedPrefsRequest = new SharedPrefsRequestImpl(context);

        try {
            SavedPhone phone = new SavedPhone();
            phone.setPhoneNumber(phoneNumber);
            phone.setNetwork(network);
            sharedPrefsRequest.saveAPhone(phone, secretKey, email);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}


