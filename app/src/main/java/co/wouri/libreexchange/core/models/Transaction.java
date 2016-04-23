package co.wouri.libreexchange.core.models;

import java.util.Date;

public class Transaction {

    private Long id;

    private String currency;

    private String comment;

    private Float amount=0f;

    private static final long serialVersionUID = 1L;

    private String transactionSubType;

    private TransactionType transactionType=TransactionType.Transfer;

    private Status status;

    private Date createDate=new Date();

    private Account recipient;

    private Account originator;

    private Date lastUpdateDate=new Date();

    private Customer originatorCustomer;

    private Customer recipientCustomer;
}
