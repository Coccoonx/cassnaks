package co.wouri.coaze.storage;

import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import co.wouri.coaze.CoazeApplication;


/**
 * An utility to get storage values
 */
public class CoazeSettingsUtils {
    public static final String NOTIFICATIONS = "NOTIFICATIONS";
    public static final String USER_RADIUS = "USER_RADIUS";
    public static final String ANONYMOUS = "ANONYMOUS";
    public static final String APP_ALREADY_CONFIGURED = "APP_ALREADY_CONFIGURED";
    public static final String ACCOUNT_ALREADY_CONFIGURED = "ACCOUNT_ALREADY_CONFIGURED";
    public static final String PREFERED_NOTIFICATION_TYPES = "PREFERED_NOTIFICATION_TYPES";
    public static final String LOCATION_TECHNIQUE = "LOCATION_TECHNIQUE";
    public static final String NOTIFICATION_FOR_CONNECTIVITY = "NOTIFICATION_FOR_CONNECTIVITY";
    public static final String FAKE_LOCATION = "FAKE_LOCATION";

    public static final String USER_LOGGED_TO_FACEBOOK = "USER_LOGGED_TO_FACEBOOK";
    public static final String USER_LOGGED_TO_TWITTER = "USER_LOGGED_TO_TWITTER";
    public static final String USER_LOGGED_TO_GOOGLE_PLUS = "USER_LOGGED_TO_GOOGLE_PLUS";
    public static final String SHARE_ON_SOCIAL_MEDIA = "SHARE_ON_SOCIAL_MEDIA";

    public static final String PLAY_NOTIFICATIONS = "notifications_play";
    public static final String NOTIFICATION_SOUND = "notification_sound";
    public static final String NOTIFICATION_VIBRATE = "notification_vibrate";
    public static final String EDIT_PROFILE = "edit_profile";

    public static final String SHOW_MY_LAST_SEEN = "show_my_last_seen";
    private static final String USER_ID = "USER_ID";
    private static final String USER_GCM_ID = "USER_GCM_ID";
    private static final String USER_FAKE_LATITUDE = "USER_FAKE_LATITUDE";
    private static final String USER_FAKE_LONGITUDE = "USER_FAKE_LONGITUDE";
    private static final String USER_FIRST_OPENING = "USER_FIRST_OPENING";


    public static boolean getFakePositionStatus() {
        boolean notificationEnabled;
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(FAKE_LOCATION, false);
        return notificationEnabled;
    }

    public static void setFakePositionStatus(boolean value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(FAKE_LOCATION, value);
    }

    public static void setNotificationsValue(boolean value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(NOTIFICATIONS, value);
    }

    public static void setAnonymous(boolean value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(ANONYMOUS, value);
    }

    public static boolean getAnonymous() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(ANONYMOUS, false);
    }


