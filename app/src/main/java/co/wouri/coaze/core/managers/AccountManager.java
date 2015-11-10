package co.wouri.coaze.core.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
                account = AccountManager.retrieveAccount();
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

    public static Set<Recipient> getRecipients() {

        TreeSet<Recipient> setIR = new TreeSet<>();
        for (Recipient report : account.getRecipients()) {
            setIR.add(report);
        }
        return setIR;
    }

    public static Set<Transfer> getTransferts() {
        TreeSet<Transfer> setCFA = new TreeSet<>();
        for (Transfer report : account.getTransfers()) {
            setCFA.add(report);
        }
        return setCFA;
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
}


