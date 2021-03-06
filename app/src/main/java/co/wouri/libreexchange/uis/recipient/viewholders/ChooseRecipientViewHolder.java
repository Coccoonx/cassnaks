package co.wouri.libreexchange.uis.recipient.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.models.Recipient;


public class ChooseRecipientViewHolder extends RecyclerView.ViewHolder {


    public String id;
    public Context context;
    public ImageView leftImageView;
    public TextView title;
//    public View rightView;
    public boolean isSelected = false;
    public RelativeLayout mRelativeLayout;
    public Recipient recipient;
    public static final String TAG="ChooseRecipientView";
    public View itemView;


    public ChooseRecipientViewHolder(final Context context, View view) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);
//        this.rightView = view.findViewById(R.id.rightIconCheck);
        this.mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rootLayout);
        itemView = view;

    }
}


