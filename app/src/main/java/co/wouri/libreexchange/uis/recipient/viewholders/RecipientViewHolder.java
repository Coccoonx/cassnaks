package co.wouri.libreexchange.uis.recipient.viewholders;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.uis.EditRecipientActivity;

/**
 * Created by lyonnel on 05/11/15.
 */
public class RecipientViewHolder extends RecyclerView.ViewHolder {

    //public int id;
    public String id;
    public Context context;
    public ImageView leftImageView;
    public TextView title;
    public View rightView;
    public View rightViewDelete;
    public Recipient recipient;
    public View rightViewEdite;
    public Boolean isSelected = false;
    public View itemView;



    public RecipientViewHolder(final Context context, View view, int viewType) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);
        this.rightViewEdite = view.findViewById(R.id.rightIconEdit);
        this.rightViewDelete = view.findViewById(R.id.rightIconDelete);

        rightViewEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit button activated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, EditRecipientActivity.class);
                 intent.putExtra("recipient", (Parcelable)recipient);

                context.startActivity(intent);
                // View parent = (View)v.getParent();
            }
        });
        itemView = view;


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View parent = (View)v.getParent();
                if (!isSelected) {

                    isSelected = true;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                    title.setTextColor(context.getResources().getColor(R.color.color_background));
                    //parent.setBackgroundColor(Color.BLUE);
                    rightViewDelete.setVisibility(View.VISIBLE);
                    rightViewEdite.setVisibility(View.VISIBLE);
                } else {
                    isSelected = false;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                    title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    rightViewDelete.setVisibility(View.INVISIBLE);
                    rightViewEdite.setVisibility(View.INVISIBLE);
                    //rightView.setVisibility(View.INVISIBLE);
                }


            }
        });


    }


}
