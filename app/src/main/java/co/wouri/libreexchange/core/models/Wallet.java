package co.wouri.libreexchange.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * Created by lyonnel on 22/04/16.
 */
@Data
public class Wallet implements Serializable, Parcelable{

    private Double availableBalance;

    private Double unavailableBalance;

    private Customer customer;

    private Long id;

    private Date createDate;


    private Date lastUpdateDate;

    private Status status;

    private String currency;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeParcelable(this.customer, flags);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeLong(createDate != null ? createDate.getTime() : -1);
        dest.writeLong(lastUpdateDate != null ? lastUpdateDate.getTime() : -1);
        dest.writeString(this.currency);
        dest.writeValue(this.availableBalance);
        dest.writeValue(this.unavailableBalance);
    }


    public Wallet() {
    }

    protected Wallet(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.customer = in.readParcelable(Customer.class.getClassLoader());
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : Status.values()[tmpStatus];
        long tmpCreateDate = in.readLong();
        this.createDate = tmpCreateDate == -1 ? null : new Date(tmpCreateDate);
        long tmpLastUpdateDate = in.readLong();
        this.lastUpdateDate = tmpLastUpdateDate == -1 ? null : new Date(tmpLastUpdateDate);
        this.currency = in.readString();
        this.availableBalance = (Double) in.readValue(Double.class.getClassLoader());
        this.unavailableBalance = (Double) in.readValue(Double.class.getClassLoader());
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
