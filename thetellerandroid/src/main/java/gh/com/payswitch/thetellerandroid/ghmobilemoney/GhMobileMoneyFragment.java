package gh.com.payswitch.thetellerandroid.ghmobilemoney;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import gh.com.payswitch.thetellerandroid.Payload;
import gh.com.payswitch.thetellerandroid.PayloadBuilder;
import gh.com.payswitch.thetellerandroid.R;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.SavedPhone;
import gh.com.payswitch.thetellerandroid.thetellerActivity;
import gh.com.payswitch.thetellerandroid.thetellerConstants;
import gh.com.payswitch.thetellerandroid.thetellerInitializer;
import gh.com.payswitch.thetellerandroid.Utils;

import static android.view.View.GONE;
import static gh.com.payswitch.thetellerandroid.thetellerConstants.theteller_results;

/**
 * A simple {@link Fragment} subclass.
 */
public class GhMobileMoneyFragment extends Fragment implements GhMobileMoneyContract.View {

    View v;
    TextInputEditText amountEt;
    TextInputLayout amountTil;
    TextInputEditText phoneEt;
    TextInputLayout phoneTil;
    Button savedPhonesBtn;
    thetellerInitializer thetellerInitializer;
    private ProgressDialog progressDialog;
    private ProgressDialog pollingProgressDialog ;
    GhMobileMoneyPresenter presenter;
    Spinner networkSpinner;
    SwitchCompat saveNumberSwitch;
    boolean shouldISaveThisPhoneNumber = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_gh_mobile_money, container, false);

        presenter = new GhMobileMoneyPresenter(getActivity(), this);
        amountEt = (TextInputEditText) v.findViewById(R.id.theteller_amountTV);
        amountTil = (TextInputLayout) v.findViewById(R.id.theteller_amountTil);
        phoneEt = (TextInputEditText) v.findViewById(R.id.theteller_phoneEt);
        phoneTil = (TextInputLayout) v.findViewById(R.id.theteller_phoneTil);
        networkSpinner = (Spinner) v.findViewById(R.id.theteller_networkSpinner);
        saveNumberSwitch = (SwitchCompat) v.findViewById(R.id.theteller_saveNumberSwitch);
        Button payButton = (Button) v.findViewById(R.id.theteller_payButton);
        savedPhonesBtn = (Button) v.findViewById(R.id.theteller_savedPhonesButton);

