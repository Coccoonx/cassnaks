package co.wouri.libreexchange.api;

import android.content.Context;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.wouri.libreexchange.api.netflow.Http;
import co.wouri.libreexchange.api.netflow.HttpFactory;
import co.wouri.libreexchange.api.netflow.HttpResponse;
import co.wouri.libreexchange.api.netflow.NetworkError;
import co.wouri.libreexchange.api.netflow.ResponseHandler;
import co.wouri.libreexchange.api.netflow.net.Web;
import co.wouri.libreexchange.core.managers.PrefUtils;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Customer;
import co.wouri.libreexchange.core.models.Document;
import co.wouri.libreexchange.core.models.Reference;
import co.wouri.libreexchange.core.models.Status;
import co.wouri.libreexchange.core.models.Wallet;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;

import static co.wouri.libreexchange.core.managers.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;

/**
 * Created by lyonnel on 03/11/15.
 */
public class ServerUtils {

    private static final String TAG = "coaze server call";


    public static Customer getCustomer(Context context, String userEmail, String userPassword) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d(TAG, "Base64.encodeToString = " + Base64.encodeToString((userEmail + ":" + userPassword).getBytes(), Base64.NO_WRAP));
        final Customer[] customer = {null};
        try {
            Http http = HttpFactory.create(context);
            http.get(Web.getAccountEndpointUrl())
                    .header("Authorization", "Basic " +
                            Base64.encodeToString((userEmail + ":" + userPassword).getBytes(), Base64.NO_WRAP))
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Profile correctly retrieve " + data.toString());

                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             if (obj != null) {
                                                 customer[0] = new Customer();
                                                 customer[0].setEmail(obj.getString("email"));
                                                 customer[0].setFirstName(obj.getString("firstName"));
                                                 customer[0].setLastName(obj.getString("lastName"));
                                                 customer[0].setCity(obj.getString("city"));
                                                 customer[0].setState(obj.getString("state"));
                                                 customer[0].setCountry(obj.getString("country"));
                                             }


                                             //responseFlag[0] = true;
                                         } catch (Exception e) {
                                             Log.d(TAG, "Exception encountered " + e.getMessage());
                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return customer[0];
    }


    public static Wallet getWallet(Context context, String userEmail, String userPassword) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.d(TAG, "Base64.encodeToString = " + Base64.encodeToString((userEmail + ":" + userPassword).getBytes(), Base64.NO_WRAP));
        final Wallet[] wallet = {null};
        try {
            Http http = HttpFactory.create(context);
            http.get(Web.getWalletEndpointUrl())
                    .header("Authorization", "Basic " +
                            Base64.encodeToString((userEmail + ":" + userPassword).getBytes(), Base64.NO_WRAP))
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Profile correctly retrieve " + data.toString());

                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             if (obj != null) {
                                                 String idString = obj.get("id").toString();
                                                 Log.d(TAG, "Id as a string " + idString);
                                                 Long id;
                                                 if (idString.split("\\.0")!=null){
                                                     id = Long.valueOf(Long.parseLong(idString.split("\\.0")[0]));
                                                 }else {
                                                     id = Long.valueOf(obj.getString("id"));
                                                 }
                                                 Log.d(TAG, "Id as long " + id);
                                                 wallet[0] = new Wallet();
                                                 wallet[0].setId(id);

                                                 //Converting a double to a Date
//                                                 DateFormat dateFormat = DateFormat.getDateInstance();
//                                                 String str_date=obj.get("lastUpdateDate").toString();
//                                                 DateFormat formatter ;
//                                                 Date date ;
//                                                 formatter = new SimpleDateFormat("yyyy-MM-dd");
//                                                 date = formatter.parse(str_date);

//                                                 Log.d(TAG, "Class of lastUpdateDouble " + obj.get("lastUpdateDate").getClass());
//                                                 Double lastUpdateDouble = (Double)obj.get("lastUpdateDate");
//                                                 double lastUpdate = lastUpdateDouble.doubleValue();
//
//                                                 long lastUpdateLong = (long) (lastUpdate * 1);
//                                                 Date lastUpdateDate = new Date(lastUpdateLong);

//                                                 Log.d(TAG, "lastUpdate Date " + date);
                                                 //Date lastUpdate = dateFormat.parse(lastUpdateDate);
//                                                 wallet[0].setLastUpdateDate(date);


//                                                 Double createDateDouble = (Double)obj.get("createDate");
//                                                 double createDate = createDateDouble.doubleValue();
//
//                                                 long createDateLong = (long) (createDate * 1);
//                                                 Date createDateDate = new Date(createDateLong);
//
//                                                 Log.d(TAG, "createDate Date " + createDateDate);
                                                 //Date lastUpdate = dateFormat.parse(lastUpdateDate);
//                                                 wallet[0].setCreateDate(createDateDate);
                                                 wallet[0].setAvailableBalance((Double)obj.get("availableBalance"));
                                                 wallet[0].setUnavailableBalance((Double) obj.get("unavailableBalance"));
                                                 wallet[0].setStatus(Status.valueOf(obj.getString("status")));
//                                                 wallet[0].setCustomer((Customer)obj.get("customer"));
                                                 wallet[0].setCurrency(obj.getString("currency"));
                                             }


                                             //responseFlag[0] = true;
                                         } catch (Exception e) {
                                             Log.d(TAG, "Exception encountered while parsing wallet " + Log.getStackTraceString(e));
                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return wallet[0];
    }

