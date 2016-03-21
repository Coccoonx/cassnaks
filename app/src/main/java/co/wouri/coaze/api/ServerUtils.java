package co.wouri.coaze.api;

import android.content.Context;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.wouri.coaze.api.netflow.Http;
import co.wouri.coaze.api.netflow.HttpFactory;
import co.wouri.coaze.api.netflow.HttpResponse;
import co.wouri.coaze.api.netflow.NetworkError;
import co.wouri.coaze.api.netflow.ResponseHandler;
import co.wouri.coaze.api.netflow.net.Web;
import co.wouri.coaze.core.models.Account;
import co.wouri.coaze.core.models.Document;
import co.wouri.coaze.core.models.Reference;
import co.wouri.coaze.storage.CoazeSettingsUtils;

/**
 * Created by lyonnel on 03/11/15.
 */
public class ServerUtils {

    private static final String TAG = "coaze server call";

    public static Account getAccount(Context context, String tokenType, String token, String uId) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Account[] account = {null};
        try {
            Http http = HttpFactory.create(context);
            http.get(Web.getUpdateAccountUrl())
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
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
                                                 account[0] = new Account();
                                                 account[0].setEmail(obj.getString("email"));
                                                 account[0].setPhoneNumber(obj.getString("phoneNumber"));
                                                 account[0].setFirstName(obj.getString("firstName"));
                                                 account[0].setLastName(obj.getString("lastName"));
                                                 account[0].setCity(obj.getString("city"));
                                                 account[0].setState(obj.getString("state"));
                                                 account[0].setCountry(obj.getString("country"));
                                                 account[0].setAddress(obj.getString("address"));
                                                 account[0].setSocialSecurityNumber(obj.getString("socialSecurityNumber"));
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
        return account[0];
    }


    public static Account updateAccount(Context context, Account account) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Account[] accounts = {null};

        String tokenType = CoazeSettingsUtils.getTokenType();
        String token = CoazeSettingsUtils.getAccessToken();
        String uId = CoazeSettingsUtils.getUserUid();
        try {
            Http http = HttpFactory.create(context);
            http.put(Web.getUpdateAccountUrl())
                    .header("Authorization", tokenType + " " + uId)
                    .header("Authorization", tokenType + " " + token)
                    .data(account)
                    .handler(new ResponseHandler() {
                                 @Override
                                 public void success(Object data, HttpResponse response) {
                                     super.success(data, response);
                                     Log.d(TAG, "HttpResponse header = " + response.getHeaders());

                                     if (data != null) {
                                         Log.d(TAG, "Account correctly retrieve " + data.toString());
                                         try {

                                             JSONObject obj = new JSONObject(data.toString());
                                             Account account1;

                                             if (obj != null) {
                                                 account1 = new Account();
                                                 account1.setId(obj.getString("id"));
                                                 account1.setCity(obj.getString("city"));
                                                 account1.setEmail(obj.getString("email"));
                                                 account1.setFirstName(obj.getString("firstName"));
                                                 account1.setLastName(obj.getString("lastName"));
                                                 account1.setPhoneNumber(obj.getString("phoneNumber"));
                                                 account1.setSocialSecurityNumber(obj.getString("socialSecurityNumber"));
                                                 account1.setState(obj.getString("state"));
                                                 account1.setCountry(obj.getString("country"));
                                                 accounts[0] = account1;
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
        return accounts[0];
    }


    public static Account createAccount(Context context,
                                        Account accountIncoming) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        final Account[] account = {null};
        try {
            Http http = HttpFactory.create(context);
            http.post(Web.getUpdateAccountUrl())
                    .data(accountIncoming)
                    .header("Authorization", Base64.encodeToString("acme:acmesecret1".getBytes(), Base64.DEFAULT))
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
                                                 account[0] = new Account();
                                                 account[0].setEmail(obj.getString("email"));
                                                 account[0].setPhoneNumber(obj.getString("phoneNumber"));
                                                 account[0].setFirstName(obj.getString("firstName"));
                                                 account[0].setLastName(obj.getString("lastName"));
                                                 account[0].setCity(obj.getString("city"));
                                                 account[0].setState(obj.getString("state"));
                                                 account[0].setCountry(obj.getString("country"));
                                                 account[0].setAddress(obj.getString("address"));
                                                 account[0].setSocialSecurityNumber(obj.getString("socialSecurityNumber"));
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
        return account[0];
    }


    public static JSONObject login(Context context) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String req = " grant_type = password & password = " + CoazeSettingsUtils.getUserPassword() + " & username = " + CoazeSettingsUtils.getUserEmail();


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
