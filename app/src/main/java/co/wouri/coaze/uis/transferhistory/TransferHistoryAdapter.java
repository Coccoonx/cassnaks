package co.wouri.coaze.uis.transferhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.uis.TransferDetailsActivity;
import co.wouri.coaze.utils.UIUtils;

/**
 * Created by edwidge on 05/11/15.
 */
public class TransferHistoryAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    List<Transfer> transfers;
    private Context context;

    public TransferHistoryAdapter(List<Transfer> transfers, Context context) {
        this.transfers = transfers;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_transfer_history, viewGroup, false);
        //set Font
        TextView personName = (TextView) v.findViewById(R.id.person_name);
        TextView personAmount = (TextView) v.findViewById(R.id.person_amount);
        TextView personTransferType = (TextView) v.findViewById(R.id.person_transfert_type);
        TextView personTransferDate = (TextView) v.findViewById(R.id.person_transfert_date);
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, personName, personAmount, personTransferDate, personTransferType);
        Log.d("Coaze", "transfer history Set Font ");
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(final PersonViewHolder personViewHolder, int i) {

        final Transfer transfer = transfers.get(i);
        final Recipient recipient = transfer.getRecipient();

        personViewHolder.personName.setText(recipient.getFirstName());
        personViewHolder.personAmount.setText(transfer.getSenderCurrency() + transfer.getAmount());
        personViewHolder.personPhoto.setImageResource(transfer.getRecipient().getImage());

        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy / hh:mmaa");
        String transferDate = sdf.format(transfer.getCreationDate());


        transferDate = transferDate.substring(0, transferDate.indexOf("/"));
        personViewHolder.personTransferDate.setText(" \\ " + transferDate);
        personViewHolder.personTransferType.setText(transfer.getTransferType());

        personViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransferDetailsActivity.class);
                intent.putExtra("transfer", (Parcelable) transfer);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transfers.size();
    }
}
