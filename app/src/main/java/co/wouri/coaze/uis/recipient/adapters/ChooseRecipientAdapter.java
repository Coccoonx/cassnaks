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
        ChooseRecipientViewHolder cv = new ChooseRecipientViewHolder(this.context, v, viewType);
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
    public void onBindViewHolder(final ChooseRecipientViewHolder holder, int position) {

        final int itemPosition = position;
        RecipientItem settingsRecipients = recipientItems.get(position);
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


    }


    @Override
    public int getItemCount() {
        return recipientItems.size();
    }


    public static class RecipientItem {
        public int leftIcon;
        public String title;
        boolean isSelected;

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

    public void updateItems(){
        recipientItems.clear();
        recipientItems.addAll(initList(recipients));
        notifyDataSetChanged();
    }

   /* private List<RecipientItem> initRecipientList() {
        ArrayList<RecipientItem> recipientItemList =new ArrayList<>();
        return null;
    }


    private List<SettingsItem> initSettingsList() {

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



    public static class  RecipientItem{
        public int id;
        public Bitmap leftIcon;
        public String title;
        public RecipientItem(int id, Bitmap leftIcon, String title) {
            this.id = id;
            this.leftIcon = leftIcon;
            this.title = title;


        }
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
    }
*/

}
