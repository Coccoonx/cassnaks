package co.wouri.coaze.uis.recipient.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.uis.recipient.viewholders.RecipientViewHolder;

/**
 * Created by lyonnel on 05/11/15.
 */
public class RecipientAdapter extends RecyclerView.Adapter<RecipientViewHolder> {


    public static final int USER_1 = 0;
    public static final int USER_2 = 1;
    public static final int USER_3 = 2;
    public static final int USER_4 = 3;
    public static final int USER_5 = 4;
    public static final int USER_6 = 5;
    public static final int USER_7 = 6;
    public static final int USER_8 = 7;

    Context context;
    List<Recipient> recipients;


    public RecipientAdapter(Context context, List<Recipient> recipients) {
        this.context = context;
        this.recipients = recipients;
    }

    @Override
    public RecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setting_items, null);
        RecipientViewHolder cv = new RecipientViewHolder(this.context, v, viewType);
        return cv;
    }

    @Override
    public void onBindViewHolder(RecipientViewHolder holder, final int position) {
        Recipient recipientsItem = recipients.get(position);
        holder.id = recipientsItem.getId();
        try {
            // holder.leftImageView.setImageBitmap(getRoundedCornerBitmap(settingsItem.leftIcon, 320));
            holder.leftImageView.setImageResource(recipientsItem.getImage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.title.setText(recipientsItem.getFirstName());
        ((ImageView) holder.rightViewDelete).setImageResource(R.drawable.ic_trashbin);
        ((ImageView) holder.rightViewEdite).setImageResource(R.drawable.ic_edit);
        ((ImageView) holder.rightViewDelete).setColorFilter(Color.argb(255, 29, 181, 245));
        ((ImageView) holder.rightViewEdite).setColorFilter(Color.argb(255, 29, 181, 245));

        holder.recipient = recipients.get(position);
        holder.rightViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDismiss(position);
            }
        });
    }

    public void onItemDismiss(int position) {
        if (position != -1 && position < recipients.size()) {

            AccountManager.deleteRecipient(recipients.remove(position));
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    @Override
    public int getItemCount() {
        return recipients.size();
    }

  /*  private List<SettingsItem> initSettingsList() {

        ArrayList<SettingsItem> settingsItemsArrayList = new ArrayList<>();

        // The data
        int[] ids = {
                USER_1,
                USER_2,
                USER_3,
                USER_4,
                USER_5,
                USER_6,
                USER_7,
                USER_8
        };

        int[] leftIcons = {
                R.drawable.friend2,
                R.drawable.friend4,
                R.drawable.thumb1,
                R.drawable.friend4,
                R.drawable.thumb3,
                R.drawable.friend2,
                R.drawable.thumb3,
                R.drawable.friend2,

        };
        String[] titles = {
                "Aaron Bennett",
                "Abbey Christensen",
                "Ali connors",
                "Alex Nelson",
                "Anthony Stevens",
                "Barry Green",
                "B.B. King",
                "Brutta Holt"

        };

        Bitmap[] bitmaps = converterDrawableToBitmap(leftIcons);

        boolean[] isSwitchItem = {
                false,
                false,
                false,
        };

        // Construct the list data
        for (int i = 0; i < titles.length; i++) {
            Log.d("COAZE", "initSettingsList LeftIcons " + leftIcons[i]);
            Log.d("COAZE", "initSettingsList " + bitmaps[i]);
            settingsItemsArrayList.add(new SettingsItem(ids[i], bitmaps[i], titles[i]));
        }
        // return the list
        return settingsItemsArrayList;
    }



    private Bitmap[] converterDrawableToBitmap(int[] leftIcons) {
        Bitmap[] bitmaps = new Bitmap[leftIcons.length];
        for (int i = 0; i < leftIcons.length; i++) {
            Bitmap output = BitmapFactory.decodeResource(context.getResources(), leftIcons[i]);
            bitmaps[i] = output;
        }
        return bitmaps;
    }

    public static class SettingsItem {
        public int id;
        //public int leftIcon;
        public Bitmap leftIcon;
        public String title;
        // public  Bitmap bitmap;

        public SettingsItem(int id, Bitmap leftIcon, String title) {
            this.id = id;
            this.leftIcon = leftIcon;
            this.title = title;


        }
    }*/

}
