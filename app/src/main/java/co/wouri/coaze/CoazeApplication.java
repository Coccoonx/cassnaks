package co.wouri.coaze;

import android.support.multidex.MultiDexApplication;

import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.storage.PreferencesStorage;
import co.wouri.coaze.utils.UIUtils;


public class CoazeApplication extends MultiDexApplication {
    private static PreferencesStorage preferencesStorageInterface;

    @Override
    public void onCreate() {
        super.onCreate();
        CoazeApplication.preferencesStorageInterface = new PreferencesStorage(this);
        UIUtils.initCustomTypefaces(this);
        initAccount();


    }

    public static PreferencesStorage getPreferencesStorageInterface() {
        return preferencesStorageInterface;
    }

    void initAccount() {
        AccountManager.getCurrentUserAccount();
        AccountManager.saveAccount();
    }
}
