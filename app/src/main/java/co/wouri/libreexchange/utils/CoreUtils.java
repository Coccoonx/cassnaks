package co.wouri.libreexchange.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.Settings;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

import co.wouri.libreexchange.LibreExchangeApplication;

/**
 * Created by fenkam on 11/12/15.
 */
public class CoreUtils {

    public static String getDeviceId() {
        return Settings.Secure.getString(LibreExchangeApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
