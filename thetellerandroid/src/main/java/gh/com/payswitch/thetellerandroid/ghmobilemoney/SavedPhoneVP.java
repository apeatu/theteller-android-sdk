package gh.com.payswitch.thetellerandroid.ghmobilemoney;

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

import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_results;

public class SavedPhoneVP {

    private ProgressDialog progressDialog;
    private ProgressDialog pollingProgressDialog ;

    public void showProgressIndicator(boolean active, Activity activity) {

        if (activity.isFinishing()) { return; }
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(activity);
            progressDialog.setCancelable(false);
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


    public void onPaymentSuccessful(String status, String responseAsString, Activity activity) {

        Intent intent = new Intent();
        intent.putExtra("response", responseAsString);
        theteller_results = responseAsString;

        if (activity != null) {
            activity.setResult(thetellerActivity.RESULT_SUCCESS, intent);
            activity.finish();
        }
    }

    public void onPaymentFailed(String message, String responseAsJSONString, Activity activity) {
        Intent intent = new Intent();
        intent.putExtra("response", responseAsJSONString);
        theteller_results = responseAsJSONString;
        if (activity != null) {
            activity.setResult(thetellerActivity.RESULT_ERROR, intent);
            activity.finish();
        }
    }

    public void chargeGhMobileMoney(final Payload payload, final String apiKey, final Activity activity) {
        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, apiKey).trim().replaceAll("\\n", "");

        Log.d("encrypted", encryptedCardRequestBody);

        ChargeRequestBody body = new ChargeRequestBody();
        body.setClient(Utils.minorUnitAmount(payload.getAmount()), "000200", payload.getTxRef(), payload.getNarration(), payload.getMerchant_id(), payload.getPhonenumber(), payload.getNetwork(), payload.getVoucherCode());

        showProgressIndicator(true, activity);

        NetworkRequestImpl networkRequestImpl = new NetworkRequestImpl();
        networkRequestImpl.setBaseUrl(CardOrNumberActivity.BASE_URL);
        networkRequestImpl.chargeMomo(payload, body, new Callbacks.OnChargeRequestComplete() {
            @Override
            public void onSuccess(ChargeResponse response, String responseAsJSONString) {

                showPollingIndicator(false, activity);
                showProgressIndicator(false, activity);

                if (response != null) {
                    Log.d("resp", responseAsJSONString);

                    String status = response.getStatus();
                    String code = response.getCode();
                    String reason = response.getReason();
                    String txRef = response.getTxRef();

                    if (code.equals("000")) {
                        onPaymentSuccessful(code, responseAsJSONString, activity);
                    }
                    else {
                        showProgressIndicator(false, activity);
                        onPaymentFailed(status, responseAsJSONString, activity);
                    }
                }
                else {
                    onPaymentError("No response data was returned", activity);
                }

            }

            @Override
            public void onError(String message, String responseAsJSONString) {
                showProgressIndicator(false, activity);
                onPaymentError(message, activity);
            }
        });
    }
}
