package co.wouri.coaze.core.managers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Account;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.storage.CoazeSettingsUtils;
import co.wouri.coaze.uis.recipient.adapters.ChooseRecipientAdapter;


public class AccountManager {

    private static FileManager fileManager = FileManager.getInstance();
    ChooseRecipientAdapter choosee;
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

        List list = new ArrayList();
        Log.d("coooze", "getRecipients    "+list);
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4, "cedric", "jiongo"));
        list.add(new Recipient(R.drawable.friend4,"cedric","jiongo"));


        Log.d("coooze", "getRecipients    "+list);

        account.setRecipients(list);
        return account.getRecipients();
    }

    public static List<Transfer> getTransferts() {

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
}


