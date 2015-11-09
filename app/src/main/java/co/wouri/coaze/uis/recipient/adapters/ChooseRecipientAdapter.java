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

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.R;
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
    List<SettingsItem> settingsItems;
    private Bitmap profilePictureRounded;
    private Notification.Builder notBuilder;

    public ChooseRecipientAdapter(Context context) {
        this.context = context;
        settingsItems = initSettingsList();
    }

    @Override
    public ChooseRecipientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_setting_items, null);
        ChooseRecipientViewHolder cv = new ChooseRecipientViewHolder(this.context, v, viewType);
        return cv;
    }

    @Override
    public void onBindViewHolder(ChooseRecipientViewHolder holder, int position) {
        SettingsItem settingsItem = settingsItems.get(position);
        holder.id = settingsItem.id;
        //Log.d("Coaze", "onBindViewHolder before " +holder.leftImageView);
        // holder.leftImageView.setImageResource(settingsItem.leftIcon);
        try{
            holder.leftImageView.setImageBitmap(getRoundedCornerBitmap(settingsItem.leftIcon, 320));
        }catch (Exception e){
            e.printStackTrace();
        }
        //holder.leftImageView.setImageBitmap(getRoundedCornerBitmap(Bitmap, 320));
        // Log.d("Coaze", "onBindViewHolder after " +holder.leftImageView.setImageBitmap(settingsItem.leftIcon););
        holder.title.setText(settingsItem.title);
        holder.settingsItem = settingsItem;
        //holder.leftImageView.setImageBitmap(settingsItem.bitmap);
        ((ImageView) holder.rightView).setImageResource(R.drawable.ic_check);
        ((ImageView) holder.rightView).setColorFilter(Color.argb(255, 111, 209, 78));
        //((ImageView) holder.rightView).setColorFilter(Color.argb(255, 35, 154, 252));
    }

    @Override
    public int getItemCount() {
        return settingsItems.size();
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
            Log.d("COAZE", "initSettingsList LeftIcons " + leftIcons[i]);
            Log.d("COAZE", "initSettingsList " + bitmaps[i]);
            settingsItemsArrayList.add(new SettingsItem(ids[i], bitmaps[i], titles[i]));
        }
        // return the list
        return settingsItemsArrayList;
    }

    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixel) {

        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = 0xffffffff;
        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixel;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();

        return output;

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
    }


}
