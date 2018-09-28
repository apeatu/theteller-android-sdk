package gh.com.payswitch.thetellerandroid;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.ItemModel;
import gh.com.payswitch.thetellerandroid.data.SavedCard;
import gh.com.payswitch.thetellerandroid.data.SavedPhone;

import java.util.ArrayList;
import java.util.List;

public class SavedCNRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{

    private List<ItemModel> savedCNList = new ArrayList<>();
    private ItemModel mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private Callbacks.SavedCardSelectedListener savedCardSelectedListener;
    private Callbacks.SavedPhoneSelectedListener savedPhoneSelectedListener;

    public void setCard(List<SavedCard> savedCardList) {
        savedCNList.addAll(savedCardList);
    }
    public void setPhone(List<SavedPhone> savedPhoneList) {
        savedCNList.addAll(savedPhoneList);
    }

    @Override
    public int getItemViewType(int position) {
        return savedCNList.get(position).getListItemType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v;

        switch  (viewType) {
            case ItemModel.TYPE_A:
                v = inflater.inflate(R.layout.select_bank_list_item, parent, false);
                return new CardViewHolder(v);
            case ItemModel.TYPE_B:
                v = inflater.inflate(R.layout.mobile_money_number_item, parent, false);
                return new PhoneViewHolder(v);
            }
        return  null;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ItemModel cardOrNumber = savedCNList.get(position);
        holder.bindType(cardOrNumber);
        Log.d("card or number", cardOrNumber.toString());
    }

    @Override
    public int getItemCount() {
        Log.wtf("count size", Integer.toString(savedCNList.size()));
        return savedCNList.size();
    }

    public void setSavedCardSelectedListener(Callbacks.SavedCardSelectedListener savedCardSelectedListener) {
        this.savedCardSelectedListener = savedCardSelectedListener;
    }
    public void setSavedPhoneSelectedListener(Callbacks.SavedPhoneSelectedListener savedPhoneSelectedListener) {
        this.savedPhoneSelectedListener = savedPhoneSelectedListener;
    }

    public class CardViewHolder extends ViewHolder implements View.OnClickListener {
        private TextView itemTv;
        SavedCard savedCard;
        private ImageView cardTypeIv;

        CardViewHolder(View v) {
            super(v);
            itemTv = (TextView) v.findViewById(R.id.phoneNumberTv);
            cardTypeIv = (ImageView) v.findViewById(R.id.cardTypeIv);
            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            savedCardSelectedListener.onCardSelected(this.savedCard);
        }

        @Override
        public void bindType(ItemModel savedCard) {
            this.savedCard = (SavedCard) savedCard;
            itemTv.setText(this.savedCard.getMaskedPan());
            cardTypeIv.setImageResource(this.savedCard.getCardType());
        }
    }

    public class PhoneViewHolder extends ViewHolder implements View.OnClickListener {
        private TextView itemTv;
        SavedPhone savedPhone;
        private ImageView r_switchIv;

        PhoneViewHolder(View v) {
            super(v);
            itemTv = (TextView) v.findViewById(R.id.phoneNumberTv);
            r_switchIv = (ImageView) v.findViewById(R.id.r_switchIv);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            savedPhoneSelectedListener.onPhoneSelected(savedPhone);
        }

        @Override
        public void bindType(ItemModel savedPhone) {
            this.savedPhone = (SavedPhone) savedPhone;
            itemTv.setText(this.savedPhone.getPhoneNumber());
            r_switchIv.setImageResource(this.savedPhone.getNetworkImage());
        }
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = savedCNList.get(position);
        mRecentlyDeletedItemPosition = position;
        savedCNList.remove(position);
        notifyItemRemoved(position);
//        showUndoSnackbar();
    }

    private void undoDelete() {
        savedCNList.add(mRecentlyDeletedItemPosition,
                mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

//    private void showUndoSnackbar() {
//        View view = mActivity.findViewById(R.id.coordinator_layout);
//        Snackbar snackbar = Snackbar.make(view, R.string.snack_bar_text,
//                Snackbar.LENGTH_LONG);
//        snackbar.setAction(R.string.snack_bar_undo, v -> undoDelete());
//        snackbar.show();
//    }

}

