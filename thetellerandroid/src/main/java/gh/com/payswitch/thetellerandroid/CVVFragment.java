package gh.com.payswitch.thetellerandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import gh.com.payswitch.thetellerandroid.card.SavedCardVP;
import gh.com.payswitch.thetellerandroid.data.SavedCard;

import static gh.com.payswitch.thetellerandroid.card.CreditCardView.cardType;

public class CVVFragment extends DialogFragment {

    SavedCardVP cardPresenter;
    TextInputEditText cvvTv;
    TextInputLayout cvvTil;
    String cvv;
    thetellerInitializer thetellerInitializer;
    SavedCard savedCard;

    public CVVFragment() {
        // Required empty public constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v;
        v = inflater.inflate(R.layout.fragment_cvv, null);
        cardPresenter = new SavedCardVP();
        cvvTv = (TextInputEditText) v.findViewById(R.id.theteller_cvvTv2);
        cvvTil = (TextInputLayout) v.findViewById(R.id.theteller_cvvTil2);

        // Add action buttons
        builder.setPositiveButton(R.string.pay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setCvv(cvvTv.getText().toString());
                if (isValid()) {
                    final PayloadBuilder builder = new PayloadBuilder();
                    builder.setAmount(thetellerInitializer.getAmount()+"")
                            .setEmail(thetellerInitializer.getEmail())
                            .setCurrency("GHS")
                            .setMerchant_id(thetellerInitializer.getMerchant_id())
                            .setFirstname(thetellerInitializer.getfName())
                            .setLastname(thetellerInitializer.getlName())
                            .setNarration(thetellerInitializer.getNarration())
                            .set3dUrl(thetellerInitializer.get3dUrl())
                            .setNarration(thetellerInitializer.getNarration())
                            .setIP(Utils.getDeviceImei(getActivity()))
                            .setTxRef(thetellerInitializer.getTxRef())
                            .setMeta(thetellerInitializer.getMeta())
                            .setApiUser(thetellerInitializer.getApiUser())
                            .setApiKey(thetellerInitializer.getApiKey())
                            .setDevice_fingerprint(Utils.getDeviceImei(getActivity()))
                            .setCardType(cardType);

                    if (thetellerInitializer.getPayment_plan() != null) {
                        builder.setPaymentPlan(thetellerInitializer.getPayment_plan());
                    }
                    String cardNoStripped = savedCard.getPan().replaceAll("\\s", "");
                    builder.setCardno(cardNoStripped);
                    builder.setExpiryyear(savedCard.getExpiryYear());
                    builder.setExpirymonth(savedCard.getExpiryMonth());
                    builder.setCvv(getCvv());
                    if (savedCard.getCardType() == R.drawable.visa) {
                        builder.setCardType("VIS");
                    }else if (savedCard.getCardType() == R.drawable.mastercard) {
                        builder.setCardType("MAS");
                    }else if (savedCard.getCardType() == R.drawable.verve) {
                        builder.setCardType("VER");
                    }else if (savedCard.getCardType() == R.drawable.amex) {
                        builder.setCardType("AME");
                    }
                    else{
                        builder.setCardType(null);
                    }

                    final Payload body = builder.createPayload();
                    Log.wtf("current", thetellerInitializer.get3dUrl()+"surprised");
                    cardPresenter.chargeCard(body, thetellerConstants.API_KEY, getActivity());
                }else {
                    Toast.makeText(getActivity(), "Invalid CVV", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(v);

        return builder.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cvv, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public boolean isValid() {
        return  !(getCvv().length() < 3);
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getCvv() {
        return this.cvv;
    }

    private void clearErrors() {
        cvvTil.setError(null);
        cvvTil.setErrorEnabled(false);
    }

    public void setthetellerVariable(thetellerInitializer thetellerInitializer, SavedCard savedCard) {
        this.thetellerInitializer = thetellerInitializer;
        this.savedCard = savedCard;
    }

}
