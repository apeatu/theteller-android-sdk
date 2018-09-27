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
    private String txRef;
    private String narration = "";
    private String currency = "GHS";
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

    public thetellerManager setfName(String fName) {
        this.fName = fName;
        return this;
    }

    public thetellerManager setlName(String lName) {
        this.lName = lName;
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
        return new thetellerInitializer(email, amount, apiKey, txRef, narration, currency, fName, lName, withCard, withGHMobileMoney, theme, staging, allowSaveCard, meta, payment_plan);
    }
}