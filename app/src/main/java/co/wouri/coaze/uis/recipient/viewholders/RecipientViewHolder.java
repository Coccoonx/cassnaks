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
    public View rightView;
    public View rightViewDelete;
    public View rightViewEdite;
    public Boolean isSelected = false;

    public RecipientAdapter.SettingsItem settingsItem;


    public RecipientViewHolder(final Context context, View view, int viewType) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);
        this.rightViewEdite = view.findViewById(R.id.rightIconEdit);
        this.rightViewDelete = view.findViewById(R.id.rightIconDelete);

        rightViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "delete button activated", Toast.LENGTH_SHORT).show();
                // View parent = (View)v.getParent();


            }
        });
        rightViewEdite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "edit button activated", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, EditRecipientActivity.class));
                // View parent = (View)v.getParent();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View parent = (View)v.getParent();
                if (isSelected) {

                    isSelected = false;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                    //parent.setBackgroundColor(Color.BLUE);
                    rightViewDelete.setVisibility(View.VISIBLE);
                    rightViewEdite.setVisibility(View.VISIBLE);
                } else {
                    isSelected = true;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                    rightViewDelete.setVisibility(View.INVISIBLE);
                    rightViewEdite.setVisibility(View.INVISIBLE);
                    //rightView.setVisibility(View.INVISIBLE);
                }


            }
        });


    }


}
