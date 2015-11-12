package co.wouri.coaze.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class Recipient implements Serializable, Parcelable {
    private String id;
    private String accountId;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private String country;
    private String address;
    private String phoneNumber;
    transient private int image;
    private String state;

    public Recipient() {
        id = UUID.randomUUID().toString();
    }


    public Recipient(String fName, String adress) {
        this();
        firstName = fName;
        address = adress;
    }

    public Recipient(int image, String fName) {
        this();
        this.image = image;
        firstName = fName;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipient recipient = (Recipient) o;

        return !(id != null ? !id.equals(recipient.id) : recipient.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.accountId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.email);
        dest.writeString(this.city);
        dest.writeString(this.country);
        dest.writeString(this.address);
        dest.writeString(this.phoneNumber);
        dest.writeInt(this.image);
        dest.writeString(this.state);
    }

    protected Recipient(Parcel in) {
        this.id = in.readString();
        this.accountId = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.email = in.readString();
        this.city = in.readString();
        this.country = in.readString();
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.image = in.readInt();
        this.state = in.readString();
    }

    public static final Creator<Recipient> CREATOR = new Creator<Recipient>() {
        public Recipient createFromParcel(Parcel source) {
            return new Recipient(source);
        }

        public Recipient[] newArray(int size) {
            return new Recipient[size];
        }
    };
}
