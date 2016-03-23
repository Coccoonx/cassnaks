package co.wouri.libreexchange.core.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

import co.wouri.libreexchange.utils.CoreUtils;
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
    private ArrayList<String> phoneNumbers;
    private String imageUri;
    //    private String imageUri;
    private String state;

    public Recipient() {
        id = UUID.randomUUID().toString();
        phoneNumbers = new ArrayList<>();
    }


    public Recipient(String fName, String adress) {
        this();
        firstName = fName;
        address = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Recipient recipient = (Recipient) o;

        return !(id != null ? !id.equals(recipient.id) : recipient.id != null);

    }

    private void writeObject(ObjectOutputStream out) throws IOException {

        out.writeObject(id);
        out.writeObject(accountId);
        out.writeObject(firstName);
        out.writeObject(lastName);
        out.writeObject(email);
        out.writeObject(city);
        out.writeObject(country);
        out.writeObject(address);
        out.writeObject(phoneNumbers);
        out.writeObject(state);
        out.writeObject(imageUri);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        id = (String) in.readObject();
        accountId = (String) in.readObject();
        firstName = (String) in.readObject();
        lastName = (String) in.readObject();
        email = (String) in.readObject();
        city = (String) in.readObject();
        country = (String) in.readObject();
        address = (String) in.readObject();
        phoneNumbers = (ArrayList<String>) in.readObject();
        state = (String) in.readObject();
        imageUri = (String) in.readObject();


    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }


    protected class BitmapDataObject implements Serializable {
        private static final long serialVersionUID = 111696345129311948L;
        public byte[] imageByteArray;
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
        dest.writeStringList(this.phoneNumbers);
        dest.writeString(this.imageUri);
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
        this.phoneNumbers = in.createStringArrayList();
        this.imageUri = in.readString();
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
