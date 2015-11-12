package co.wouri.coaze.core.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.core.models.Profile;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.storage.CoazeSettingsUtils;


public class AccountManager {

    private static FileManager fileManager = FileManager.getInstance();

    private static Profile profile = null;


    private static synchronized Profile retrieveAccount() {

        profile = fileManager.restore();
        return profile;
    }

    public static Profile saveAccount(Profile profile) {
        boolean isOk = fileManager.save(profile);
        if (isOk) {
            CoazeSettingsUtils.setAccountAlreadyConfigured(true);
            Log.d("coaze", "saveAccount completed");
            return profile;
        } else {
            return null;
        }
    }

    public static synchronized Profile saveAccount() {
        return profile = saveAccount(profile);
    }

    public static Profile getCurrentUserAccount() {
        if (profile == null) {
            profile = AccountManager.retrieveAccount();
            if (profile == null) {
                //KeeperApplication.initUserAccount();
                profile = new Profile();
            }
            return profile;
        } else {
            return profile;
        }
    }

    public static boolean deleteLocalAccount() {
        return fileManager.deleteData();
    }


    // Managing Profile

    public static List<Recipient> getRecipients() {
        return profile.getRecipients();
    }

    public static List<Transfer> getTransferts() {
        List list = new ArrayList();
        return profile.getTransfers();
    }

    public static String addRecipient(Recipient recipient) {
        if (profile.getRecipients().add(recipient)) {
            saveAccount();
            return recipient.getId();
        } else
            return null;

    }

    public static void updateRecipient(Recipient recipient) {

        for (Recipient recipients : profile.getRecipients()) {
            if (recipients.getId().equals(recipient.getId())) {
                recipients.setAddress(recipient.getAddress());
                recipients.setCountry(recipient.getCountry());
                recipients.setFirstName(recipient.getFirstName());
                recipients.setCity(recipient.getCity());
                recipients.setEmail(recipient.getEmail());
                recipients.setPhoneNumber(recipient.getPhoneNumber());
                saveAccount();
                return;
            }

        }

    }


    public static String addTransfer(Transfer transfer) {
        if (profile.getTransfers().add(transfer)) {
            saveAccount();
            return transfer.getId();
        } else
            return null;
    }

    public static void deleteRecipient(Recipient recipient) {
        List<Recipient> recipients = getRecipients();
        recipients.remove(recipient);
        profile.setRecipients(recipients);
        saveAccount();
    }
}


