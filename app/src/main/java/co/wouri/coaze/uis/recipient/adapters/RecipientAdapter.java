package co.wouri.coaze.uis.recipient.adapters;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.uis.EditRecipientActivity;
import co.wouri.coaze.uis.recipient.viewholders.RecipientViewHolder;
import co.wouri.coaze.utils.BitmapUtils;

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
    List<SettingsItem> settingsItems;
    Boolean isSelected =false;


    public RecipientAdapter(Context context) {
        this.context = context;
        settingsItems = initSettingsList();
    }

    @Override
    public RecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setting_items, null);
        RecipientViewHolder rvh = new RecipientViewHolder(this.context, v, viewType);
        return rvh;
    }

    @Override
    public void onBindViewHolder(final RecipientViewHolder holder, final int position) {
        final SettingsItem settingsItem = settingsItems.get(position);
        holder.id = settingsItem.id;
        try{
            holder.leftImageView.setImageBitmap(BitmapUtils.getRoundedCornerBitmap(settingsItem.leftIcon, 320));
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        holder.title.setText(settingsItem.title);
        ((ImageView) holder.rightViewDelete).setImageResource(R.drawable.ic_trashbin);
        ((ImageView) holder.rightViewEdit).setImageResource(R.drawable.ic_edit);
        ((ImageView) holder.rightViewDelete).setColorFilter(Color.argb(255, 29, 181, 245));
        ((ImageView) holder.rightViewEdit).setColorFilter(Color.argb(255, 29, 181, 245));

        holder.rightViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemDismiss(position);
//
            }
        });

        holder.rightViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditRecipientActivity.class);
                i.putExtra("name", settingsItem.title);
                context.startActivity(i);
                // View parent = (View)v.getParent();
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelected) {
                    holder.relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_seleted_item));
                    holder.rightViewDelete.setVisibility(View.VISIBLE);
                    holder.rightViewEdit.setVisibility(View.VISIBLE);
                    isSelected = true;
                } else {
                    holder.relativeLayout.setBackgroundColor(context.getResources()
                            .getColor(R.color.color_background));
                    holder.rightViewDelete.setVisibility(View.INVISIBLE);
                    holder.rightViewEdit.setVisibility(View.INVISIBLE);
                    isSelected = false;
                }
            }
        });

    }
    //This is used to delete an item in the recyclerview
    public  void onItemDismiss(int position){
        if(position!=-1 && position<settingsItems.size()){
            settingsItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
        }
    }

    @Override
    public int getItemCount() {
        return (null !=settingsItems ? settingsItems.size() :0);
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
