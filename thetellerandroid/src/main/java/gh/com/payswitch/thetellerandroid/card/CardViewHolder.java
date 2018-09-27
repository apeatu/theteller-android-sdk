package gh.com.payswitch.thetellerandroid.card;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import gh.com.payswitch.thetellerandroid.R;

public class CardViewHolder extends RecyclerView.ViewHolder {
    public final TextView mTitle;
    public CardViewHolder(final View itemView) {
        super(itemView);
        mTitle = (TextView) itemView.findViewById(R.id.title);
    }
}