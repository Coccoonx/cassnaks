package co.wouri.libreexchange.api.netflow.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import co.wouri.libreexchange.LibreExchangeApplication;
import co.wouri.libreexchange.core.models.Reference;


public class Web {

    //    public static final String IP = "52.7.32.245";
    public static final String IP = "192.168.1.3";
    public static final String PORT = "9999";
    public static final String LOGIN_PORT = "9000";


    private Web() {
        // TODO Auto-generated constructor stub
    }

    public static String getCreateAccountUrl() {
        return "http://" + IP + ":" + LOGIN_PORT + "/account/";
    }

    public static String getLoginUrl() {
        return "http://" + IP + ":" + PORT + "/uaa/oauth/token/";
    }

    public static String getUpdateAccountUrl() {
        return "http://" + IP + ":" + LOGIN_PORT + "/account/";
    }

    public static String getAllReferencesUrl() {
        return "http://" + IP + ":" + LOGIN_PORT + "/reference";
    }

    public static String getCreateReferenceUrl() {
        return "http://" + IP + ":" + LOGIN_PORT + "/reference/";
    }

    public static String getDeleteReferenceUrl(Reference reference) {
        return "http://" + IP + ":" + LOGIN_PORT + "/reference/" + reference.getId();
    }

    public static String getAllDocuments() {
        return "http://" + IP + ":" + LOGIN_PORT + "/document/";
    }


    //Loan Management
    public static String getSubmitLoanUrl() {
        return "http://" + IP + ":" + LOGIN_PORT + "/loan/submit/";
    }

    public static String getCreateLoanUrl() {
        return "http://" + IP + ":" + LOGIN_PORT + "/loan/";
    }

    //    /**
//     * This method is responsible for performing and post based request on
//     * background thread. Once it receive data from server sends response back
//     * on response listener, along with request id
//     *
//     * @param url  URL to post data
//     * @param data actual data to be posed
//     * @param r    response listener to be called on response or error from
//     *             server
//     * @param rid  request ID
//     */
    public static void requestAsynData(final Request r) {
        Runnable rn = new Runnable() {
            public void run() {
                if (!Web.isNetworkAvailable(LibreExchangeApplication.getInstance()
                        .getApplicationContext())) {
                    r.r.noNetwork();
                    return;
                }
                r.r.onResponse(getResponse(r), r.rid);

            }
        };

        new Thread(rn).start();
    }

    /**
     * Method to check network connection is available or not.......
     *
     * @param act
     * @return
     */
    public static boolean isNetworkAvailable(Context act) {
        ConnectivityManager connectivityManager = (ConnectivityManager) act
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    //
    public static Response getResponse(Request r) {
        Response response = null;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) new URL(r.url).openConnection();
            if (r.auth)
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            else
                con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            if (r.data != null)
                con.setRequestProperty("Content-Length", "" + r.data.length());
            if (r.auth)
                con.setRequestProperty("Authorization", "Basic " + Base64.encodeToString("acme:acmesecret1".getBytes(), Base64.DEFAULT));

            if (r.token != null) {
                con.setRequestProperty("X-AUTH-TOKEN", r.token);
                //con.setRequestProperty("Authorization", "Client-ID " + r.userid);
                //con.setRequestProperty("Authorization", r.tokenType + " " + r.userid);
                //con.setRequestProperty("Authorization", r.tokenType + " " + r.token);
            }
            con.setRequestMethod(r.method);
            // con.setDoInput(true);
            con.connect();
            if (r.data != null) {
                OutputStream out = con.getOutputStream();
                out.write(r.data.getBytes());
                out.flush();
                out.close();
            }
            int status = 200;// con.getResponseCode();
            if (status == 200) {
                InputStream in = con.getInputStream();
                String resp = getString(in);
                if (resp == null || resp.equals("")) {

                    response = new Response("", false);
                } else {

                    response = new Response(resp, false);
                }
            }
        } catch (Exception e) {
            response = new Response("Error", true);
        } finally {
            try {
                con.disconnect();

            } catch (Exception e2) {
                // TODO: handle exception
            }
        }

        return response;
    }

    /**
     * Read complete fetched data from server into string
     */

    private static String getString(InputStream in) {
        StringBuffer r = new StringBuffer();

        try {

            InputStreamReader isr = new InputStreamReader(in, "UTF-8");
            BufferedReader reader = new BufferedReader(isr);
            String line = null;
            while ((line = reader.readLine()) != null)
                r.append(line);
            isr.close();
            reader.close();
            return r.toString();
        } catch (Exception e) {
        }

        return null;
    }
}
