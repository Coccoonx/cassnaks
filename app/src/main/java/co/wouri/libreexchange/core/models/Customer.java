package co.wouri.libreexchange.core.models;

import android.accounts.AccountManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

import co.wouri.libreexchange.LibreExchangeApplication;
import lombok.Data;


@Data
public class Customer implements Serializable {

    private Long id;

    private Wallet wallet;

    private Status status;

    private Date createDate;

    private Date lastUpdateDate;

    private String city;

    private String firstName;

    private String language;

    private String lastName;

    private String email;

    private String phone;

    private String state;

    private String country;

    private String zipCode;

    private String password;

    private Boolean enabled=false;

    public static String getUserEmail() {

        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        android.accounts.Account[] accounts = AccountManager.get(LibreExchangeApplication.getInstance()).getAccounts();
        String possibleEmail = "";
        for (android.accounts.Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                possibleEmail = account.name;
            }
        }
        return possibleEmail;
    }

    public String getUserPhoneNumber() {
        TelephonyManager tm = (TelephonyManager) LibreExchangeApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        return number;
    }


}
