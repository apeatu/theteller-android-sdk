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

import com.google.gson.Gson;

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
import static gh.com.payswitch.thetellerandroid.card.CreditCardView.cardType;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_results;

public class SavedCardVP {

    private ProgressDialog progressDialog;
    private ProgressDialog pollingProgressDialog ;
    WebView webView;
    String initialUrl = null;

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

    public void onVBVAuthModelUsed(String authUrlCrude, Activity v, String txRef) {
        FrameLayout webViewContainer;

        if (v.findViewById(R.id.theteller_webview) == null) {
            webViewContainer = (FrameLayout) v.findViewById(R.id.webView_container);
            webViewContainer.setVisibility(View.VISIBLE);
            webView = (WebView) v.findViewById(R.id.theteller_webview2);
        }else {
            webViewContainer = (FrameLayout) v.findViewById(R.id.webView_container);
            webViewContainer.setVisibility(View.VISIBLE);
            webView = (WebView) v.findViewById(R.id.theteller_webview3);
        }

        if (webView != null){
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            // Configure the client to use when opening URLs
            webView.setWebViewClient(new MyBrowser(v, txRef));
            // Load the initial URL
            webView.loadUrl(authUrlCrude);
        }else {
            Log.wtf("webView","web view is still "+webView);
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

    public void chargeCard(final Payload payload, final String secretKey, final Activity activity) {

        String cardRequestBodyAsString = Utils.convertChargeRequestPayloadToJson(payload);
        String encryptedCardRequestBody = Utils.getEncryptedData(cardRequestBodyAsString, secretKey).trim().replaceAll("\\n", "");

        Log.d("encrypted", encryptedCardRequestBody);

        gh.com.payswitch.thetellerandroid.card.ChargeRequestBody body = new gh.com.payswitch.thetellerandroid.card.ChargeRequestBody();

        body.setClient(Utils.minorUnitAmount(payload.getAmount()), "000000", payload.getTxRef(), payload.getNarration(), payload.getMerchant_id(), payload.getCardno(),
                payload.get3dUrl(), payload.getExpirymonth(), payload.getExpiryyear(), payload.getCvv(), payload.getCurrency(), payload.getFirstname()+" "+payload.getLastname(),
                payload.getEmail(), payload.getPhonenumber(), payload.getCardType());

        showProgressIndicator(true, activity);

        NetworkRequestImpl networkRequestImpl = new NetworkRequestImpl();
        networkRequestImpl.setBaseUrl(CardOrNumberActivity.BASE_URL);
        networkRequestImpl.chargeCard(payload, body, new Callbacks.OnChargeRequestComplete() {
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
                        onVBVAuthModelUsed(vbvUrl, activity, txRef);
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
        String responseAsJString;
        Activity activity;
        String txRef;

        MyBrowser(Activity activity, String txRef) {
            this.activity = activity;
            this.txRef = txRef;
        }

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
            Toast.makeText(activity, "Please Wait", Toast.LENGTH_LONG).show();
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (initialUrl == null) {
                initialUrl = url;
            } else {
                if (url.contains("code=000")) {
                    ChargeResponse chargeResponse = new ChargeResponse();
                    chargeResponse.setCode("000");
                    chargeResponse.setStatus("approved");
                    chargeResponse.setReason("Transaction successful!");
                    chargeResponse.setTxRef(txRef);
                    if (activity != null) {
                        activity.finish();
                    }
                    Gson gson = new Gson();
                    responseAsJString = gson.toJson(chargeResponse);
                    Intent intent = new Intent();
                    intent.putExtra("response", responseAsJString);
                    theteller_results = responseAsJString;
                    Log.wtf("response", responseAsJString);

                    if (activity != null) {
                        activity.setResult(thetellerActivity.RESULT_SUCCESS, intent);
                        activity.finish();
                    }

                }
                if (url.contains("code=100&status=Declined")) {
                    ChargeResponse chargeResponse = new ChargeResponse();
                    chargeResponse.setCode("100");
                    chargeResponse.setStatus("Declined");
                    chargeResponse.setReason("Transaction failed!");
                    chargeResponse.setTxRef(txRef);
                    if (activity != null) {
                        activity.finish();
                    }
                    Gson gson = new Gson();
                    responseAsJString = gson.toJson(chargeResponse);
                    Intent intent = new Intent();
                    intent.putExtra("response", responseAsJString);
                    theteller_results = responseAsJString;
                    Log.wtf("response", responseAsJString);

                    if (activity != null) {
                        activity.setResult(thetellerActivity.RESULT_SUCCESS, intent);
                        activity.finish();
                    }

                }
            }
            Log.d("URLS", url);
        }

    }

}
