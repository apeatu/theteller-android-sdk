package gh.com.payswitch.theteller_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gh.com.payswitch.thetellerandroid.Meta;
import gh.com.payswitch.thetellerandroid.card.CardFragment;
import gh.com.payswitch.thetellerandroid.card.CardPresenter;
import gh.com.payswitch.thetellerandroid.data.SavedCard;
import gh.com.payswitch.thetellerandroid.data.SavedPhone;
import gh.com.payswitch.thetellerandroid.ghmobilemoney.GhMobileMoneyFragment;
import gh.com.payswitch.thetellerandroid.ghmobilemoney.GhMobileMoneyPresenter;
import gh.com.payswitch.thetellerandroid.thetellerConstants;
import gh.com.payswitch.thetellerandroid.thetellerActivity;
import gh.com.payswitch.thetellerandroid.thetellerManager;
import gh.com.payswitch.thetellerandroid.Utils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GhMobileMoneyPresenter ghMobileMoneyPresenter;
    CardPresenter cardPresenter;
    ProgressDialog progressDialog;
    EditText emailEt;
    EditText amountEt;
    EditText apiKeyEt;
    EditText txRefEt;
    EditText redirectUrlEt;
    EditText narrationEt;
    EditText currencyEt;
    EditText merchantIdEt;
    EditText apiUserEt;
    EditText dUrlEt;
    EditText fNameEt;
    EditText lNameEt;
    EditText voucherCode;
    Button startPayBtn;
    SwitchCompat cardSwitch;
    SwitchCompat ghMobileMoneySwitch;
    SwitchCompat isLiveSwitch;
    List<Meta> meta = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt = findViewById(R.id.emailEt);
        amountEt = findViewById(R.id.amountEt);
        apiKeyEt = findViewById(R.id.apiKeyEt);
        txRefEt = findViewById(R.id.txRefEt);
        merchantIdEt = findViewById(R.id.merchant_idEt);
        apiUserEt = findViewById(R.id.api_userEt);
        dUrlEt = findViewById(R.id.d_urlEt);
        narrationEt = findViewById(R.id.narrationTV);
        currencyEt = findViewById(R.id.currencyEt);
        fNameEt = findViewById(R.id.fNameEt);
        lNameEt = findViewById(R.id.lnameEt);
//        voucherCode = findViewById(R.id.voucherCodeEt);
        startPayBtn = findViewById(R.id.startPaymentBtn);
        cardSwitch = findViewById(R.id.cardPaymentSwitch);
        ghMobileMoneySwitch = findViewById(R.id.accountGHMobileMoneySwitch);
        isLiveSwitch = findViewById(R.id.isLiveSwitch);

        apiKeyEt.setText(thetellerConstants.API_KEY);

        meta.add(new Meta("test key 1", "test value 1"));
        meta.add(new Meta("test key 2", "test value 2"));

        startPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressIndicator(true);
                validateEntries();
                showProgressIndicator(false);
            }
        });


    }

    public void showProgressIndicator(boolean active) {

        if (this.isFinishing()) { return; }
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
        }

        if (active && !progressDialog.isShowing()) {
            progressDialog.show();
        }
        else {
            progressDialog.dismiss();
        }
    }

    private void validateEntries() {

        clearErrors();
        String email = emailEt.getText().toString();
        String amount = amountEt.getText().toString();
        String apiKey = apiKeyEt.getText().toString();
        String txRef = txRefEt.getText().toString();
        String merchantId = merchantIdEt.getText().toString();
        String apiUser = apiUserEt.getText().toString();
        String dUrl = dUrlEt.getText().toString();
        String narration = narrationEt.getText().toString();
        String currency = currencyEt.getText().toString();
        String fName = fNameEt.getText().toString();
        String lName = lNameEt.getText().toString();



        boolean valid = true;

        if (amount.length() == 0) {
            amount = "0";
        }

        //check for compulsory fields
        if (!Utils.isEmailValid(email)) {
            valid = false;
            emailEt.setError("A valid email is required");
        }

        if (apiKey.length() < 1){
            valid = false;
            apiKeyEt.setError("A valid public key is required");
        }

        if (txRef.length() < 1){
            valid = false;
            txRefEt.setError("A valid txRef key is required");
        }

        if (currency.length() < 1){
            valid = false;
            currencyEt.setError("A valid currency code is required");
        }

        if (valid) {
            ghMobileMoneyPresenter = new GhMobileMoneyPresenter(this, new GhMobileMoneyFragment());
            cardPresenter = new CardPresenter(this, new CardFragment());
            List<SavedPhone> checkForSavedMobileMoney = ghMobileMoneyPresenter.checkForSavedGHMobileMoney(email);
            List<SavedCard> checkForSavedCards = cardPresenter.checkForSavedCards(email);

            if (checkForSavedCards.isEmpty() && checkForSavedMobileMoney.isEmpty()){
                new thetellerManager(this).setAmount(Long.parseLong(amount))
                    .setEmail(email)
                    .setfName(fName)
                    .setlName(lName)
                    .setMerchant_id(merchantId)
                    .setNarration(narration)
                    .setApiUser(apiUser)
                    .setApiKey(apiKey)
                    .setTxRef(txRef)
                    .set3dUrl(dUrl)
                    .acceptCardPayments(cardSwitch.isChecked())
                    .acceptGHMobileMoneyPayments(ghMobileMoneySwitch.isChecked())
                    .onStagingEnv(!isLiveSwitch.isChecked())
                    .initialize();
            }else {
                new thetellerManager(this).setAmount(Long.parseLong(amount))
                    .setEmail(email)
                    .setfName(fName)
                    .setlName(lName)
                    .setMerchant_id(merchantId)
                    .setNarration(narration)
                    .setApiUser(apiUser)
                    .setApiKey(apiKey)
                    .setTxRef(txRef)
                    .set3dUrl(dUrl)
                    .acceptCardPayments(cardSwitch.isChecked())
                    .acceptGHMobileMoneyPayments(ghMobileMoneySwitch.isChecked())
                    .onStagingEnv(!isLiveSwitch.isChecked())
                    .chooseCardOrNumber();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == thetellerConstants.theteller_REQUEST_CODE && data != null) {

            String message = data.getStringExtra("response");

            if (message != null) {
                Log.d("theteller response", message);
            }

            if (resultCode == thetellerActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "SUCCESS " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == thetellerActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR " + message, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == thetellerActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED " + message, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void clearErrors() {
        emailEt.setError(null);
        amountEt.setError(null);
        apiKeyEt.setError(null);
        txRefEt.setError(null);
        narrationEt.setError(null);
        currencyEt.setError(null);
        fNameEt.setError(null);
        lNameEt.setError(null);
    }

}
