package co.wouri.coaze.uis.recipient.viewholders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.coaze.R;
import co.wouri.coaze.uis.EditRecipientActivity;
import co.wouri.coaze.uis.recipient.adapters.RecipientAdapter;

/**
 * Created by lyonnel on 05/11/15.
 */
public class RecipientViewHolder extends RecyclerView.ViewHolder {

    public int id;
    public Context context;
    public ImageView leftImageView;
    public TextView title;
    public View rightViewDelete;
    public View rightViewEdit;
    public RelativeLayout relativeLayout;


    public RecipientViewHolder(final Context context, View view, int viewType) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);
        this.rightViewEdit = view.findViewById(R.id.rightIconEdit);
        this.rightViewDelete = view.findViewById(R.id.rightIconDelete);
        this.relativeLayout = (RelativeLayout) view.findViewById(R.id.rootLayout);
    }


}
