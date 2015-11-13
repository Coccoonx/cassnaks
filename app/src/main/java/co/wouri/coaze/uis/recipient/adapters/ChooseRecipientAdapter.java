package co.wouri.coaze.uis.recipient.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.uis.ChooseRecipientActivity;
import co.wouri.coaze.uis.recipient.viewholders.ChooseRecipientViewHolder;

/**
 * Created by lyonnel on 05/11/15.
 */
public class ChooseRecipientAdapter extends RecyclerView.Adapter<ChooseRecipientViewHolder> {


    public static final int USER_1 = 0;
    public static final int USER_2 = 1;
    public static final int USER_3 = 2;
    public static final int USER_4 = 3;
    public static final int USER_5 = 4;
    public static final int USER_6 = 5;
    public static final int USER_7 = 6;
    public static final int USER_8 = 7;

    public int selectedItem = -1;

    Context context;
    List<RecipientItem> recipientItems;
    List<Recipient> recipients;


    public ChooseRecipientAdapter(Context context, List<Recipient> recipients) {
        this.context = context;
        this.recipientItems = initList(recipients);
        this.recipients = recipients;
    }

    @Override
    public ChooseRecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setting_items, null);
        ChooseRecipientViewHolder cv = new ChooseRecipientViewHolder(this.context, v);
        return cv;
    }


    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // Handle key up and key down and attempt to move selection
        recyclerView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();

                // Return false if scrolled to the bounds and allow focus to move off the list
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                        // return tryMoveSelection(lm, 1);
                    } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                        //return tryMoveSelection(lm, -3);
                    }
                }

                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(final ChooseRecipientViewHolder holder, final int position) {
        RecipientItem settingsRecipients = recipientItems.get(position);
        settingsRecipients.holder = holder;
        //holder.id = settingsRecipients.getId();
        try {
            holder.leftImageView.setImageResource(settingsRecipients.leftIcon);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        holder.title.setText(settingsRecipients.title);
        ((ImageView) holder.rightView).setImageResource(R.drawable.ic_check);
        ((ImageView) holder.rightView).setColorFilter(Color.argb(255, 111, 209, 78));

        holder.recipient = recipients.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedItem < 0) {
                    selectedItem = position;
                    holder.isSelected = true;
                    holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                    holder.rightView.setVisibility(View.VISIBLE);
                    ChooseRecipientActivity.recipient = holder.recipient;
                    Log.d("coaze", "selected item :" + selectedItem);
                } else if (holder.isSelected) {
                    selectedItem = -1;
                    holder.isSelected = false;
                    holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                    holder.rightView.setVisibility(View.INVISIBLE);
                    ChooseRecipientActivity.recipient = null;
                    Log.d("coaze", "selected item :" + selectedItem);
                } else {
                    if(getItem(selectedItem).holder!=null){
                        getItem(selectedItem).holder.isSelected = false;
                        getItem(selectedItem).holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                        getItem(selectedItem). holder.rightView.setVisibility(View.INVISIBLE);
                        selectedItem = position;
                        holder.isSelected = true;
                        holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                        holder.rightView.setVisibility(View.VISIBLE);
                        ChooseRecipientActivity.recipient = holder.recipient;
                        Log.d("coaze", "selected item :" + selectedItem);
                    }else{
                        Log.d("coaze","not possible");
                    }
                }
            }
        });


    }

    public RecipientItem getItem(int position){
        return recipientItems.get(position);
    }
    @Override
    public int getItemCount() {
        return recipientItems.size();
    }


    public static class RecipientItem {
        public int leftIcon;
        public String title;
        public ChooseRecipientViewHolder holder = null;

        public RecipientItem(int leftIcon, String title) {

            this.leftIcon = leftIcon;
            this.title = title;

        }
    }

    private List<RecipientItem> initList(List<Recipient> recipients) {

        List<RecipientItem> list = new ArrayList<>();

        for (Recipient recipient : recipients) {
            RecipientItem recipientItem = new RecipientItem(recipient.getImage(), recipient.getFirstName());
            list.add(recipientItem);
        }
        return list;


    }

    public void updateItems() {
        recipientItems.clear();
        recipientItems.addAll(initList(recipients));
        notifyDataSetChanged();
    }

}