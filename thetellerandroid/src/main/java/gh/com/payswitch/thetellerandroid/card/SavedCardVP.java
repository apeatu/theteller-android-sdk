package gh.com.payswitch.thetellerandroid.card;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import gh.com.payswitch.thetellerandroid.CardOrNumberActivity;
import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.R;
import gh.com.payswitch.thetellerandroid.Utils;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.NetworkRequestImpl;
import gh.com.payswitch.thetellerandroid.ghmobilemoney.ChargeRequestBody;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.thetellerActivity;

import static android.view.View.GONE;

public class SavedCardVP {

    private ProgressDialog progressDialog;
    private ProgressDialog pollingProgressDialog ;
    WebView webView;

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

    public void onPaymentSuccessful(String status, String responseAsString, Activity activity) {

        Intent intent = new Intent();
        intent.putExtra("response", responseAsString);

        if (activity != null) {
            activity.setResult(thetellerActivity.RESULT_SUCCESS, intent);
            activity.finish();
        }
    }

    public void onVBVAuthModelUsed(String authUrlCrude, View v) {
        webView = (WebView) v.findViewById(R.id.theteller_webview2);

        if (webView != null){
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            // Configure the client to use when opening URLs
            webView.setWebViewClient(new MyBrowser());
            // Load the initial URL
            webView.loadUrl(authUrlCrude);
        }else {
            Log.wtf("webView","web view is still null");
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

    public void chargeCard(final Payload payload, final String secretKey, final Activity activity, final View v) {

        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, secretKey).trim().replaceAll("\\n", "");

        Log.d("encrypted", encryptedCardRequestBody);

        gh.com.payswitch.thetellerandroid.card.ChargeRequestBody body = new gh.com.payswitch.thetellerandroid.card.ChargeRequestBody();
        body.setClient(payload.getAmount(), "000000", payload.getTxRef(), payload.getNarration(), payload.getMerchant_id(), payload.getCardno(),
                payload.get3dUrl(), payload.getExpirymonth(), payload.getExpiryyear(), payload.getCvv(), payload.getCurrency(), payload.getFirstname()+" "+payload.getLastname(),
                payload.getEmail(), payload.getCardno(), payload.getNetwork());

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
                    }else if(Integer.parseInt(code) == 200) {
                        String vbvUrl = response.getReason();
                        onVBVAuthModelUsed(vbvUrl, v);
                    } else {
                        showProgressIndicator(false, activity);
                        onPaymentFailed(status, responseAsJSONString, activity);
                    }

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

    public void chargeMomo(final Payload payload, final String secretKey, final Activity activity, final View v) {

        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, secretKey).trim().replaceAll("\\n", "");

        Log.d("encrypted", encryptedCardRequestBody);

        ChargeRequestBody body = new ChargeRequestBody();
//        body.setApiKey(payload.getApiKey());
//        body.setClient(encryptedCardRequestBody);
        body.setClient(payload.getAmount(), "000200", payload.getTxRef(), payload.getNarration(), payload.getMerchant_id(), payload.getPhonenumber(), payload.getNetwork(), payload.getVoucherCode());

        showProgressIndicator(true, activity);

        NetworkRequestImpl networkRequestImpl = new NetworkRequestImpl();
        networkRequestImpl.setBaseUrl(CardOrNumberActivity.BASE_URL);
        networkRequestImpl.chargeMomo(body, new Callbacks.OnChargeRequestComplete() {
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
                    }else if(Integer.parseInt(code) == 200) {
                        String vbvUrl = response.getReason();
                        onVBVAuthModelUsed(vbvUrl, v);
                    } else {
                        showProgressIndicator(false, activity);
                        onPaymentFailed(status, responseAsJSONString, activity);
                    }

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
    // Manages the behavior when URLs are loaded
    public class MyBrowser extends WebViewClient {
        FrameLayout progressContainer;

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.loadUrl(request.getUrl().toString());
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            showFullProgressIndicator(true, view.getRootView());

        }

        public void showFullProgressIndicator(boolean active, View v) {

            progressContainer = (FrameLayout) v.findViewById(R.id.theteller_progressContainer);

            if (progressContainer == null) {
                progressContainer = (FrameLayout) v.findViewById(R.id.theteller_progressContainer);
            }

            if (active) {
                progressContainer.setVisibility(View.VISIBLE);
            }
            else {
                progressContainer.setVisibility(GONE);
            }


        }
    }
}
