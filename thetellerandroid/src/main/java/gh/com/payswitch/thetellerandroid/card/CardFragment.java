package gh.com.payswitch.thetellerandroid.card;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Linkify.TransformFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import gh.com.payswitch.thetellerandroid.CVVFragment;
import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.PayloadBuilder;
import gh.com.payswitch.thetellerandroid.R;
import gh.com.payswitch.thetellerandroid.responses.ChargeResponse;
import gh.com.payswitch.thetellerandroid.thetellerConstants;
import gh.com.payswitch.thetellerandroid.thetellerActivity;
import gh.com.payswitch.thetellerandroid.thetellerInitializer;
import gh.com.payswitch.thetellerandroid.Utils;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.SavedCard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;

import static android.view.View.GONE;
import static gh.com.payswitch.thetellerandroid.card.CreditCardView.cardType;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_results;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment implements View.OnClickListener, CardContract.View {

    TextInputEditText cardNoTv;
    TextInputEditText cardExpiryTv;
    TextInputEditText cvvTv;
    TextInputLayout amountTil;
    TextInputLayout emailTil;
    TextInputLayout cardNoTil;
    TextInputLayout cardExpiryTil;
    TextInputLayout cvvTil;
    SwitchCompat saveCardSwitch;
    Button payButton;
    private ProgressDialog progessDialog ;
    CardPresenter presenter;
    BottomSheetBehavior bottomSheetBehaviorOTP;
    BottomSheetBehavior bottomSheetBehaviorVBV;
    private String flwRef;
    private FrameLayout vbvLayout;
    thetellerInitializer thetellerInitializer;
    WebView webView;
    String initialUrl = null;
    Payload body;
//    private TextView pcidss_tv;
    private AlertDialog dialog;
    FrameLayout progressContainer;
    View v;
    Button savedCardBtn;
    String cardFirst6;
    TextView otpInstructionsTv;
    String cardLast4;
    boolean shouldISaveThisCard = false;

    public CardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        presenter = new CardPresenter(getActivity(), this);
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_card, container, false);
        savedCardBtn = (Button) v.findViewById(R.id.theteller_savedCardButton);
//        amountEt = (TextInputEditText) v.findViewById(R.id.theteller_amountTV);
//        emailEt = (TextInputEditText) v.findViewById(R.id.theteller_emailTv);
        cardNoTv = (TextInputEditText) v.findViewById(R.id.theteller_cardNoTv);
        cardExpiryTv = (TextInputEditText) v.findViewById(R.id.theteller_cardExpiryTv);
        cvvTv = (TextInputEditText) v.findViewById(R.id.theteller_cvvTv);
        payButton = (Button) v.findViewById(R.id.theteller_payButton);
        saveCardSwitch = (SwitchCompat) v.findViewById(R.id.theteller_saveCardSwitch);
//        amountTil = (TextInputLayout) v.findViewById(R.id.theteller_amountTil);
//        emailTil = (TextInputLayout) v.findViewById(R.id.theteller_emailTil);
        cardNoTil = (TextInputLayout) v.findViewById(R.id.theteller_cardNoTil);
        cardExpiryTil = (TextInputLayout) v.findViewById(R.id.theteller_cardExpiryTil);
        cvvTil = (TextInputLayout) v.findViewById(R.id.theteller_cvvTil);
        webView = (WebView) v.findViewById(R.id.theteller_webview);
//        pcidss_tv = (TextView) v.findViewById(R.id.theteller_pcidss_compliant_tv);
        progressContainer = (FrameLayout) v.findViewById(R.id.theteller_progressContainer);

        thetellerInitializer = ((thetellerActivity) getActivity()).getThetellerInitializer();

        TransformFilter filter = new TransformFilter() {
            public final String transformUrl(final Matcher match, String url) {
                return "";
            }
        };

