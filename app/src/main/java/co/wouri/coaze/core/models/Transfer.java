package co.wouri.coaze.core.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import lombok.Data;


@Data
public class Transfer implements Serializable, Parcelable {


    private String id;
    private String transferType;
    private String accountId;
    private
    @NotNull
    Double amount;

    private Double recieverAmount;
    private
    @NotEmpty
    String senderCurrency;
    private
    @NotEmpty
    String receiverCurrency;
    private Date creationDate = new Date();
    private List<Note> notes = new ArrayList<>();
    private
    @NotNull
    Recipient recipient;
    private TransferStatus status;


    public Transfer() {
        id = UUID.randomUUID().toString();

    }

    public Transfer(Recipient recipient, String senderCurrency, String receiverCurrency, Double recieverAmount) {
        this();
        this.recipient = recipient;
        this.senderCurrency = senderCurrency;
        this.receiverCurrency = receiverCurrency;
        this.recieverAmount = recieverAmount;

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.transferType);
        dest.writeString(this.accountId);
        dest.writeValue(this.amount);
        dest.writeValue(this.recieverAmount);
        dest.writeString(this.senderCurrency);
        dest.writeString(this.receiverCurrency);
        dest.writeLong(creationDate != null ? creationDate.getTime() : -1);
        dest.writeList(this.notes);
        dest.writeParcelable(this.recipient, 0);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
    }

    protected Transfer(Parcel in) {
        this.id = in.readString();
        this.transferType = in.readString();
        this.accountId = in.readString();
        this.amount = (Double) in.readValue(Double.class.getClassLoader());
        this.recieverAmount = (Double) in.readValue(Double.class.getClassLoader());
        this.senderCurrency = in.readString();
        this.receiverCurrency = in.readString();
        long tmpCreationDate = in.readLong();
        this.creationDate = tmpCreationDate == -1 ? null : new Date(tmpCreationDate);
        this.notes = new ArrayList<Note>();
        in.readList(this.notes, List.class.getClassLoader());
        this.recipient = in.readParcelable(Recipient.class.getClassLoader());
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : TransferStatus.values()[tmpStatus];
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