//    public static void setUserRadius(int value) {
//        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
//        storageInterface.save(USER_RADIUS, value);
//    }
//
//    public static int getUserRadius() {
//        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
//        return storageInterface.load(USER_RADIUS, Constants.USER_RADIUS_2000M);
//    }

    private static void setUserFakeLatitude(float value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_FAKE_LATITUDE, value);
    }

    private static float getUserFakeLatitude() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_FAKE_LATITUDE, 0.0f);
    }

    private static void setUserFakeLongitude(float value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_FAKE_LONGITUDE, value);
    }

    private static float getUserFakeLongitude() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_FAKE_LONGITUDE, 0.0f);
    }

    public static void setUserFakeAddress(String value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_FAKE_LONGITUDE, value);
    }

    public static String getUserFakeAddress() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_FAKE_LONGITUDE, "");
    }

    public static boolean getNotificationsValue() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(NOTIFICATIONS, true);
    }

    public static String getUserUid() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        String userUID = storageInterface.load(USER_ID, "");
        if (userUID.equals("")) {
            setUserUid(UUID.randomUUID().toString());
            userUID = getUserUid();
            registerUser();
        }
        return userUID;
    }

    public static void setUserUid(String value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_ID, value);
    }

    public static String getUserGCMId() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        String userUID = storageInterface.load(USER_GCM_ID, "");
        return userUID;
    }

    public static void setUserGCMId(String regId) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_GCM_ID, regId);
    }

    public static boolean getAppAlreadyConfigured() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(APP_ALREADY_CONFIGURED, false);
    }

    public static void setAppAlreadyConfigured(boolean value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(APP_ALREADY_CONFIGURED, value);
    }

    public static void setAccountAlreadyConfigured(boolean value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(ACCOUNT_ALREADY_CONFIGURED, value);
    }

    public static boolean getAccountAlreadyConfigured() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(ACCOUNT_ALREADY_CONFIGURED, false);
    }

    public static void setLocationTechnique(String provider) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(LOCATION_TECHNIQUE, provider);
    }

    public static String getLocationTechnique() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(LOCATION_TECHNIQUE, LocationManager.NETWORK_PROVIDER);
    }


    public static void setNotificationForConnectivity(boolean isSend) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(NOTIFICATION_FOR_CONNECTIVITY, isSend);
    }

    public static boolean getNotificationForConnectivity() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(NOTIFICATION_FOR_CONNECTIVITY, false);
    }


    public static void setPreferedNotificationTypes(List<Integer> preferedNotificationTypes) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
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
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();

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
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(CoazeSettingsUtils.PLAY_NOTIFICATIONS, true);
        return notificationEnabled;
    }

    public static boolean canVibrate() {
        boolean notificationEnabled;
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        notificationEnabled = storageInterface.load(CoazeSettingsUtils.NOTIFICATION_VIBRATE, true);
        return notificationEnabled;
    }

    public static void canVibrate(boolean value) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(CoazeSettingsUtils.NOTIFICATION_VIBRATE, value);
    }

    public static boolean canPlayNotificationSound() {
        // TODO Implement this method, in order to load the sound the account want to play. Since now, we just return true
        return true;
    }

    public static void registerUser() {
//        Log.d("safer init", "Job Manager" + CoazeApplication.jobManager);
//        Log.d("safer init", "Current Account account" + AccountManager.getCurrentUserAccount());
//        CoazeApplication.jobManager.addJobInBackground(new PostAccountJob(
//                AccountManager.getCurrentUserAccount().getCurrentUser()));
    }


    public static Location getUserLastKnownPosition() {
        Float latitude, longitude;
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        latitude = storageInterface.load("userlat", -1f);
        longitude = storageInterface.load("userlong", -1f);

        if (latitude == -1f && longitude == -1f)
            return null;

        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    public static void saveLastUserLocation(Location location) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save("userlat", (float) location.getLatitude());
        storageInterface.save("userlong", (float) location.getLongitude());
        Log.d("Save Location:", location.toString());
    }

    public static void saveFakeUserLocation(Location location) {
        setUserFakeLatitude((float) location.getLatitude());
        setUserFakeLongitude((float) location.getLongitude());
        Log.d("Save Fake Location:", location.toString());
    }

    public static Location loadFakeUserLocation() {
        Location location = new Location("FakeLocation");
        location.setLatitude(getUserFakeLatitude());
        location.setLongitude(getUserFakeLongitude());
        Log.d("Loading Fake Location:", location.toString());
        return location;
    }


    public static void setFacebookLogged(boolean b) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_LOGGED_TO_FACEBOOK, b);
    }

    public static boolean getFacebookLogged() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_LOGGED_TO_FACEBOOK, false);
    }

    public static void setTwitterkLogged(boolean b) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_LOGGED_TO_TWITTER, b);
    }

    public static boolean getTwitterLogged() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_LOGGED_TO_TWITTER, false);
    }

    public static void setGooglePlusLogged(boolean b) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_LOGGED_TO_GOOGLE_PLUS, b);
    }

    public static boolean getGooglePlusLogged() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_LOGGED_TO_GOOGLE_PLUS, false);
    }

    public static boolean shareOnSocialMedia() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(SHARE_ON_SOCIAL_MEDIA, false);
    }

    public static void shareOnSocialMedia(boolean b) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(SHARE_ON_SOCIAL_MEDIA, false);
    }


    public static Long firstOpening() {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        return storageInterface.load(USER_FIRST_OPENING, (Long) new Date().getTime());
    }

    public static void firstOpening(long l) {
        PreferencesStorage storageInterface = CoazeApplication.getPreferencesStorageInterface();
        storageInterface.save(USER_FIRST_OPENING, l);
    }
}
