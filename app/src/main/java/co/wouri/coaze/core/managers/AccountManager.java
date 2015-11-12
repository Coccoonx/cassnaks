package co.wouri.coaze.core.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.core.models.Account;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.storage.CoazeSettingsUtils;


public class AccountManager {

    private static FileManager fileManager = FileManager.getInstance();

    private static Account account = null;


    private static synchronized Account retrieveAccount() {

        account = fileManager.restore();
        return account;
    }

    public static Account saveAccount(Account account) {
        boolean isOk = fileManager.save(account);
        if (isOk) {
            CoazeSettingsUtils.setAccountAlreadyConfigured(true);
            Log.d("coaze", "saveAccount completed");
            return account;
        } else {
            return null;
        }
    }

    public static synchronized Account saveAccount() {
        return account = saveAccount(account);
    }

    public static Account getCurrentUserAccount() {
        if (account == null) {
            account = AccountManager.retrieveAccount();
            if (account == null) {
                //KeeperApplication.initUserAccount();
                account = new Account();
            }
            return account;
        } else {
            return account;
        }
    }

    public static boolean deleteLocalAccount() {
        return fileManager.deleteData();
    }


    // Managing Account

    public static List<Recipient> getRecipients() {
        return account.getRecipients();
    }

    public static List<Transfer> getTransferts() {
        List list = new ArrayList();
        return account.getTransfers();
    }

    public static String addRecipient(Recipient recipient) {
        if (account.getRecipients().add(recipient)) {
            saveAccount();
            return recipient.getRecipientId();
        } else
            return null;

    }


    public static String addTransfer(Transfer transfer) {
        if (account.getTransfers().add(transfer)) {
            saveAccount();
            return transfer.getTransferId();
        } else
            return null;
    }

    public static void deleteRecipient(Recipient recipient) {
        List<Recipient> recipients = getRecipients();
        recipients.remove(recipient);
        account.setRecipients(recipients);
        saveAccount();
    }
}


