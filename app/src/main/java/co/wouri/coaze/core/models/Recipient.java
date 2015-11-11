package co.wouri.coaze.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;

@Data
public class Recipient implements Serializable, Parcelable {
    private String recipientId;
    private String email;
    private String city;
    private String state;
    private String country;
    private String address;
    private String phoneNumber;
    private String name;
    private String accountId;
    private byte[] userPicture;
    private String currency;
    private int image;


    public Recipient(String fName, String adress) {
        name = fName;
        address = adress;
        recipientId = UUID.randomUUID().toString();

    }

   /* public Recipient(int image, String lName, String adress) {
        this.image = image;
        lastName = lName;
        address = adress;
        recipientId = UUID.randomUUID().toString();

    }*/

    public Recipient(int image, String fName) {
        this.image = image;
        name = fName;
        recipientId = UUID.randomUUID().toString();

    }


    public Recipient() {
        recipientId = UUID.randomUUID().toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.recipientId);
        dest.writeString(this.email);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.country);
        dest.writeString(this.address);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.name);
        dest.writeString(this.accountId);
        dest.writeByteArray(this.userPicture);
        dest.writeString(this.currency);
        dest.writeInt(this.image);
    }

    protected Recipient(Parcel in) {
        this.recipientId = in.readString();
        this.email = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.country = in.readString();
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.name = in.readString();
        this.accountId = in.readString();
        this.userPicture = in.createByteArray();
        this.currency = in.readString();
        this.image = in.readInt();
    }

    public static final Parcelable.Creator<Recipient> CREATOR = new Parcelable.Creator<Recipient>() {
        public Recipient createFromParcel(Parcel source) {
            return new Recipient(source);
        }

        public Recipient[] newArray(int size) {
            return new Recipient[size];
        }
    };
}
