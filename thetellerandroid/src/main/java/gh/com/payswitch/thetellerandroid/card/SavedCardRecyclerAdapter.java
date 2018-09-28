package gh.com.payswitch.thetellerandroid.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import gh.com.payswitch.thetellerandroid.R;
import gh.com.payswitch.thetellerandroid.Utils;
import gh.com.payswitch.thetellerandroid.data.Callbacks;
import gh.com.payswitch.thetellerandroid.data.SavedCard;

public class SavedCardRecyclerAdapter  extends RecyclerView.Adapter<SavedCardRecyclerAdapter.CardViewHolder>{

    private List<SavedCard> savedCardList;

    private Callbacks.SavedCardSelectedListener savedCardSelectedListener;

    public void set(List<SavedCard> cards) {
        this.savedCardList = cards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.select_bank_list_item, parent, false);
        return new CardViewHolder(v);
    }


    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        holder.bind(savedCardList.get(position));
    }

    @Override
    public int getItemCount() {
        return savedCardList.size();
    }

    public void setSavedCardSelectedListener(Callbacks.SavedCardSelectedListener savedCardSelectedListener) {
        this.savedCardSelectedListener = savedCardSelectedListener;
    }


    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView itemTv;
        private ImageView cardTypeIv;
        SavedCard savedCard;


        CardViewHolder(View v) {
            super(v);
            itemTv = (TextView) v.findViewById(R.id.phoneNumberTv);
            cardTypeIv = (ImageView) v.findViewById(R.id.cardTypeIv);
            v.setOnClickListener(this);
        }

        public void bind(SavedCard savedCard) {
            this.savedCard = savedCard;
            itemTv.setText(Utils.spacifyCardNumber(Utils.obfuscateCardNumber(this.savedCard.getFirst6(), this.savedCard.getLast4())));
            cardTypeIv.setImageResource(this.savedCard.getCardType());
        }

        @Override
        public void onClick(View v) {
            savedCardSelectedListener.onCardSelected(savedCard);
        }

    }
}