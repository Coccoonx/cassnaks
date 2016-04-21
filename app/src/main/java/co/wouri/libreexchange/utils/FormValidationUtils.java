package co.wouri.libreexchange.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by landryfoko on 11/11/15.
 */
public class FormValidationUtils {
    private static final String TAG = "Form Validation";

    public static boolean checkFirstName(Context context, String name){
        if(name==null || name.split(" ").length==0 || name.length()<=2){
            Toast.makeText(context, "Invalid firstName, the firstName must at least have 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean checkLastName(Context context, String name){
        if(name==null || name.split(" ").length==0 || name.length()<=2){
            Toast.makeText(context, "Invalid LastName, the LastName must at least have 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean checkCity(Context context,String city){
        if(city==null || city.split(" ").length==0 || city.length()<=2){
            Toast.makeText(context, "Invalid city, the city must at least have 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean checkAddress(Context context,String address){
        if(address==null || address.split(" ").length==0 || address.length()<=2){
            Toast.makeText(context, "Invalid address, the address must at least have 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean checkEmail(String email){
        Pattern pattern= Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches()){
            Log.d(TAG,"Invalid email");
            return false;
        }
        return true;
    }
    public static boolean checkPhone(String phone){
        String simpleRegex = "(^\\+[0-9]{3}([0-9])+)|([0-9]+)";
        String northAmericaPhoneNumber ="\\D*([2-9]\\d{2})(\\D*)([2-9]\\d{2})(\\D*)(\\d{4})\\D*";
        Pattern pattern= Pattern.compile(northAmericaPhoneNumber+"|"+simpleRegex);
        Matcher matcher = pattern.matcher(phone);
        if(!matcher.matches()){
            Log.d(TAG, "Invalid Phone number");
            return false;
        }
        return true;
    }
    public static boolean checkCountry(Context context,String country){
       if(country.equals("Choose a country")){
            Toast.makeText(context, "You must choose a country", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public static boolean checkPassword(Context context,String country){
        if(country==null || country.split(" ").length==0 || country.length()<=2){
            Toast.makeText(context, "The password must at least have 3 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
