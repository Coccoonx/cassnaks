package co.wouri.libreexchange;

import android.support.multidex.MultiDexApplication;

import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.storage.PreferencesStorage;
import co.wouri.libreexchange.utils.UIUtils;


public class LibreExchangeApplication extends MultiDexApplication {
    private static PreferencesStorage preferencesStorageInterface;
    private static LibreExchangeApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        LibreExchangeApplication.preferencesStorageInterface = new PreferencesStorage(this);
        UIUtils.initCustomTypefaces(this);
        initAccount();


    }

    public static PreferencesStorage getPreferencesStorageInterface() {
        return preferencesStorageInterface;
    }

    void initAccount() {
        ProfileManager.getCurrentUserAccount();
        ProfileManager.saveAccount();
    }

    public static synchronized LibreExchangeApplication getInstance() {
        if (instance != null)
            return instance;
        return null;
    }
}
