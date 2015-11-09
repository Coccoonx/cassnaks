package co.wouri.coaze.uis.recipient.adapters;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.uis.recipient.viewholders.ChooseRecipientViewHolder;
import co.wouri.coaze.utils.BitmapUtils;

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
    List<SettingsItem> settingsItems;
    Boolean isSelected =false;

    public ChooseRecipientAdapter(Context context) {
        this.context = context;
        settingsItems = initSettingsList();
    }

    @Override
    public ChooseRecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_setting_items, null);
        ChooseRecipientViewHolder cv = new ChooseRecipientViewHolder(this.context, v, viewType);
        return cv;
    }

    @Override
    public void onBindViewHolder(final ChooseRecipientViewHolder holder, int position) {
        SettingsItem settingsItem = settingsItems.get(position);
        holder.id = settingsItem.id;
        //Log.d("Coaze", "onBindViewHolder before " +holder.leftImageView);
        // holder.leftImageView.setImageResource(settingsItem.leftIcon);
        try{
            holder.leftImageView.setImageBitmap(BitmapUtils
                    .getRoundedCornerBitmap(settingsItem.leftIcon, 320));
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.title.setText(settingsItem.title);
        ((ImageView) holder.rightView).setColorFilter(Color.argb(255, 111, 209, 78));
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected) {
                    isSelected = true;
                    RelativeLayout relativeLayout = (RelativeLayout)
                            v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_seleted_item));
                    holder.rightView.setVisibility(View.VISIBLE);
                } else {
                    isSelected = false;
                    RelativeLayout relativeLayout = (RelativeLayout)
                            v.findViewById(R.id.rootLayout);
                    relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_background));
                    holder.rightView.setVisibility(View.INVISIBLE);
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != settingsItems? settingsItems.size():0);
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
                R.drawable.user_profile,
                R.drawable.thumb3,
                R.drawable.user_profile,
                R.drawable.friend2,
                R.drawable.thumb3,


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

        //convert int into Bitmap
        Bitmap[] bitmaps = converterDrawableToBitmap(leftIcons);


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

    public static class SettingsItem {

        public int id;
        public Bitmap leftIcon;
        public String title;

        public SettingsItem(int id, Bitmap leftIcon, String title) {
            this.id = id;
            this.leftIcon = leftIcon;
            this.title = title;
        }
    }


}
