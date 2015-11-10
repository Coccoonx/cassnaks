package co.wouri.coaze.uis.transferhistory;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Person;
import co.wouri.coaze.uis.TransferDetailsActivity;
import co.wouri.coaze.utils.UIUtils;

/**
 * Created by edwidge on 05/11/15.
 */
public class TransferHistoryAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    List<Person> persons;
    private Context context;

    public TransferHistoryAdapter(List<Person> persons, Context context) {
        this.persons = persons;
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
        personViewHolder.personName.setText(persons.get(i).getName());
        personViewHolder.personAmount.setText("$" + persons.get(i).getAmount());
        personViewHolder.personPhoto.setImageBitmap(persons.get(i).getPhotoId());
        personViewHolder.personTransferDate.setText(" \\" + persons.get(i).getTransfertDate());
        personViewHolder.personTransferType.setText(persons.get(i).getTransfertType());
        final Person person = new Person(persons.get(i).getName(), persons.get(i).getPhotoId(),
                persons.get(i).getAmount(), persons.get(i).getTransfertType(), " \\" + persons.get(i).getTransfertDate());

        personViewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransferDetailsActivity.class);
                intent.putExtra("personName", person.getName());
                intent.putExtra("personAmount", person.getAmount());
//                intent.putExtra("personPhoto",person.getPhotoId());
                intent.putExtra("personTransferDate", person.getTransfertDate());
                intent.putExtra("personTransferType", person.getTransfertType());
                //Convert to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                person.getPhotoId().compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("personByteArray", byteArray);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