    public static Customer updateCustomer(Context context, Customer customer) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        final Customer[] customers = {null};
//
        String tokenType = LibreExchangeSettingsUtils.getTokenType();
        String token = LibreExchangeSettingsUtils.getAccessToken();
        String uId = LibreExchangeSettingsUtils.getUserUid();

        final String loggedInUserName = PrefUtils.getFromPrefs(context, PrefUtils.PREFS_LOGIN_USERNAME_KEY, "noUserName");
        final String loggedInUserPassword = PrefUtils.getFromPrefs(context, PREFS_LOGIN_PASSWORD_KEY, "noPassword");
        Log.d(TAG, "loggedInUserName = " + loggedInUserName);
        Log.d(TAG, "loggedInUserPassword = " + loggedInUserPassword);

        Log.d(TAG, "Base64.encodeToString = " + Base64.encodeToString((loggedInUserName + ":" + loggedInUserPassword).getBytes(), Base64.NO_WRAP));
        final String email = ProfileManager.getCurrentUserProfile().getCustomer().getEmail();
        String password = ProfileManager.getCurrentUserProfile().getCustomer().getPassword();
        final Customer[] customers = new Customer[1];
        customers[0] = new Customer();
        try {
            Http http = HttpFactory.create(context);
            http.put(Web.getAccountEndpointUrl())
                    .header("Authorization", "Basic " +
                            Base64.encodeToString((loggedInUserName + ":" + loggedInUserPassword).getBytes(), Base64.NO_WRAP))
                    .data(customer)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Customer correctly retrieve for update " + data.toString());
                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             Customer customer1;

                                             if (obj != null) {
                                                 customer1 = new Customer();
                                                 customer1.setCity(obj.getString("city"));
                                                 customer1.setEmail(loggedInUserName);
                                                 customer1.setFirstName(obj.getString("firstName"));
                                                 customer1.setLastName(obj.getString("lastName"));
                                                 customer1.setState(obj.getString("state"));
                                                 customer1.setCountry(obj.getString("country"));
                                                 customers[0] = customer1;
                                             }
                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return customers[0];
    }


