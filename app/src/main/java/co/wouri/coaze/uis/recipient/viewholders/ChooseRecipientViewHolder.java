package co.wouri.coaze.uis.recipient.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.uis.ChooseRecipientActivity;


public class ChooseRecipientViewHolder extends RecyclerView.ViewHolder {


    //public int id;
    public String id;
    public Context context;
    public ImageView leftImageView;
    public TextView title;
    public View rightView;
    public boolean isSelected = false;
    public RelativeLayout mRelativeLayout;
    public Recipient recipient;


    // public ChooseRecipientAdapter.SettingsItem settingsItem;


    public ChooseRecipientViewHolder(final Context context, View view, int viewType) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);
        this.rightView = view.findViewById(R.id.rightIconCheck);
        this.mRelativeLayout = (RelativeLayout) view.findViewById(R.id.rootLayout);

        view.setClickable(true);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rightView.setVisibility(View.VISIBLE);
                // View parent = (View)v.getParent();
                if (!isSelected) {
                    isSelected = true;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_seleted_item));

                    ChooseRecipientActivity.recipient = recipient;
                    rightView.setVisibility(View.VISIBLE);
                } else {
                    isSelected = false;
                    RelativeLayout relativeLayout = (RelativeLayout) v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_background));
                    rightView.setVisibility(View.INVISIBLE);
                }


            }
        });


    }
}


