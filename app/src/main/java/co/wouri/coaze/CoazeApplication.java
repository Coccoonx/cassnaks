package co.wouri.coaze;

import android.support.multidex.MultiDexApplication;

import co.wouri.coaze.storage.PreferencesStorage;
import co.wouri.coaze.utils.UIUtils;

/**
 * Created by borelkoumo on 05/11/15.
 */
public class CoazeApplication extends MultiDexApplication {
    private static PreferencesStorage preferencesStorageInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        this.preferencesStorageInterface = new PreferencesStorage(this);
        UIUtils.initCustomTypefaces(this);


    }

    public static PreferencesStorage getPreferencesStorageInterface() {
        return preferencesStorageInterface;
    }
}
