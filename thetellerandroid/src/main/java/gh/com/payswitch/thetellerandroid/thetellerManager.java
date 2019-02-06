package gh.com.payswitch.thetellerandroid;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import org.parceler.Parcels;

import java.util.List;

import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_PARAMS;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_REQUEST_CODE;

public class thetellerManager {
    private String email;
    private double amount = -1;
    private String apiKey;
    private String apiUser;
    private String txRef;
    private String narration = "";
    private String currency = "GHS";
    private String merchant_id;
    private String terminal_id;
    private String voucher_code = "";
    private String d_response_url;
    private String fName = "";
    private String lName = "";
    private String meta = "";
    private String payment_plan;
    private Activity activity;
    private boolean withCard = true;
    private boolean withGHMobileMoney = true;
    private int theme = R.style.DefaultTheme;
    private boolean staging = true;
    private boolean allowSaveCard = true;


    public thetellerManager allowSaveCardFeature(boolean allowSaveCard) {
        this.allowSaveCard = allowSaveCard;
        return this;
    }

    public thetellerManager onStagingEnv(boolean staging) {
        this.staging = staging;
        return this;
    }

    public thetellerManager withTheme(int theme) {
        this.theme = theme;
        return this;
    }

    public thetellerManager(Activity activity) {
        this.activity = activity;
    }

    public thetellerManager acceptCardPayments(boolean withCard) {
        this.withCard = withCard;
        return this;
    }

    public thetellerManager acceptGHMobileMoneyPayments(boolean withGHMobileMoney) {
        this.withGHMobileMoney = withGHMobileMoney;
        return this;
    }

    public thetellerManager setMeta(List<Meta> meta) {
        this.meta = Utils.stringifyMeta(meta);
        return this;
    }


    public thetellerManager setEmail(String email) {
        this.email = email;
        return this;
    }

    public thetellerManager setAmount(double amount) {
        if (amount != 0) {
            this.amount = amount;
        }
        return this;
    }

    public thetellerManager setApiKey(String publicKey) {
        this.apiKey = publicKey;
        return this;
    }
    public thetellerManager setApiUser(String username) {
        this.apiUser = username;
        return this;
    }

    public thetellerManager setTxRef(String txRef) {
        this.txRef = txRef;
        return this;
    }

    public thetellerManager setNarration(String narration) {
        this.narration = narration;
        return this;
    }

    public thetellerManager setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public thetellerManager setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
        return this;
    }

    public thetellerManager setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
        return this;
    }

    public thetellerManager setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
        return this;
    }

    public thetellerManager setfName(String fName) {
        this.fName = fName;
        return this;
    }

    public thetellerManager setlName(String lName) {
        this.lName = lName;
        return this;
    }

    public thetellerManager set3dUrl(String d_response_url) {
        this.d_response_url = d_response_url;
        return this;
    }

    public thetellerManager setPaymentPlan(String payment_plan) {
        this.payment_plan = payment_plan;
        return this;
    }

    public void initialize() {
        if (activity != null) {
            Intent intent = new Intent(activity, thetellerActivity.class);
            intent.putExtra(theteller_PARAMS, Parcels.wrap(createthetellerInitializer()));
            activity.startActivityForResult(intent, theteller_REQUEST_CODE) ;
        }
        else {
            Log.d(theteller, "Context is required!");
        }
    }

    public void initializeCardOrNumber() {
        if (activity != null) {
            Intent intent = new Intent(activity, thetellerActivity.class);
            intent.putExtra(theteller_PARAMS, Parcels.wrap(createthetellerInitializer()));
            intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
            activity.startActivity(intent) ;
        }
        else {
            Log.d(theteller, "Context is required!");
        }
    }

    public void chooseCardOrNumber() {
        if (activity != null) {
            Intent intent = new Intent(activity, CardOrNumberActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.putExtra(theteller_PARAMS, Parcels.wrap(createthetellerInitializer()));
            activity.startActivityForResult(intent, theteller_REQUEST_CODE) ;
        }
        else {
            Log.d(theteller, "Context is required!");
        }
    }

    public thetellerInitializer createthetellerInitializer() {
        return new thetellerInitializer(email, amount, apiKey, apiUser, txRef, narration, currency, merchant_id, terminal_id, voucher_code, fName, lName, withCard, withGHMobileMoney, d_response_url, theme, staging, allowSaveCard, meta, payment_plan);
    }
}