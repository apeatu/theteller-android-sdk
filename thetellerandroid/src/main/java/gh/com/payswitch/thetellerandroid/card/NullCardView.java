package gh.com.payswitch.thetellerandroid.card;

import androidx.fragment.app.Fragment;
import android.view.View;

import gh.com.payswitch.thetellerandroid.data.SavedCard;

import java.util.List;

public class NullCardView extends Fragment implements View.OnClickListener, CardContract.View {

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showProgressIndicator(boolean active) {

    }

    @Override
    public void onPaymentError(String message) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void onVBVAuthModelUsed(String authUrlCrude, String responseAsJSONString, String txRef) {

    }

    @Override
    public void onPaymentSuccessful(String status, String responseAsString) {

    }

    @Override
    public void onPaymentFailed(String status, String responseAsString) {

    }

    @Override
    public void showSavedCards(List<SavedCard> cards) {

    }

}