//        presenter.checkForSavedGHMobileMoney(thetellerInitializer.getEmail());

        savedPhonesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onSavedPhonesClicked(thetellerInitializer.getEmail());
            }
        });

        thetellerInitializer = ((thetellerActivity) getActivity()).getThetellerInitializer();

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        double amountToPay = thetellerInitializer.getAmount();

        if (amountToPay > 0) {
            amountTil.setVisibility(GONE);
            amountEt.setText(String.valueOf(amountToPay));
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gh_mobile_money_networks, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        networkSpinner.setAdapter(adapter);

        return v;
    }

    private void clearErrors() {
        amountTil.setError(null);
        phoneTil.setError(null);

        amountTil.setErrorEnabled(false);
        phoneTil.setErrorEnabled(false);

    }

    private void validate() {
        clearErrors();
        Utils.hide_keyboard(getActivity());

        boolean valid = true;

        String amount = amountEt.getText().toString();
        String phone = phoneEt.getText().toString();

        try {
            double amnt = Double.parseDouble(amount);

            if (amnt <= 0) {
                valid = false;
                amountTil.setError("Enter a valid amount");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            valid = false;
            amountTil.setError("Enter a valid amount");
        }

        if (phone.length() < 1) {
            valid = false;
            phoneTil.setError("Enter a valid number");
        }

        String network = networkSpinner.getSelectedItem().toString();
        String shortNetwork = null;
        switch (network){
            case "MTN":
                shortNetwork = "MTN";
                break;
            case "TIGO":
                shortNetwork = "TGO";
                break;
            case "AIRTEL":
                shortNetwork = "ATL";
                break;
            case "VODAFONE":
                shortNetwork = "VDF";
            default:
                break;
        }

        if (networkSpinner.getSelectedItemPosition() == 0) {
            valid = false;
            showToast("Select a network");
        }

        if (valid) {

            if (saveNumberSwitch.isChecked()) {
                shouldISaveThisPhoneNumber = true;
//                presenter.requeryTx(flwRef, thetellerInitializer.getApiKey(), shouldISaveThisPhoneNumber);
            }

            thetellerInitializer.setAmount(Double.parseDouble(amount));

            PayloadBuilder builder = new PayloadBuilder();
            builder.setAmount(thetellerInitializer.getAmount() + "")
                    .setNarration(thetellerInitializer.getNarration())
                    .setCurrency(thetellerInitializer.getCurrency())
                    .setMerchant_id(thetellerInitializer.getMerchant_id())
                    .setVoucher_code(thetellerInitializer.getVoucher_code())
                    .setEmail(thetellerInitializer.getEmail())
                    .setFirstname(thetellerInitializer.getfName())
                    .setLastname(thetellerInitializer.getlName())
                    .setIP(Utils.getDeviceImei(getActivity()))
                    .setTxRef(thetellerInitializer.getTxRef())
                    .setMeta(thetellerInitializer.getMeta())
                    .setNetwork(shortNetwork)
                    .setPhonenumber(phone)
                    .setApiUser(thetellerInitializer.getApiUser())
                    .setApiKey(thetellerInitializer.getApiKey())
                    .setDevice_fingerprint(Utils.getDeviceImei(getActivity()));

            if (thetellerInitializer.getPayment_plan() != null) {
                builder.setPaymentPlan(thetellerInitializer.getPayment_plan());
            }

            Payload body = builder.createGhMobileMoneyPayload();

//            presenter.fetchFee(body);
            presenter.chargeGhMobileMoney(body, thetellerConstants.API_KEY);
        }

    }


    @Override
    public void showProgressIndicator(boolean active) {

//        if (getActivity().isFinishing()) { return; }
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
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

    @Override
    public void showPollingIndicator(boolean active) {
        if (getActivity().isFinishing()) { return; }

        if(pollingProgressDialog == null) {
            pollingProgressDialog = new ProgressDialog(getActivity());
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


    @Override
    public void onPaymentError(String message) {
//        dismissDialog();
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(String message) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccessful(String status, String responseAsString) {
//        dismissDialog();
//        closeBottomSheetsIfOpen();

        if (shouldISaveThisPhoneNumber && status.equals("000")) {
            presenter.saveThisPhone(thetellerInitializer.getEmail(), thetellerActivity.getApiKey(), phoneEt.getText().toString(), networkSpinner.getSelectedItem().toString());
        }

        Intent intent = new Intent();
        intent.putExtra("response", responseAsString);
        theteller_results = responseAsString;

        if (getActivity() != null) {

            getActivity().setResult(thetellerActivity.RESULT_SUCCESS, intent);
            getActivity().finish();
        }
    }

    @Override
    public void displayFee(String charge_amount, final Payload payload) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You will be charged a total of " + charge_amount + thetellerInitializer.getCurrency() + ". Do you want to continue?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();

            presenter.chargeGhMobileMoney(payload, thetellerInitializer.getApiKey());

            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void showFetchFeeFailed(String s) {
        showToast(s);
    }

    @Override
    public void onPaymentFailed(String message, String responseAsJSONString) {
        Intent intent = new Intent();
        intent.putExtra("response", responseAsJSONString);
        if (getActivity() != null) {
            getActivity().setResult(thetellerActivity.RESULT_ERROR, intent);
            getActivity().finish();
        }
    }

//    @Override
//    public void showFullProgressIndicator(boolean active) {
//
//        if (progressContainer == null) {
//            progressContainer = (FrameLayout) v.findViewById(R.id.theteller_progressContainer);
//        }
//
//        if (active) {
//            progressContainer.setVisibility(View.VISIBLE);
//        }
//        else {
//            progressContainer.setVisibility(GONE);
//        }
//
//
//    }

    /**
     *
     *  Displays a list of user saved cards and displays them in a bottom sheet
     *  It also attaches a listener to the list of displayed cards to detect clicks
     *  and sends the savedPhone details to the presenter for further processing of payment
     * @param phoneList = List of saved phones
     */
    @Override
    public void showSavedPhoneList(List<SavedPhone> phoneList) {


        if (phoneList.size() > 0) {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.pick_saved_number_layout, null, false);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.theteller_recycler2);

            SavedPhoneRecyclerAdapter adapter = new SavedPhoneRecyclerAdapter();
            adapter.set(phoneList);

            adapter.setSavedPhoneSelectedListener(new Callbacks.SavedPhoneSelectedListener() {
                @Override
                public void onPhoneSelected(SavedPhone savedPhone) {
                    bottomSheetDialog.dismiss();
                    final PayloadBuilder builder = new PayloadBuilder();
                    builder.setAmount(thetellerInitializer.getAmount() + "")
                            .setNarration(thetellerInitializer.getNarration())
                            .setCurrency(thetellerInitializer.getCurrency())
                            .setMerchant_id(thetellerInitializer.getMerchant_id())
                            .setTerminal_id(thetellerInitializer.getTerminal_id())
                            .setVoucher_code(thetellerInitializer.getVoucher_code())
                            .setEmail(thetellerInitializer.getEmail())
                            .setFirstname(thetellerInitializer.getfName())
                            .setLastname(thetellerInitializer.getlName())
                            .setIP(Utils.getDeviceImei(getActivity()))
                            .setTxRef(thetellerInitializer.getTxRef())
                            .setMeta(thetellerInitializer.getMeta())
                            .setApiUser(thetellerInitializer.getApiUser())
                            .setApiKey(thetellerInitializer.getApiKey())
                            .setDevice_fingerprint(Utils.getDeviceImei(getActivity()));

                    if (thetellerInitializer.getPayment_plan() != null) {
                        builder.setPaymentPlan(thetellerInitializer.getPayment_plan());
                    }
                    builder.setPhonenumber(savedPhone.getPhoneNumber());
                    builder.setNetwork(savedPhone.getNetwork());
                    final Payload body = builder.createGhMobileMoneyPayload();

                    presenter.chargeGhMobileMoney(body, thetellerConstants.API_KEY);
                }
            });

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            bottomSheetDialog.setContentView(v);
            bottomSheetDialog.show();
        }
        else {
            showToast("You have no saved Phone Numbers");
        }

    }


}

