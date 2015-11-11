package co.wouri.coaze.core.managers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Account;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.storage.CoazeSettingsUtils;


public class AccountManager {

    private static FileManager fileManager = FileManager.getInstance();

    private static Account account = null;

    public static String firstName = "Prisca";
    public static String lastName = "Nzouckio";
    public static String address = "30610Yde";
    public static String location = "30610Yde";


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
        /*List list  =  new ArrayList();
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        list.add(new Recipient(R.drawable.barack,"barack", "Obama"));
        account.setRecipients(list);*/
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
}


