package co.wouri.libreexchange.uis.account.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.uis.MyDocumentsActivity;
import co.wouri.libreexchange.uis.ProfileActivity;


public class MainProfileViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemIcon;
    public TextView itemText;
    public int id;

    public MainProfileViewHolder(final Context context, View itemView) {
        super(itemView);
        itemIcon = (ImageView) itemView.findViewById(R.id.itemIcon);
        itemText = (TextView) itemView.findViewById(R.id.title);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemText.getText().toString().equals(context.getResources().getString(R.string.personal_information))) {
                    context.startActivity(new Intent(context, ProfileActivity.class));
                }
                if (itemText.getText().toString().equals(context.getResources().getString(R.string.my_documents))) {
                    context.startActivity(new Intent(context, MyDocumentsActivity.class));
                } else Toast.makeText(context, "Not yet Implemented", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
