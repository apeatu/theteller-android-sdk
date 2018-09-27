package gh.com.payswitch.thetellerandroid.card;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import gh.com.payswitch.thetellerandroid.CardOrNumberActivity;
import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.Utils;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.NetworkRequestImpl;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.thetellerActivity;

public class SavedCardVP {

    private ProgressDialog progressDialog;
    private ProgressDialog pollingProgressDialog ;

    public void showProgressIndicator(boolean active, Activity activity) {

        if (activity.isFinishing()) { return; }
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage("Please wait...");
        }

        if (active && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        else {
            progressDialog.dismiss();
        }
    }
    public void showPollingIndicator(boolean active, Activity activity) {
        if (activity.isFinishing()) { return; }

        if(pollingProgressDialog == null) {
            pollingProgressDialog = new ProgressDialog(activity);
            pollingProgressDialog.setMessage("Checking transaction status. \nPlease wait");
        }

        if (active && !pollingProgressDialog.isShowing()) {
            pollingProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    pollingProgressDialog.dismiss();
                }
            });

            pollingProgressDialog.show();
        }
        else if (active && pollingProgressDialog.isShowing()) {
            //pass
        }
        else {
            pollingProgressDialog.dismiss();
        }
    }


    public void onPaymentError(String message, Activity activity) {
//        dismissDialog();
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }


//    @Override
//    public void onChargePhoneNumberSuccessful(ChargeResponse response) {
//        presenter
//                .requeryTx(response.getData().getFlwRef(),
//                        thetellerInitializer.getApiKey(),
//                        shouldISaveThisPhoneNumber);
//    }

    public void onPaymentSuccessful(String status, String responseAsString, Activity activity) {

        Intent intent = new Intent();
        intent.putExtra("response", responseAsString);

        if (activity != null) {
            activity.setResult(thetellerActivity.RESULT_SUCCESS, intent);
            activity.finish();
        }
    }

    public void onPaymentFailed(String message, String responseAsJSONString, Activity activity) {
        Intent intent = new Intent();
        intent.putExtra("response", responseAsJSONString);
        if (activity != null) {
            activity.setResult(thetellerActivity.RESULT_ERROR, intent);
            activity.finish();
        }
    }

    public void chargeCard(final Payload payload, final String secretKey, final Activity activity) {

        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, secretKey).trim().replaceAll("\\n", "");

        Log.d("encrypted", encryptedCardRequestBody);

        ChargeRequestBody body = new ChargeRequestBody();
//        body.setAlg("3DES-24");
        body.setApiKey(payload.getApiKey());
        body.setClient(encryptedCardRequestBody);

        showProgressIndicator(true, activity);

        NetworkRequestImpl networkRequestImpl = new NetworkRequestImpl();
        networkRequestImpl.setBaseUrl(CardOrNumberActivity.BASE_URL);
        networkRequestImpl.chargeCard(body, new Callbacks.OnChargeRequestComplete() {
            @Override
            public void onSuccess(ChargeResponse response, String responseAsJSONString) {

                showProgressIndicator(false, activity);

                if (response != null) {

                    Log.d("resp", responseAsJSONString);

                    String status = response.getStatus();
                    String code = response.getCode();
                    String reason = response.getReason();
                    String txRef = response.getTxRef();

                    if (code.equals("000")) {
                        onPaymentSuccessful(code, responseAsJSONString, activity);
                    } else {
                        showProgressIndicator(false, activity);
                        onPaymentFailed(status, responseAsJSONString, activity);
                    }

//                    if (response.getData().getSuggested_auth() != null) {
//                        String suggested_auth = response.getData().getSuggested_auth();
//
//
//                        if (suggested_auth.equals(thetellerConstants.PIN)) {
//                            mView.onPinAuthModelSuggested(payload);
//                        }
//                        else if (suggested_auth.equals(AVS_VBVSECURECODE)) { //address verification then verification by visa
//                            mView.onAVS_VBVSECURECODEModelSuggested(payload);
//                        }
//                        else if (suggested_auth.equalsIgnoreCase(thetellerConstants.NOAUTH_INTERNATIONAL)) {
//                            mView.onNoAuthInternationalSuggested(payload);
//                        }
//                        else {
//                            mView.onPaymentError("Unknown auth model");
//                        }
//                    }
//                    else {
//                        String authModelUsed = response.getData().getAuthModelUsed();
//
//                        if (authModelUsed != null) {
//
//                            if (authModelUsed.equalsIgnoreCase(thetellerConstants.VBV)) {
//                                String authUrlCrude = response.getData().getAuthurl();
//                                String flwRef = response.getData().getFlwRef();
//
//                                mView.onVBVAuthModelUsed(authUrlCrude, flwRef);
//                            }
//                            else if (authModelUsed.equalsIgnoreCase(thetellerConstants.GTB_OTP)) {
//                                String flwRef = response.getData().getFlwRef();
//                                String chargeResponseMessage = response.getData().getChargeResponseMessage();
//                                chargeResponseMessage = chargeResponseMessage == null ? "Enter your one  time password (OTP)" : chargeResponseMessage;
//                                mView.showOTPLayout(flwRef, chargeResponseMessage);
//                            }
//                            else if (authModelUsed.equalsIgnoreCase(thetellerConstants.NOAUTH)) {
//                                String flwRef = response.getData().getFlwRef();
//
//                                mView.onNoAuthUsed(flwRef, secretKey);
//                            }
//                        }
//                    }
                } else {
                    onPaymentError("No response data was returned", activity);
                }

            }

            public void onError(String message, String responseAsJSONString) {
                showProgressIndicator(false, activity);
                onPaymentError(message, activity);
            }
        });
    }
}