    public static Customer createCustomer(Context context,
                                          Customer customerIncoming) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Customer[] customer = new Customer[1];
        customer[0] = new Customer();
        try {
            Http http = HttpFactory.create(context);
            http.post(Web.getAccountEndpointUrl())
                    .data(customerIncoming)
//                    .header("Authorization", Base64.encodeToString("acme:acmesecret1".getBytes(), Base64.DEFAULT))
//                    .header("ContentType", "application/json")
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Profile correctly retrieved " + data.toString());

                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             Log.d(TAG, "JSON of customer retrieved " + obj.toString());

                                             if (obj != null) {
                                                 String idString = obj.get("id").toString();
                                                 Long id;
                                                 if (idString.split("\\.0")!=null){
                                                     id = Long.valueOf(Long.parseLong(idString.split("\\.0")[0]));
                                                 }else {
                                                     id = Long.valueOf(obj.getString("id"));
                                                 }
                                                 customer[0].setId(id);
                                                 customer[0].setEmail(obj.getString("email"));
                                                 //customer[0].setPhone(obj.getString("phone"));
                                                 //customer[0].setZipCode(obj.getString("zipCode"));
                                                 customer[0].setPassword(obj.getString("password"));
                                                 //Converting a double to a Date
                                                 DateFormat dateFormat = DateFormat.getDateInstance();
                                                 Double lastUpdateDouble = (Double)obj.get("lastUpdateDate");
                                                 double lastUpdate = lastUpdateDouble.doubleValue();

                                                 long lastUpdateLong = (long) (lastUpdate * 1);
                                                 Date lastUpdateDate = new Date(lastUpdateLong);

                                                 Log.d(TAG, "lastUpdate Date " + lastUpdateDate);
                                                 //Date lastUpdate = dateFormat.parse(lastUpdateDate);
                                                 customer[0].setLastUpdateDate(lastUpdateDate);


                                                 Double createDateDouble = (Double)obj.get("createDate");
                                                 double createDate = createDateDouble.doubleValue();

                                                 long createDateLong = (long) (createDate * 1);
                                                 Date createDateDate = new Date(createDateLong);

                                                 Log.d(TAG, "createDate Date " + createDateDate);
                                                 //Date lastUpdate = dateFormat.parse(lastUpdateDate);
                                                 customer[0].setCreateDate(createDateDate);

                                                 customer[0].setFirstName(obj.getString("firstName"));
                                                 customer[0].setLastName(obj.getString("lastName"));
//                                                 customer[0].setCity(obj.getString("city"));
//                                                 customer[0].setState(obj.getString("state"));
                                                 customer[0].setCountry(obj.getString("country"));
//                                                 customer[0].setLanguage(obj.getString("language"));
                                                 customer[0].setEnabled(Boolean.valueOf(obj.getString("enabled")));
                                                 customer[0].setStatus(Status.valueOf(obj.getString("status")));
                                             }


                                             //responseFlag[0] = true;
                                         } catch (Exception e) {
                                             Log.d(TAG, "Exception encountered " + e.getMessage());
                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        Log.d(TAG, "Customer before exitting the createCustomer " + customer[0].toString());

        return customer[0];
    }


    public static JSONObject login(Context context) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String req = " grant_type = password & password = " + LibreExchangeSettingsUtils.getUserPassword() + " & username = " + LibreExchangeSettingsUtils.getUserEmail();


