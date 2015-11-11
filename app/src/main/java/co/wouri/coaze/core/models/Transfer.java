package co.wouri.coaze.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import lombok.Data;


@Data
public class Transfer implements Serializable, Parcelable {
    private String transferId;
    private double amount;
    private String senderCurrency;
    private String receiverCurrency;
    private Date sendDate = new Date();
    private Date notifyDate = new Date();
    private Recipient recipient;
    private String notes;


    public Transfer() {
        transferId = UUID.randomUUID().toString();

    }

    public Transfer(Recipient recipient, String senderCurrency, String receiverCurrency){
        this.recipient = recipient;
        this.senderCurrency = senderCurrency;
        this.receiverCurrency= receiverCurrency;
        this.transferId = UUID.randomUUID().toString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.transferId);
        dest.writeDouble(this.amount);
        dest.writeString(this.senderCurrency);
        dest.writeString(this.receiverCurrency);
        dest.writeLong(sendDate != null ? sendDate.getTime() : -1);
        dest.writeLong(notifyDate != null ? notifyDate.getTime() : -1);
        dest.writeParcelable(this.recipient, 0);
        dest.writeString(this.notes);
    }

    protected Transfer(Parcel in) {
        this.transferId = in.readString();
        this.amount = in.readDouble();
        this.senderCurrency = in.readString();
        this.receiverCurrency = in.readString();
        long tmpSendDate = in.readLong();
        this.sendDate = tmpSendDate == -1 ? null : new Date(tmpSendDate);
        long tmpNotifyDate = in.readLong();
        this.notifyDate = tmpNotifyDate == -1 ? null : new Date(tmpNotifyDate);
        this.recipient = in.readParcelable(Recipient.class.getClassLoader());
        this.notes = in.readString();
    }

    public static final Creator<Transfer> CREATOR = new Creator<Transfer>() {
        public Transfer createFromParcel(Parcel source) {
            return new Transfer(source);
        }

        public Transfer[] newArray(int size) {
            return new Transfer[size];
        }
    };
}
