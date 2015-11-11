package co.wouri.coaze.core.models;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;


@Data
public class Transfer implements Serializable {
    private String transferId;
    private Float amount;
    private String senderCurrency;
    private String receiverCurrency;
    private Date sendDate = new Date();
    private Date notifyDate = new Date();
    private Recipient recipient;
    private String notes;
}
