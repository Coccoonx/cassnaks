package co.wouri.coaze.uis.transferhistory;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;

import co.wouri.coaze.R;

/**
 * Created by edwidge on 05/11/15.
 */
public class PersonViewHolder extends RecyclerView.ViewHolder implements Serializable {

    CardView cardView;
    TextView personName;
    TextView personAmount;
    ImageView personPhoto;
    TextView personTransferType;
    TextView personTransferDate;
    RelativeLayout relativeLayout;

    PersonViewHolder(View itemView) {
        super(itemView);
        cardView = (CardView) itemView.findViewById(R.id.card_view_item);
        personName = (TextView) itemView.findViewById(R.id.person_name);
        personAmount = (TextView) itemView.findViewById(R.id.person_amount);
        personPhoto = (ImageView) itemView.findViewById(R.id.person_photo);
        personTransferDate = (TextView) itemView.findViewById(R.id.person_transfert_date);
        personTransferType = (TextView) itemView.findViewById(R.id.person_transfert_type);
        relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_transfer_history_item);
    }

}
