package co.wouri.libreexchange.core.models;

import android.accounts.AccountManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.regex.Pattern;

import co.wouri.libreexchange.LibreExchangeApplication;
import lombok.Data;


@Data
public class Account implements Serializable, Parcelable {
    private String name;
    private String token;
    private String address;
    private double balance;

    private Customer customer;

    private String extension;

    private String username;
    private
    @NotEmpty
    String password;
    private String description;
    private Boolean isDefaultDepositAccount;
    private Boolean isDefaultWithdrawalAccount;
    private Boolean acceptsDeposit;
    private Boolean acceptsWithdraw;
    private String swift;
    private String iban;
    private String bankName;



    private String id;
    private
    @NotEmpty
    String firstName;
    private
    @NotEmpty
    String lastName;

    private
    @NotEmpty
    String email;
    private
    @NotEmpty
    String phoneNumber;
    private String state;
    private AccountStatus status;
    private String socialSecurityNumber;
    private
    @NotEmpty
    String city, country;
    private Float loanLimit = 200.0f;
    private String preferedSourceCurrency;
    private String preferedTargetCurrency;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.token);
        dest.writeString(this.address);
        dest.writeDouble(this.balance);
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.password);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.state);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeString(this.socialSecurityNumber);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeValue(this.loanLimit);
        dest.writeString(this.preferedSourceCurrency);
        dest.writeString(this.preferedTargetCurrency);
    }

    public Account() {
        email = getUserEmail();
        phoneNumber = getPhoneNumber();

    }

    protected Account(Parcel in) {
        this.name = in.readString();
        this.token = in.readString();
        this.address = in.readString();
        this.balance = in.readDouble();
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.state = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : AccountStatus.values()[tmpStatus];
        this.socialSecurityNumber = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.loanLimit = (Float) in.readValue(Float.class.getClassLoader());
        this.preferedSourceCurrency = in.readString();
        this.preferedTargetCurrency = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

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