//        Pattern pattern = Pattern.compile("()PCI-DSS COMPLIANT");
//        Linkify.addLinks(pcidss_tv, pattern, "https://www.pcisecuritystandards.org/pci_security/", null, filter);


        savedCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSavedCardsClicked(thetellerInitializer.getEmail());
            }
        });

        cardExpiryTv.addTextChangedListener(new ExpiryWatcher());

        payButton.setOnClickListener(this);

        vbvLayout = (FrameLayout) v.findViewById(R.id.theteller_VBVBottomSheet);
        bottomSheetBehaviorVBV = BottomSheetBehavior.from(vbvLayout);


        if (!thetellerInitializer.isAllowSaveCard()) {
            saveCardSwitch.setVisibility(GONE);
        }

        return v;
    }

    /**
     * Closes all open bottom sheets and returns true is bottom sheet is showing, else return false
     * @return
     */
    public boolean closeBottomSheetsIfOpen() {

        boolean showing = false;

        if (bottomSheetBehaviorVBV.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            showing = true;
            bottomSheetBehaviorVBV.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        return showing;
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.theteller_payButton) {
            validateDetails();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter = new CardPresenter(getActivity(), this);
        }
        assert presenter != null;
        presenter.onAttachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.onDetachView();
        }
    }

    /**
     *  Validate card details and get the fee if available
     */
    private void validateDetails() {

        clearErrors();
        Utils.hide_keyboard(getActivity());

        boolean valid = true;

        String cvv = cvvTv.getText().toString();
        String expiryDate = cardExpiryTv.getText().toString();
        String cardNo = cardNoTv.getText().toString();

        if (cvv.length() < 3) {
            valid = false;
            cvvTil.setError("Enter a valid cvv");
        }

        if (expiryDate.length() != 5) {
            cardExpiryTil.setError("Enter a valid expiry date");
            valid = false;
        }

        String cardNoStripped = cardNo.replaceAll("\\s", "");

        if (cardNoStripped.length() < 12 ) {
            valid = false;
            cardNoTil.setError("Enter a valid credit card number");
        }
        else {
            try {
                Long parsed = Long.parseLong(cardNoStripped);
            }
            catch (Exception e) {
                e.printStackTrace();
                valid = false;
                cardNoTil.setError("Enter a valid credit card number");
            }
        }

        if (valid) {

            if (saveCardSwitch.isChecked()) {
                int cardLen = cardNoStripped.length();
                cardFirst6 = cardNoStripped.substring(0, 6);
                cardLast4 = cardNoStripped.substring(cardLen - 4, cardLen);
                shouldISaveThisCard = true;
                presenter.savePotentialCardDets(cardFirst6, cardLast4);
            }

            //make request
            PayloadBuilder builder = new PayloadBuilder();
            builder.setAmount(thetellerInitializer.getAmount() + "")
                .setNarration(thetellerInitializer.getNarration())
                .setCardno(cardNoStripped)
                .set3dUrl(thetellerInitializer.get3dUrl())
                .setEmail(thetellerInitializer.getEmail())
                .setCurrency(thetellerInitializer.getCurrency())
                .setMerchant_id(thetellerInitializer.getMerchant_id())
                .setCvv(cvv).setFirstname(thetellerInitializer.getfName())
                .setLastname(thetellerInitializer.getlName())
                .setIP(Utils.getDeviceImei(getActivity()))
                .setTxRef(thetellerInitializer.getTxRef())
                .setExpiryyear(expiryDate.substring(3,5))
                .setExpirymonth(expiryDate.substring(0,2))
                .setMeta(thetellerInitializer.getMeta())
                .setApiUser(thetellerInitializer.getApiUser())
                .setApiKey(thetellerInitializer.getApiKey())
                .setDevice_fingerprint(Utils.getDeviceImei(getActivity()))
                .setCardType(cardType);

            if (thetellerInitializer.getPayment_plan() != null) {
                builder.setPaymentPlan(thetellerInitializer.getPayment_plan());
            }

            body = builder.createPayload();

            presenter.chargeCard(body, thetellerConstants.API_KEY);
        }
    }

    /**
     * Remove all errors from the input fields
     */
    private void clearErrors() {
        cvvTil.setError(null);
        cardExpiryTil.setError(null);
        cardNoTil.setError(null);

        cvvTil.setErrorEnabled(false);
        cardExpiryTil.setErrorEnabled(false);
        cardNoTil.setErrorEnabled(false);

    }

    /**
     * Show/Hide a progress dialog (general purpose)
     * @param active = status of progress indicator
     */
    @Override
    public void showProgressIndicator(boolean active) {

        try {
            if (getActivity().isFinishing()) {
                return;
            }
            if (progessDialog == null) {
                progessDialog = new ProgressDialog(getActivity());
                progessDialog.setCancelable(false);
                progessDialog.setMessage("Please wait...");
            }

            if (active && !progessDialog.isShowing()) {
                progessDialog.show();
            } else {
                progessDialog.dismiss();
            }
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentError(String message) {
        dismissDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void dismissDialog() {

        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onVBVAuthModelUsed(String authUrlCrude, String responseAsJSONString, String txRef) {
        FrameLayout webViewContainer;
        LinearLayout cardFragmentLL;

        webViewContainer = (FrameLayout) v.findViewById(R.id.theteller_VBVBottomSheet);
        cardFragmentLL = v.findViewById(R.id.theteller_topLay);
        cardFragmentLL.setVisibility(GONE);
        webViewContainer.setVisibility(View.VISIBLE);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        // Configure the client to use when opening URLs
        webView.setWebViewClient(new MyBrowser(txRef));
        // Load the initial URL
        webView.loadUrl(authUrlCrude);
        bottomSheetBehaviorVBV.setState(BottomSheetBehavior.STATE_EXPANDED);

    }

    @Override
    public void onPaymentSuccessful(String status, String responseAsJSONString) {
        dismissDialog();
        closeBottomSheetsIfOpen();

        if (shouldISaveThisCard && status.equals("000")) {
            presenter.saveThisCard(
                thetellerInitializer.getEmail(),
                thetellerInitializer.getApiKey(),
                cardNoTv.getText().toString(),
                cardExpiryTv.getText().toString().substring(0,2),
                cardExpiryTv.getText().toString().substring(3,5),
                cardType);
        }

        Intent intent = new Intent();
        intent.putExtra("response", responseAsJSONString);
        theteller_results = responseAsJSONString;

        if (getActivity() != null) {
            getActivity().setResult(thetellerActivity.RESULT_SUCCESS, intent);
            getActivity().finish();
        }
    }

    @Override
    public void onPaymentFailed(String status, String responseAsJSONString) {
        dismissDialog();
        bottomSheetBehaviorVBV.setState(BottomSheetBehavior.STATE_COLLAPSED);

        Intent intent = new Intent();
        intent.putExtra("response", responseAsJSONString);
        theteller_results = responseAsJSONString;

        if (getActivity() != null) {
            getActivity().setResult(thetellerActivity.RESULT_ERROR, intent);
            getActivity().finish();
        }
    }

    @Override
    public void showSavedCards(List<SavedCard> cards) {


        if (cards.size() > 0) {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.pick_saved_card_layout, null, false);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.theteller_recycler2);

            SavedCardRecyclerAdapter adapter = new SavedCardRecyclerAdapter();
            adapter.set(cards);

            adapter.setSavedCardSelectedListener(new Callbacks.SavedCardSelectedListener() {
                @Override
                public void onCardSelected(SavedCard savedCard) {
                    bottomSheetDialog.dismiss();
                    CVVFragment CVVFragment = new CVVFragment();
                    CVVFragment.setthetellerVariable(thetellerInitializer, savedCard);
                    CVVFragment.show(getFragmentManager(), "enter_cvv");
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            bottomSheetDialog.setContentView(v);
            bottomSheetDialog.show();
        }
        else {
            showToast("You have no saved cards");
        }

    }

    public class MyBrowser extends WebViewClient {
        FrameLayout progressContainer;
        String responseAsJString;
        String txRef;

        public MyBrowser(String txRef) {
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
            if (getActivity() != null) {
                Toast.makeText(getActivity(), "Please Wait", Toast.LENGTH_LONG).show();
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (initialUrl == null) {
                initialUrl = url;
            }
            else {
                if (url.contains("code=000")) {
                    ChargeResponse chargeResponse = new ChargeResponse();
                    chargeResponse.setCode("000");
                    chargeResponse.setStatus("approved");
                    chargeResponse.setReason("Transaction successful!");
                    chargeResponse.setTxRef(txRef);
                    if (CardFragment.this.getActivity() != null) {
                        CardFragment.this.getActivity().finish();
                    }
                    presenter.saveThisCard(
                            thetellerInitializer.getEmail(),
                            thetellerActivity.getApiKey(),
                            cardNoTv.getText().toString(),
                            cardExpiryTv.getText().toString().substring(0,2),
                            cardExpiryTv.getText().toString().substring(3,5),
                            cardType);
                    Gson gson = new Gson();
                    responseAsJString = gson.toJson(chargeResponse);
                    Intent intent = new Intent();
                    intent.putExtra("response", responseAsJString);
                    theteller_results = responseAsJString;

                    if (getActivity() != null) {
                        Log.wtf("response", theteller_results);
                        getActivity().setResult(thetellerActivity.RESULT_SUCCESS, intent);
                        getActivity().finish();
                    }

                }
                if (url.contains("code=100&status=Declined")){
                    ChargeResponse chargeResponse = new ChargeResponse();
                    chargeResponse.setCode("100");
                    chargeResponse.setStatus("Declined");
                    chargeResponse.setReason("Transaction failed!");
                    chargeResponse.setTxRef(txRef);
                    if (getActivity() != null) {
                        getActivity().finish();
                    }
                    Gson gson = new Gson();
                    responseAsJString = gson.toJson(chargeResponse);
                    Intent intent = new Intent();
                    intent.putExtra("response", responseAsJString);
                    theteller_results = responseAsJString;
                    Log.wtf("response", responseAsJString);

                    if (getActivity() != null) {
                        getActivity().setResult(thetellerActivity.RESULT_SUCCESS, intent);
                        getActivity().finish();
                    }
                }
            }
            Log.d("URLS", url);
        }

    }

    private class ExpiryWatcher implements TextWatcher {

        private final Calendar calendar;
        private final SimpleDateFormat simpleDateFormat;
        private String lastInput = "";

        public ExpiryWatcher() {
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("MM/yy");
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String input = editable.toString();

            try {
                calendar.setTime(simpleDateFormat.parse(input));
            } catch (ParseException e) {
                if (editable.length() == 2 && !lastInput.endsWith("/")) {
                    int month = Integer.parseInt(input);
                    if (month <= 12) {
                        cardExpiryTv.setText(cardExpiryTv.getText().toString() + "/");
                        cardExpiryTv.setSelection(cardExpiryTv.getText().toString().length());
                    } else {
                        cardExpiryTv.setText("12");
                        cardExpiryTv.setSelection(cardExpiryTv.getText().toString().length());
                    }
                } else if (editable.length() == 2 && lastInput.endsWith("/")) {
                    int month = Integer.parseInt(input);
                    if (month <= 12) {
                        cardExpiryTv.setText(cardExpiryTv.getText().toString().substring(0,1));
                        cardExpiryTv.setSelection(cardExpiryTv.getText().toString().length());
                    } else {
                        cardExpiryTv.setText("12");
                        cardExpiryTv.setSelection(cardExpiryTv.getText().toString().length());
                    }
                } else if (editable.length() == 1) {
                    int month = Integer.parseInt(input);
                    if (month > 1) {
                        cardExpiryTv.setText("0" + cardExpiryTv.getText().toString()  + "/");
                        cardExpiryTv.setSelection(cardExpiryTv.getText().toString().length());
                    }
                }

                lastInput = cardExpiryTv.getText().toString();
            }
        }
    }

}
