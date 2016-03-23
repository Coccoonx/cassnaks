package co.wouri.libreexchange.uis.recipient.adapters;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import co.wouri.libreexchange.BuildConfig;
import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.uis.ChooseRecipientActivity;
import co.wouri.libreexchange.uis.recipient.viewholders.ChooseRecipientViewHolder;
import co.wouri.libreexchange.utils.ImageLoader;
import co.wouri.libreexchange.utils.Utils;

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
    ImageLoader mImageLoader;


    public ChooseRecipientAdapter(Context context, List<Recipient> recipients, ImageLoader imageLoader) {
        this.context = context;
        this.recipientItems = initList(recipients);
        this.recipients = recipients;
        mImageLoader = imageLoader;


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
            mImageLoader.loadImage(settingsRecipients.leftIcon, holder.leftImageView);
//            holder.leftImageView.setImageBitmap(settingsRecipients.leftIcon);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        holder.title.setText(settingsRecipients.title);
        ((ImageView) holder.rightView).setImageResource(R.drawable.ic_check);
        ((ImageView) holder.rightView).setColorFilter(Color.argb(255, 0, 0, 0));

        holder.recipient = recipients.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedItem < 0) {
                    selectedItem = position;
                    holder.isSelected = true;
                    holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                    holder.title.setTextColor(context.getResources().getColor(R.color.color_background));
                    holder.rightView.setVisibility(View.VISIBLE);
                    ChooseRecipientActivity.recipient = holder.recipient;
                    Log.d("coaze", "selected item :" + selectedItem);
                } else if (holder.isSelected) {
                    selectedItem = -1;
                    holder.isSelected = false;
                    holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                    holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    holder.rightView.setVisibility(View.INVISIBLE);
                    ChooseRecipientActivity.recipient = null;
                    Log.d("coaze", "selected item :" + selectedItem);
                } else {
                    if (getItem(selectedItem).holder != null) {
                        getItem(selectedItem).holder.isSelected = false;
                        getItem(selectedItem).holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_background));
                        getItem(selectedItem).holder.title.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                        getItem(selectedItem).holder.rightView.setVisibility(View.INVISIBLE);
                        selectedItem = position;
                        holder.isSelected = true;
                        holder.mRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.color_seleted_item));
                        holder.title.setTextColor(context.getResources().getColor(R.color.color_background));
                        holder.rightView.setVisibility(View.VISIBLE);
                        ChooseRecipientActivity.recipient = holder.recipient;
                        Log.d("coaze", "selected item :" + selectedItem);
                    } else {
                        Log.d("coaze", "not possible");
                    }
                }
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


    public static class RecipientItem {
        public String leftIcon;
        public String title;
        public ChooseRecipientViewHolder holder = null;

        public RecipientItem(String leftIcon, String title) {

            this.leftIcon = leftIcon;
            this.title = title;

        }
    }

    private List<RecipientItem> initList(List<Recipient> recipients) {

        List<RecipientItem> list = new ArrayList<>();

        for (Recipient recipient : recipients) {
//            Bitmap bm = recipient.getImageUri();
//            if (bm == null) {
//                 bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.unknown);
//            }
            RecipientItem recipientItem = new RecipientItem(recipient.getImageUri(), recipient.getFirstName());
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