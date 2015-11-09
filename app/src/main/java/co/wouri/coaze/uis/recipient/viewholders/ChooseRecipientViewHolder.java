package co.wouri.coaze.uis.recipient.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.uis.recipient.adapters.ChooseRecipientAdapter;

/**
 * Created by lyonnel on 05/11/15.
 */
public class ChooseRecipientViewHolder extends RecyclerView.ViewHolder {

    public int id;
    public Context context;
    public ImageView leftImageView;
    public TextView title;
    public View rightView;
    public Boolean isSelected = false;


    public ChooseRecipientAdapter.SettingsItem settingsItem;


    public ChooseRecipientViewHolder(final Context context, View view, int viewType) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);
        this.rightView = view.findViewById(R.id.rightIconCheck);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View parent = (View)v.getParent();
                if (isSelected) {

                    isSelected = false;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_seleted_item));
                    rightView.setVisibility(View.VISIBLE);
                } else {
                    isSelected = true;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_background));
                    rightView.setVisibility(View.INVISIBLE);
                }


            }
        });


    }


}
