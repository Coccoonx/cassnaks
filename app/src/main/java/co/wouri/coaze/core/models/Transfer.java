package co.wouri.coaze.core.models;

import java.util.Date;

import lombok.Data;

/**
 * Created by prisca on 10/11/15.
 */
@Data
public class Transfer {
    private String transferId;
    private Float amount;
    private String senderCurrency;
    private String receiverCurrency;
    private Date sendDate= new Date();
    private Date notifyDate= new Date();
    private Recipient recipient;
    private String notes;
}
