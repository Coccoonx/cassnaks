package co.wouri.coaze.core.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by edwidge on 05/11/15.
 */
public class Person implements Parcelable {
    private String name;
    private Bitmap photoId;
    private int amount;
    private String transfertType;
    private String transfertDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Bitmap photoId) {
        this.photoId = photoId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getTransfertType() {
        return transfertType;
    }

    public void setTransfertType(String transfertType) {
        this.transfertType = transfertType;
    }

    public String getTransfertDate() {
        return transfertDate;
    }

    public void setTransfertDate(String transfertDate) {
        this.transfertDate = transfertDate;
    }

    public Person(String name, Bitmap photoId, int amount, String transfertType, String transfertDate) {
        this.name = name;
        this.photoId = photoId;
        this.amount = amount;
        this.transfertType = transfertType;
        this.transfertDate = transfertDate;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", photoId=" + photoId +
                ", amount=" + amount +
                ", transfertType='" + transfertType + '\'' +
                ", transfertDate='" + transfertDate + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(this.id);
        dest.writeString(this.name);
        photoId.writeToParcel(dest, flags);
//        dest.writeParcelable(this.photoId, 0);
        dest.writeString(this.transfertType);
        dest.writeString(this.transfertDate);
        dest.writeInt(this.amount);
    }

    protected Person(Parcel in) {
//        this.id = in.readString();
        this.name = in.readString();
        this.photoId = (Bitmap) in.readParcelable(Bitmap.class.getClassLoader());
        this.amount = in.readInt();
        this.transfertType = in.readString();
        this.transfertDate = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

}
