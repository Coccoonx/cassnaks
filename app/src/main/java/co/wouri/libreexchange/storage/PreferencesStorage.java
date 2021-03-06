package co.wouri.libreexchange.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class PreferencesStorage {

    private SharedPreferences prefs;

    public PreferencesStorage(Context ctx) {
        prefs = ctx.getSharedPreferences(ctx.getPackageName() + "_libreexchange_settings", Context.MODE_PRIVATE);
    }

    public void save(String key, Boolean value) {
        prefs.edit().putBoolean(key, value).commit();
    }

    public boolean load(String key, Boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

    public void save(String key, String value) {
        prefs.edit().putString(key, value).commit();
    }

    public String load(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void save(String key, Integer value) {
        prefs.edit().putInt(key, value).commit();
    }

    public Integer load(String key, Integer defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void save(String key, Float value) {
        prefs.edit().putFloat(key, value).commit();
    }

    public float load(String key, float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public int load(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void save(String key, Long value) {
        prefs.edit().putLong(key, value).commit();
    }

    public Long load(String key, Long defaultValue) {
        return prefs.getLong(key, defaultValue);
    }

    public Map<String, ?> getAll() {

        return prefs.getAll();
    }
}