        final JSONObject[] objects = {null};
        try {
            Http http = HttpFactory.create(context);
            http.post(Web.getLoginUrl())
                    .header("Authorization", "Basic " + Base64.encodeToString("acme:acmesecret1".getBytes(), Base64.DEFAULT))
                    .data(req)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Profile correctly retrieve " + data.toString());

                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             objects[0] = obj;


                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return objects[0];
    }
//
//    public static List<Reference> getAllReferences(Context context, String tokenType, String token, String uId) {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        final List<Reference> references = new ArrayList<>();
//        try {
//            Http http = HttpFactory.create(context);
//            http.get(Web.getAllReferencesUrl() + "?pageSize=10&pageNumber=0")
//                    .header("Authorization", tokenType + " " + uId)
//                    .header("Authorization", tokenType + " " + token)
//                    .handler(new ResponseHandler() {
//                                 @Override
//                                 public void success(Object data, HttpResponse response) {
//                                     super.success(data, response);
//                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());
//
//                                     if (data != null) {
//                                         Log.d(TAG, "References correctly retrieve " + data.toString());
//                                         try {
//
//                                             JSONArray objects = new JSONObject(data.toString()).getJSONArray("content");
//                                             for (int i = 0; i < objects.length(); i++) {
//                                                 JSONObject obj = objects.getJSONObject(i);
//                                                 Reference reference;
//                                                 if (obj != null) {
//                                                     reference = new Reference();
//                                                     reference.setId(obj.getString("id"));
//                                                     reference.setAccountId(obj.getString("accountId"));
//                                                     reference.setEmail(obj.getString("email"));
//                                                     reference.setFirstName(obj.getString("firstName"));
//                                                     reference.setLastName(obj.getString("lastName"));
//                                                     reference.setPhoneNumber(obj.getString("phoneNumber"));
//                                                     references.add(reference);
//                                                 }
//                                                 Log.d(TAG, "References " + references);
//                                             }
//                                             //responseFlag[0] = true;
//                                         } catch (Exception e) {
//
//                                             Log.d(TAG, "Exception : " + Log.getStackTraceString(e));
//                                         }
//                                     }
//                                 }
//
//                                 @Override
//                                 public void error(String message, HttpResponse response) {
//                                     super.error(message, response);
//                                     //responseFlag[0] = false;
//                                     Log.d(TAG, message);
//                                 }
//
//                                 @Override
//                                 public void failure(NetworkError error) {
//                                     super.failure(error);
//                                     //responseFlag[0] = false;
//                                     Log.d(TAG, "Error " + error.firstName());
//                                 }
//
//                                 @Override
//                                 public void complete() {
//                                     super.complete();
//                                     Log.d(TAG, "Terminated");
//                                 }
//                             }
//
//                    ).
//
//                    send();
//        } catch (RuntimeException e) {
//            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
//        }
//        return references;
//    }

//
//    public static Reference createReference(Context context, String tokenType, String token, String uId, Reference reference) {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        final Reference[] references = {null};
//        try {
//            Http http = HttpFactory.create(context);
//            http.post(Web.getCreateReferenceUrl())
//                    .header("Authorization", tokenType + " " + uId)
//                    .header("Authorization", tokenType + " " + token)
//                    .data(reference)
//                    .handler(new ResponseHandler() {
//                                 @Override
//                                 public void success(Object data, HttpResponse response) {
//                                     super.success(data, response);
//                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());
//
//                                     if (data != null) {
//                                         Log.d(TAG, "References correctly retrieve " + data.toString());
//                                         try {
//
//                                             JSONObject obj = new JSONObject(data.toString());
//                                             Reference reference;
//                                             if (obj != null) {
//                                                 reference = new Reference();
//                                                 reference.setId(obj.getString("id"));
//                                                 reference.setAccountId(obj.getString("accountId"));
//                                                 reference.setEmail(obj.getString("email"));
//                                                 reference.setFirstName(obj.getString("firstName"));
//                                                 reference.setLastName(obj.getString("lastName"));
//                                                 reference.setPhoneNumber(obj.getString("phoneNumber"));
//                                                 references[0] = reference;
//                                             }
//                                             //responseFlag[0] = true;
//                                         } catch (Exception e) {
//
//                                         }
//                                     }
//                                 }
//
//                                 @Override
//                                 public void error(String message, HttpResponse response) {
//                                     super.error(message, response);
//                                     //responseFlag[0] = false;
//                                     Log.d(TAG, message);
//                                 }
//
//                                 @Override
//                                 public void failure(NetworkError error) {
//                                     super.failure(error);
//                                     //responseFlag[0] = false;
//                                     Log.d(TAG, "Error " + error.firstName());
//                                 }
//
//                                 @Override
//                                 public void complete() {
//                                     super.complete();
//                                     Log.d(TAG, "Terminated");
//                                 }
//                             }
//
//                    ).
//
//                    send();
//        } catch (RuntimeException e) {
//            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
//        }
//        return references[0];
//    }

