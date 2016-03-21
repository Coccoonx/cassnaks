package co.wouri.coaze.api.netflow.net;

import org.json.JSONObject;

/**
 * @author neelam goyal
 */
public class Response {

    public JSONObject obj;
    String data;
    boolean is406;

    public int status;


    /**
     * Creates a new Response object.
     *
     * @param data json data
     */
    public Response(String data, boolean is406) {
        this.data = data;
        this.is406 = is406;
        if (is406)
            return;

        try {
            obj = new JSONObject(data);
        } catch (Exception e) {
        }
    }

    public Response(String data, int status) {
        this.status = status;
    }


    /**
     * Checks for error from server json response root note
     *
     * @return true if error is returned from server
     */
    public boolean isError() {
        try {
            if (data == null) {
                return true;
            } else {

                return false;//! obj.getBoolean("success");


            }
        } catch (Exception e) {
            // Need to support old format too


        }

        return true;
    }

    public String getErrorMessage() {
        try {
            return obj.getString("info");
        } catch (Exception ex) {

        }
        return "Service not Avaialble";
    }

    public boolean is406() {
        return is406;
    }

}
