package co.wouri.coaze.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

import lombok.Data;


@Data
public class Account implements Serializable, Parcelable {
    private String name;
    private String token;
    private String address;
    private double balance;


    private String id;
    private
    @NotEmpty
    String deviceId;
    private
    @NotEmpty
    String firstName;
    private
    @NotEmpty
    String lastName;
    private
    @NotEmpty
    String password;
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
        dest.writeString(this.deviceId);
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
    }

    protected Account(Parcel in) {
        this.name = in.readString();
        this.token = in.readString();
        this.address = in.readString();
        this.balance = in.readDouble();
        this.id = in.readString();
        this.deviceId = in.readString();
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
}