    //    public static Reference updateReference(Context context, String tokenType, String token, String uId, Reference reference) {
//
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//
//        final Reference[] references = {null};
//        try {
//            Http http = HttpFactory.create(context);
//            http.put(Web.getCreateReferenceUrl())
//                    .header("Authorization", tokenType + " " + uId)
//                    .header("Authorization", tokenType + " " + token)
//                    .data(reference)
//                    .handler(new ResponseHandler() {
//                                 @Override
//                                 public void success(Object data, HttpResponse response) {
//                                     super.success(data, response);
//                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());
//
//                                     if (data != null) {
//                                         Log.d(TAG, "References correctly retrieve " + data.toString());
//                                         try {
//
//                                             JSONObject obj = new JSONObject(data.toString());
//                                             Reference reference;
//                                             if (obj != null) {
//                                                 reference = new Reference();
//                                                 reference.setId(obj.getString("id"));
//                                                 reference.setAccountId(obj.getString("accountId"));
//                                                 reference.setEmail(obj.getString("email"));
//                                                 reference.setFirstName(obj.getString("firstName"));
//                                                 reference.setLastName(obj.getString("lastName"));
//                                                 reference.setPhoneNumber(obj.getString("phoneNumber"));
//                                                 references[0] = reference;
//                                             }
//                                             //responseFlag[0] = true;
//                                         } catch (Exception e) {
//
//                                         }
//                                     }
//                                 }
//
//                                 @Override
//                                 public void error(String message, HttpResponse response) {
//                                     super.error(message, response);
//                                     //responseFlag[0] = false;
//                                     Log.d(TAG, message);
//                                 }
//
//                                 @Override
//                                 public void failure(NetworkError error) {
//                                     super.failure(error);
//                                     //responseFlag[0] = false;
//                                     Log.d(TAG, "Error " + error.firstName());
//                                 }
//
//                                 @Override
//                                 public void complete() {
//                                     super.complete();
//                                     Log.d(TAG, "Terminated");
//                                 }
//                             }
//                    ).
//
//                    send();
//        } catch (RuntimeException e) {
//            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
//        }
//        return references[0];
//    }
    public static JSONObject createLoan(Context context, String tokenType, String token, String uId, Object loan) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final JSONObject[] objects = {null};
        try {
            Http http = HttpFactory.create(context);
            http.post(Web.getCreateLoanUrl())
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
                    .data(loan)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "References correctly retrieve " + data.toString());
                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             if (obj != null) {

                                                 objects[0] = obj;
                                             }
                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }
                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return objects[0];
    }


    public static JSONObject submitLoan(Context context, String tokenType, String token, String uId, Object loan) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        final JSONObject[] objects = {null};
        try {
            Http http = HttpFactory.create(context);
            http.post(Web.getSubmitLoanUrl())
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
                    .data(loan)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "References correctly retrieve " + data.toString());
                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             if (obj != null) {

                                                 objects[0] = obj;
                                             }
                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }
                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return objects[0];
    }


    public static boolean deleteReference(Context context, String tokenType, String token, String uId, Reference reference) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Reference[] references = {null};
        try {
            Http http = HttpFactory.create(context);
            http.delete(Web.getDeleteReferenceUrl(reference))
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());
                                     Log.d(TAG, "References correctly retrieve " + data.toString());

                                     if (data != null) {
                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             Reference reference;
                                             if (obj != null) {
                                                 reference = new Reference();
                                                 reference.setId(obj.getString("id"));
                                                 reference.setAccountId(obj.getString("accountId"));
                                                 reference.setEmail(obj.getString("email"));
                                                 reference.setFirstName(obj.getString("firstName"));
                                                 reference.setLastName(obj.getString("lastName"));
                                                 reference.setPhoneNumber(obj.getString("phoneNumber"));
                                                 references[0] = reference;
                                             }
                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return true;
    }

    public static Document createDocument(Context context, String tokenType, String token, String uId, Document document) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Document[] documents = {null};
        try {
            Http http = HttpFactory.create(context);
            http.post(Web.getCreateReferenceUrl())
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
                    .data(document)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "References correctly retrieve " + data.toString());
                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             Document document1;
                                             if (obj != null) {
                                                 document1 = new Document();
                                                 document1.setId(obj.getString("id"));
                                                 document1.setDocumentName(obj.getString("documentName"));
                                                 documents[0] = document1;
                                             }
                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return documents[0];
    }

    public static List<Document> getAllDocuments(Context context, String tokenType, String token, String uId) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final List<Document> documents = new ArrayList<>();
        try {
            Http http = HttpFactory.create(context);
            http.get(Web.getAllDocuments() + "?pageSize=10&pageNumber=0")
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Documents correctly retrieve " + data.toString());
                                         try {

                                             JSONArray objects = new JSONObject(data.toString()).getJSONArray("content");
                                             for (int i = 0; i < objects.length(); i++) {
                                                 JSONObject obj = objects.getJSONObject(i);
                                                 Document document;
                                                 if (obj != null) {
                                                     document = new Document();
                                                     document.setId(obj.getString("id"));
                                                     document.setDocumentName(obj.getString("documentName"));
                                                     documents.add(document);
                                                 }
                                             }
                                             //responseFlag[0] = true;
                                         } catch (Exception e) {

                                         }
                                     }
                                 }

                                 @Override
                                 public void error(String message, HttpResponse response) {
                                     super.error(message, response);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, message);
                                 }

                                 @Override
                                 public void failure(NetworkError error) {
                                     super.failure(error);
                                     //responseFlag[0] = false;
                                     Log.d(TAG, "Error " + error.name());
                                 }

                                 @Override
                                 public void complete() {
                                     super.complete();
                                     Log.d(TAG, "Terminated");
                                 }
                             }

                    ).

                    send();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error while sending feedback " + Log.getStackTraceString(e));
        }
        return documents;
    }


}
