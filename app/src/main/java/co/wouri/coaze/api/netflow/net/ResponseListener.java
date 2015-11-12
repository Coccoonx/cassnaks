package co.wouri.coaze.api.netflow.net;

/**
 * Response listener which need to implemented by requesting classes
 *
 * @author neelam goyal
 */
public interface ResponseListener {

    public static final int REQUEST_CREATE_ACCOUNT = 1;
    public static final int REQUEST_CREATE_LOGIN = 2;
    public static final int REQUEST_UPDATE_ACCOUNT = 3;
    public static final int REQUEST_GET_ACCOUNT = 4;

    public static final int REQUEST_GET_ALL_REFERENCES = 5;
    public static final int REQUEST_CREATE_REFERENCES = 6;
    public static final int REQUEST_DELETE_REFERENCE = 7;

    public static final int REQUEST_GET_ALL_DOCUMENTS = 8;

    public static final int REQUEST_CREATE_LOAN = 9;

    /**
     * Callback
     */
    public boolean onResponse(final Response r, final int rid);

    public void noNetwork();
}
