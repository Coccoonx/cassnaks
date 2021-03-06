package co.wouri.libreexchange.uis.recipient.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.uis.ChooseRecipientActivity;
import co.wouri.libreexchange.uis.recipient.viewholders.ChooseRecipientViewHolder;
import co.wouri.libreexchange.utils.ImageLoader;

public class ChooseRecipientAdapter extends RecyclerView.Adapter<ChooseRecipientViewHolder> {


    public int selectedItem = -1;

    Context context;
    List<RecipientItem> recipientItems;
    List<Recipient> recipients;
    ImageLoader mImageLoader;
    ChooseRecipientCallBack chooseRecipientCallBack;


    public ChooseRecipientAdapter(Context context, List<Recipient> recipients, ImageLoader imageLoader, ChooseRecipientCallBack callBack) {
        this.context = context;
        this.chooseRecipientCallBack = callBack;
        this.recipientItems = initList(recipients);
        this.recipients = recipients;
        mImageLoader = imageLoader;


    }

    @Override
    public ChooseRecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipients_item, null);
        ChooseRecipientViewHolder cv = new ChooseRecipientViewHolder(this.context, v);
        return cv;
    }


    @Override
    public void onBindViewHolder(final ChooseRecipientViewHolder holder, final int position) {
        RecipientItem settingsRecipients = recipientItems.get(position);
        settingsRecipients.holder = holder;
        //holder.id = settingsRecipients.getId();
        try {
            mImageLoader.loadImage(settingsRecipients.leftIcon, holder.leftImageView);
//            holder.leftImageView.setImageBitmap(settingsRecipients.leftIcon);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        holder.title.setText(settingsRecipients.title);
//        ((ImageView) holder.rightView).setImageResource(R.drawable.ic_check);
//        ((ImageView) holder.rightView).setColorFilter(Color.argb(255, 0, 0, 0));

        holder.recipient = recipients.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem = position;
                holder.isSelected = true;
                holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            holder.itemView.setSelected(true);
                            holder.title.setTextColor(context.getResources().getColor(R.color.color_background));

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                            holder.itemView.setSelected(false);
                        }
                        return true;
                    }
                });
                ChooseRecipientActivity.recipient = holder.recipient;
                chooseRecipientCallBack.nextStep();


                /*if (selectedItem < 0) {
                    selectedItem = position;
                    holder.isSelected = true;
                    holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                    holder.title.setTextColor(context.getResources().getColor(R.color.color_background));
                    ChooseRecipientActivity.recipient = holder.recipient;
                    chooseRecipientCallBack.nextStep();

                    Log.d("coaze", "selected item : [selectedItem < 0]" + selectedItem);
                } else if (holder.isSelected) {
                    selectedItem = -1;
                    holder.isSelected = false;
                    holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                    holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    ChooseRecipientActivity.recipient = null;
                    Log.d("coaze", "selected item : [holder.isSelected]" + selectedItem);
                } else {
                    if (getItem(selectedItem).holder != null) {
                        getItem(selectedItem).holder.isSelected = false;
                        getItem(selectedItem).holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                        getItem(selectedItem).holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                        selectedItem = position;
                        holder.isSelected = true;
                        holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                        holder.title.setTextColor(context.getResources().getColor(R.color.color_background));
                        ChooseRecipientActivity.recipient = holder.recipient;
                        Log.d("coaze", "selected item :  [Big else i]" + selectedItem);
                    } else {
                        Log.d("coaze", "not possible");
                    }
                }*/
            }
        });


    }

    public RecipientItem getItem(int position) {
        return recipientItems.get(position);
    }

    @Override
    public int getItemCount() {
        return recipientItems.size();
    }

    private List<RecipientItem> initList(List<Recipient> recipients) {

        List<RecipientItem> list = new ArrayList<>();

        for (Recipient recipient : recipients) {
            String title = recipient.getFirstName();
            if (recipient.getLastName() != null)
                title += " " + recipient.getLastName();
            RecipientItem recipientItem = new RecipientItem(recipient.getImageUri(), title);
            list.add(recipientItem);
        }
        return list;


    }

    public void updateItems() {
        recipientItems.clear();
        recipientItems.addAll(initList(recipients));
        notifyDataSetChanged();
    }

    public interface ChooseRecipientCallBack {
        void nextStep();
    }

    public static class RecipientItem {
        public String leftIcon;
        public String title;
        public ChooseRecipientViewHolder holder = null;

        public RecipientItem(String leftIcon, String title) {

            this.leftIcon = leftIcon;
            this.title = title;

        }
    }
}