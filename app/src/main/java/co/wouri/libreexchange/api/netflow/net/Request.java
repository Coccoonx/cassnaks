package co.wouri.libreexchange.api.netflow.net;

public class Request {

    public String url;
    public boolean auth;
    public String token;
    public String method;
    public String data;
    public ResponseListener r;
    public int rid;
    public String userid;
    public String tokenType;


    public Request(String url, boolean auth, String token,
                   String method, String data, ResponseListener r,
                   int rid) {
        this.url = url;
        this.auth = auth;
        this.method = method;
        this.token = token;
        this.data = data;
        this.r = r;
        this.rid = rid;
    }

    public Request(String url, boolean auth, String token,
                   String method, String data, ResponseListener r,
                   int rid, String userid, String tokenType) {
        this.url = url;
        this.auth = auth;
        this.method = method;
        this.token = token;
        this.data = data;
        this.r = r;
        this.rid = rid;
        this.userid = userid;
        this.tokenType = tokenType;
    }


}
