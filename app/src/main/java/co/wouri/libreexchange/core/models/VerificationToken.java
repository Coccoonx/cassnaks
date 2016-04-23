package co.wouri.libreexchange.core.models;

import java.util.Date;

public class VerificationToken {

    private Long id;

    private String token;

    private Long customerId;

    private Date createDate=new Date();
}
