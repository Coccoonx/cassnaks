package co.wouri.libreexchange.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.wouri.libreexchange.LibreExchangeApplication;


/**
 * An utility to get storage values
 */
public class LibreExchangeSettingsUtils {
    public static final String NOTIFICATIONS = "NOTIFICATIONS";
    public static final String APP_ALREADY_CONFIGURED = "APP_ALREADY_CONFIGURED";
    public static final String ACCOUNT_ALREADY_CONFIGURED = "ACCOUNT_ALREADY_CONFIGURED";
    public static final String PREFERED_NOTIFICATION_TYPES = "PREFERED_NOTIFICATION_TYPES";
    public static final String NOTIFICATION_FOR_CONNECTIVITY = "NOTIFICATION_FOR_CONNECTIVITY";


    public static final String PLAY_NOTIFICATIONS = "notifications_play";
    public static final String NOTIFICATION_SOUND = "notification_sound";
    public static final String NOTIFICATION_VIBRATE = "notification_vibrate";
    public static final String EDIT_PROFILE = "edit_profile";

    private static final String USER_ID = "USER_ID";
    private static final String USER_EMAIL = "USER_EMAIL";


    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String TOKEN_TYPE = "TOKEN_TYPE";
    private static final String EXPIRE_IN = "EXPIRE_IN";
    private static final String USER_FIRST_LOGIN = "USER_FIRST_LOGIN";
    private static final String USER_PIN = "USER_PIN";
    public static final String USER_LOGGED = "USER_LOGGED";
    public static final String USER_PASSWORD = "PASSWORD";


    public static void setNotificationsValue(boolean value) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(NOTIFICATIONS, value);
    }


    public static boolean getNotificationsValue() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(NOTIFICATIONS, true);
    }


    public static void setUserPassword(String value) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_PASSWORD, value);
    }


    public static String getUserPassword() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_PASSWORD, "");
    }

    public static String getUserUid() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        String userUID = storageInterface.load(USER_ID, "");
        if (userUID.equals("")) {
            setUserUid(UUID.randomUUID().toString());
            userUID = getUserUid();
            registerUser();
        }
        return userUID;
    }

    public static void setUserUid(String value) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_ID, value);
    }


    public static boolean getAppAlreadyConfigured() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(APP_ALREADY_CONFIGURED, false);
    }

    public static void setAppAlreadyConfigured(boolean value) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(APP_ALREADY_CONFIGURED, value);
    }

    public static void setAccountAlreadyConfigured(boolean value) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(ACCOUNT_ALREADY_CONFIGURED, value);
    }

    public static boolean getAccountAlreadyConfigured() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(ACCOUNT_ALREADY_CONFIGURED, false);
    }


    public static void setNotificationForConnectivity(boolean isSend) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(NOTIFICATION_FOR_CONNECTIVITY, isSend);
    }

    public static boolean getNotificationForConnectivity() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(NOTIFICATION_FOR_CONNECTIVITY, false);
    }


    public static void setPreferedNotificationTypes(List<Integer> preferedNotificationTypes) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        StringBuilder sb = new StringBuilder();
        for (Integer notificationID : preferedNotificationTypes) {
            sb.append(notificationID).append(",");
        }
        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1); //delete last comma
        }

        String value = sb.toString();
        storageInterface.save(PREFERED_NOTIFICATION_TYPES, value);
        Log.d("safertoday", "setPreferedNotificationTypes " + value);
    }

    public static List<Integer> getPreferedNotificationTypes() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();

        List<Integer> preferedNotificationTypes = new ArrayList<>();

        String loadedString = storageInterface.load(PREFERED_NOTIFICATION_TYPES, "");

        if (loadedString == null || "".equals(loadedString)) {
            return preferedNotificationTypes;
        }

        String[] parts = loadedString.split(",");
        Integer value;
        for (int i = 0; i < parts.length; i++) {
            try {
                value = Integer.parseInt(parts[i]);
                preferedNotificationTypes.add(value);
            } catch (NumberFormatException e) {
                Log.d("safertoday", "NumberFormatException thrown for value " + parts[i] + " (" + e.getMessage() + ")");
            }
        }
        return preferedNotificationTypes;
    }

    public static boolean canDisplayNotifications() {
        boolean notificationEnabled;
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(LibreExchangeSettingsUtils.PLAY_NOTIFICATIONS, true);
        return notificationEnabled;
    }

    public static boolean canVibrate() {
        boolean notificationEnabled;
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(LibreExchangeSettingsUtils.NOTIFICATION_VIBRATE, true);
        return notificationEnabled;
    }

    public static void canVibrate(boolean value) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(LibreExchangeSettingsUtils.NOTIFICATION_VIBRATE, value);
    }

    public static boolean canPlayNotificationSound() {
        // TODO Implement this method, in order to load the sound the customer want to play. Since now, we just return true
        return true;
    }

    public static void registerUser() {
//        Log.d("safer init", "Job Manager" + LibreExchangeApplication.jobManager);
//        Log.d("safer init", "Current Customer customer" + ProfileManager.getCurrentUserProfile());
//        LibreExchangeApplication.jobManager.addJobInBackground(new PostAccountJob(
//                ProfileManager.getCurrentUserProfile().getCurrentUser()));
    }


    public static Location getUserLastKnownPosition() {
        Float latitude, longitude;
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        latitude = storageInterface.load("userlat", -1f);
        longitude = storageInterface.load("userlong", -1f);

        if (latitude == -1f && longitude == -1f)
            return null;

        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }


    public static void setUserLogged(boolean b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_LOGGED, b);
    }

    public static boolean getUserLogged() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_LOGGED, false);
    }

    public static void setUserFirstLogin(long b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_FIRST_LOGIN, b);
    }

    public static Float getUserFirstLogin() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_FIRST_LOGIN, System.currentTimeMillis());
    }

    public static void setUserPin(String b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_PIN, b);
    }

    public static String getUserPin() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_PIN, "0000");
    }

    public static void setExpireIn(int b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(EXPIRE_IN, b);
    }

    public static int getExpireIn() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(EXPIRE_IN, 0);
    }

    public static void setTokenType(String b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(TOKEN_TYPE, b);
    }

    public static String getTokenType() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(TOKEN_TYPE, "");
    }

    public static void setAccessToken(String b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(ACCESS_TOKEN, b);
    }

    public static String getAccessToken() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(ACCESS_TOKEN, "");
    }


    public static void setUserEmail(String b) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_EMAIL, b);
    }

    public static String getUserEmail() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_EMAIL, "");
    }


    public static Long firstOpening() {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_FIRST_LOGIN, (Long) new Date().getTime());
    }

    public static void firstOpening(long l) {
        PreferencesStorage storageInterface = LibreExchangeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_FIRST_LOGIN, l);
    }
}
