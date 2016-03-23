package co.wouri.libreexchange.core.managers;

import android.util.Log;

import java.util.List;

import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.storage.CoazeSettingsUtils;


public class ProfileManager {

    private static FileManager fileManager = FileManager.getInstance();

    private static Profile profile = null;


    private static synchronized Profile retrieveProfile() {

        profile = fileManager.restore();
        return profile;
    }

    public static Profile saveProfile(Profile profile) {
        boolean isOk = fileManager.save(profile);
        if (isOk) {
            CoazeSettingsUtils.setAccountAlreadyConfigured(true);
            Log.d("coaze", "saveProfile completed");
            return profile;
        } else {
            return null;
        }
    }

    public static synchronized Profile saveProfile() {
        return profile = saveProfile(profile);
    }

    public static Profile getCurrentUserProfile() {
        if (profile == null) {
            profile = retrieveProfile();
            if (profile == null) {
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
        return profile.getTransfers();
    }

    public static String addRecipient(Recipient recipient) {
        if (profile.getRecipients().add(recipient)) {
            saveProfile();
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
                recipients.setPhoneNumbers(recipient.getPhoneNumbers());
                saveProfile();
                return;
            }

        }

    }

    public static void updateTransfer(Transfer transfer){
        for (Transfer transfer1:profile.getTransfers()){

            if (transfer1.getId().equals(transfer.getId())){
                transfer1.setSenderCurrency(transfer.getSenderCurrency());
                transfer1.setAmount(transfer.getAmount());
                transfer1.setRecipient(transfer.getRecipient());
                transfer1.setCreationDate(transfer.getCreationDate());

            }
        }


    }


    public static String addTransfer(Transfer transfer) {
        if (profile.getTransfers().add(transfer)) {
            saveProfile();
            return transfer.getId();
        } else
            return null;
    }

    public static void deleteRecipient(Recipient recipient) {
        List<Recipient> recipients = getRecipients();
        recipients.remove(recipient);
        profile.setRecipients(recipients);
        saveProfile();
    }
}


