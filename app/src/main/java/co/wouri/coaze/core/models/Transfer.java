package co.wouri.coaze.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.UUID;

import lombok.Data;


@Data
public class Transfer implements Serializable, Parcelable {
    private String transferId;
    private double amount;
    private String senderCurrency;
    private String receiverCurrency;
    private double receiverAmount;
    private double senderAmount;
    private String sendDate;
    private String notifiedDate;
    private String receivedDate;
    private Recipient recipient;
    private String notes;
    private String transferType;


    public Transfer() {
        transferId = UUID.randomUUID().toString();

    }

    public Transfer(Recipient recipient, String senderCurrency, String receiverCurrency) {
        this.recipient = recipient;
        this.senderCurrency = senderCurrency;
        this.receiverCurrency = receiverCurrency;
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
        dest.writeString(this.receiverCurrency);
        dest.writeDouble(this.receiverAmount);
        dest.writeString(this.senderCurrency);
        dest.writeDouble(this.senderAmount);
        dest.writeString(this.sendDate);
        dest.writeString(this.notifiedDate);
        dest.writeString(this.receivedDate);
        dest.writeParcelable(this.recipient, 0);
        dest.writeString(this.notes);
        dest.writeString(this.transferType);
    }

    protected Transfer(Parcel in) {
        this.transferId = in.readString();
        this.amount = in.readDouble();
        this.receiverCurrency = in.readString();
        this.receiverAmount = in.readDouble();
        this.senderCurrency = in.readString();
        this.senderAmount = in.readDouble();
        this.sendDate = in.readString();
        this.notifiedDate = in.readString();
        this.receivedDate = in.readString();
        this.recipient = in.readParcelable(Recipient.class.getClassLoader());
        this.notes = in.readString();
        this.transferType = in.readString();
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
