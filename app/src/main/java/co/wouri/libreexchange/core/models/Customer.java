package co.wouri.libreexchange.core.models;

import android.accounts.AccountManager;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.TelephonyManager;
import android.util.Patterns;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Pattern;

import co.wouri.libreexchange.LibreExchangeApplication;
import lombok.Data;


@Data
public class Customer implements Serializable, Parcelable {

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

    public Customer(){

    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.wallet, flags);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeLong(createDate != null ? createDate.getTime() : -1);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1);
        dest.writeString(this.city);
        dest.writeString(this.firstName);
        dest.writeString(this.language);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeString(this.zipCode);
        dest.writeString(this.password);
        dest.writeValue(this.enabled);
    }

    protected Customer(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.wallet = in.readParcelable(Wallet.class.getClassLoader());
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
        long tmpCreateDate = in.readLong();
        this.createDate = tmpCreateDate == -1 ? null : new Date(tmpCreateDate);
        long tmpLastUpdateDate = in.readLong();
        this.lastUpdateDate = tmpLastUpdateDate == -1 ? null : new Date(tmpLastUpdateDate);
        this.city = in.readString();
        this.firstName = in.readString();
        this.language = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.zipCode = in.readString();
        this.password = in.readString();
        this.enabled = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
