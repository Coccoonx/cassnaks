package co.wouri.libreexchange.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.Random;
import co.wouri.libreexchange.R;


public class UIUtils {
    //Font of the application
    public static String fontNameThin = "fonts/Roboto-Thin.ttf";
    public static String fontNameLight = "fonts/Roboto-Light.ttf";
    public static String fontNameBold = "fonts/Roboto-Bold.ttf";
    public static String fontNameRegular = "fonts/Roboto-Regular.ttf";
    //    public static String fontNameBellSlimBold = "fonts/Bell Slim Bold";
    public static String fontNameStMarie = "fonts/StMarie-Thin.otf";
    private static String fontNameMuseoSans500 = "fonts/MuseoSans_500.otf";
    //    public static String fontNameStMarie = "StMarie-Thin.otf";
//    public static String fontNameStMarie = "StMarie-Thin.otf";
//    public static String fontNameStMarie = "StMarie-Thin.otf";
//    public static String fontNameStMarie = "StMarie-Thin.otf";
    private static Typeface typefaceThin;
    private static Typeface typefaceBellSlimBold;
    private static Typeface typefaceLight;
    private static Typeface typefaceBold;
    private static Typeface typefaceRegular;
    private static Typeface typefaceStMarie;
    private static Typeface typefaceMuseoSans_500;

    public static int getActionBarSize(Context context) {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = context.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    /**
     * Returns the screen width in pixels
     *
     * @param context is the context to get the resources
     * @return the screen width in pixels
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * Returns a pseudo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimum value
     * @param max Maximum value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see Random#nextInt(int)
     */
    public static int getRandomInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so addPost 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static void setFont(Font fontType, View... views) {
        try {
            Typeface typeface = getTypeface(fontType);

            for (View view : views) {
                if (view instanceof TextView) {
                    ((TextView) view).setTypeface(typeface);
                }
                if (view instanceof EditText) {
                    ((EditText) view).setTypeface(typeface);
                }
                if (view instanceof Button) {
                    ((Button) view).setTypeface(typeface);
                } else if (view instanceof Toolbar) {
                    TextView t = (TextView) view.findViewById(R.id.toolbar_title);
                    if (t != null) {
                        t.setTypeface(typeface);
                    }
                }
            }

        } catch (Exception e) {
            Log.d("FONT", "FontName not found", e);
        }
    }

    public static void initCustomTypefaces(Context context) {
        Log.d("typeface", "" + context.getAssets() + fontNameThin);
        typefaceThin = Typeface.createFromAsset(context.getAssets(), fontNameThin);
        typefaceLight = Typeface.createFromAsset(context.getAssets(), fontNameLight);
        typefaceBold = Typeface.createFromAsset(context.getAssets(), fontNameBold);
        typefaceRegular = Typeface.createFromAsset(context.getAssets(), fontNameRegular);
        typefaceStMarie = Typeface.createFromAsset(context.getAssets(), fontNameStMarie);
        typefaceMuseoSans_500 = Typeface.createFromAsset(context.getAssets(), fontNameMuseoSans500);

    }

    public static Typeface getTypeface(Font fontType) {
        // TODO Uncomment this code in order to use the other font types. For now, our app use the regular font type
        if (fontType.equals(Font.THIN)) {
            return typefaceThin;
        } else if (fontType.equals(Font.LIGHT)) {
            return typefaceLight;
        } else if (fontType.equals(Font.BOLD)) {
            return typefaceBold;
        } else if (fontType.equals(Font.REGULAR)) {
            return typefaceRegular;
        } else if (fontType.equals(Font.ST_MARIE)) {
            return typefaceStMarie;
        } else if (fontType.equals(Font.MUSEOSANS_500)) {
            return typefaceMuseoSans_500;
        } else {
            return typefaceThin;
        }
    }

    public enum Font {
        THIN, LIGHT, BOLD, REGULAR, ST_MARIE, BELLSLIMBOLD, MUSEOSANS_500
    }

    public static void applyFontToMenuItem(Font font, MenuItem mi) {
        Typeface typeface = getTypeface(font);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", typeface), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    public static void showAnswer(Context context) {
        View infoView = LayoutInflater.from(context).inflate(R.layout.dialog_question, null);

        DialogPlus dialog = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(infoView))
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                    }
                })
                .setExpanded(false)  // This will enable the expand feature, (similar to android L share dialog)
                .create();
        dialog.show();
    }
}

