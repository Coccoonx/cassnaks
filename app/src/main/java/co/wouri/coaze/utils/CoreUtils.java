package co.wouri.coaze.utils;

import android.provider.Settings;

import co.wouri.coaze.CoazeApplication;

/**
 * Created by fenkam on 11/12/15.
 */
public class CoreUtils {

    public static String getDeviceId() {
        return Settings.Secure.getString(CoazeApplication.getInstance().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }
}
