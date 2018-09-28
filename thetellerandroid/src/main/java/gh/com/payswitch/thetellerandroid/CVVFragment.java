package gh.com.payswitch.thetellerandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.parceler.Parcels;

import gh.com.payswitch.thetellerandroid.card.CardContract;
import gh.com.payswitch.thetellerandroid.card.CardFragment;
import gh.com.payswitch.thetellerandroid.card.CardPresenter;
import gh.com.payswitch.thetellerandroid.card.SavedCardVP;
import gh.com.payswitch.thetellerandroid.data.SavedCard;

import static gh.com.payswitch.thetellerandroid.card.CreditCardView.cardType;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 */
public class CVVFragment extends DialogFragment {

//    private OnFragmentInteractionListener mListener;

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
        View v;
        v = inflater.inflate(R.layout.fragment_cvv, null);
        cardPresenter = new SavedCardVP();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v);// Add action buttons
        cvvTv = (TextInputEditText) v.findViewById(R.id.theteller_cvvTv2);
        cvvTil = (TextInputLayout) v.findViewById(R.id.theteller_cvvTil2);
        builder.setPositiveButton(R.string.pay, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setCvv(cvvTv.getText().toString());
                if (isValid()) {
                    final PayloadBuilder builder = new PayloadBuilder();
                    builder.setAmount(thetellerInitializer.getAmount() + "")
                            .setCurrency(thetellerInitializer.getCurrency())
                            .setFirstname(thetellerInitializer.getfName())
                            .setLastname(thetellerInitializer.getlName()).setIP(Utils.getDeviceImei(getActivity()))
                            .setTxRef(thetellerInitializer.getTxRef())
                            .setMeta(thetellerInitializer.getMeta())
                            .setPBFPubKey(thetellerInitializer.getApiKey())
                            .setDevice_fingerprint(Utils.getDeviceImei(getActivity()));

                    if (thetellerInitializer.getPayment_plan() != null) {
                        builder.setPaymentPlan(thetellerInitializer.getPayment_plan());
                    }
                    builder.setCardno(savedCard.getPan());
                    builder.setExpiryyear(savedCard.getExpiryYear());
                    builder.setExpirymonth(savedCard.getExpiryMonth());
                    builder.setCvv(getCvv());
                    builder.setCardType(cardType);
                    Log.wtf("card type in CVVFragment", cardType+" ..");
                    final Payload body = builder.createPayload();
                    cardPresenter.chargeCard(body, thetellerConstants.API_KEY, getActivity());

                }else {
                    Toast.makeText(getActivity(), "Invalid CVV", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return builder.create();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cvv, container, false);
    }

//    public void onPayButtonPressed(DialogFragment dialogFragment) {
//        if (mListener != null) {
//            mListener.onDialogPositiveClick(dialogFragment);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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

    /**
     * Remove all errors from the input fields
     */
    private void clearErrors() {
        cvvTil.setError(null);
        cvvTil.setErrorEnabled(false);
    }

    public void setthetellerVariable(thetellerInitializer thetellerInitializer, SavedCard savedCard) {
        this.thetellerInitializer = thetellerInitializer;
        this.savedCard = savedCard;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onDialogPositiveClick(DialogFragment dialogFragment);
//    }
}
