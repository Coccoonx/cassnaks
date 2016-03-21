package co.wouri.libreexchange.utils;

import android.provider.Settings;

import co.wouri.libreexchange.LibreExchangeApplication;

/**
 * Created by fenkam on 11/12/15.
 */
public class CoreUtils {

    public static String getDeviceId() {
        return Settings.Secure.getString(LibreExchangeApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
