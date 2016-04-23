package co.wouri.libreexchange.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by lyonnel on 22/04/16.
 */
public class Wallet implements Serializable, Parcelable{
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Wallet() {
    }

    protected Wallet(Parcel in) {
    }

    public static final Creator<Wallet> CREATOR = new Creator<Wallet>() {
        public Wallet createFromParcel(Parcel source) {
            return new Wallet(source);
        }

        public Wallet[] newArray(int size) {
            return new Wallet[size];
        }
    };
}
