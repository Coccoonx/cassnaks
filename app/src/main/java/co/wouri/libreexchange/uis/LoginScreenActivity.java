package co.wouri.libreexchange.uis;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kbeanie.pinscreenlibrary.views.PinEntryAuthenticationListener;
import com.kbeanie.pinscreenlibrary.views.PinEntrySetupListener;
import com.kbeanie.pinscreenlibrary.views.PinView;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;

import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Customer;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

public class LoginScreenActivity extends Activity implements LoadingTaskFinishedListener, PinEntryAuthenticationListener {

    private static final String TAG = "LoginScreenActivity";
    TextView appName;
    TextView slogan;
    ImageView menu;
    EditText email;
    EditText password;
    EditText passwordConf;
    PinView pinView;
    TextView loginWithEmailOrPin;
    TextView signUp;
    LinearLayout loginForm;
    EditText firstName;
    EditText lastName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the activity_splash screen
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initComponents() {

        appName = (TextView) findViewById(R.id.appName);
        slogan = (TextView) findViewById(R.id.appSlogan);

        email = (EditText) findViewById(R.id.userEmailLogin);
        password = (EditText) findViewById(R.id.userPasswordLogin);

        firstName = (EditText) findViewById(R.id.userFirstName);
        lastName = (EditText) findViewById(R.id.userLastName);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password,firstName,lastName);
        pinView = (PinView) findViewById(R.id.pinView);
        loginWithEmailOrPin = (TextView) findViewById(R.id.loginEmail);
        signUp = (TextView) findViewById(R.id.signUp);

        loginForm = (LinearLayout) findViewById(R.id.loginForm);

        pinView.setModeAuthenticate(this);

        loginWithEmailOrPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginWithEmailOrPin.getText().equals("Log in with email ")){
                    loginWithEmailOrPin.setText("Log in with pin ");
                    pinView.setVisibility(View.GONE);
                    loginForm.setVisibility(View.VISIBLE);
                }else{
                    loginWithEmailOrPin.setText("Log in with email ");
                    pinView.setVisibility(View.VISIBLE);
                    loginForm.setVisibility(View.GONE);
                }

            }
        });

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password, passwordConf);

    }

    @Override
    public void onPinCorrect() {
        finish();
    }

    @Override
    public void onPinWrong() {

    }

    // This is the callback for when your async task has finished
    @Override
    public void onTaskFinished() {
        completeSplash();
    }

    private void completeSplash() {
        runAnimation();
    }

    private void runAnimation() {
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        menu.startAnimation(animationFadeIn);
        animationFadeIn.setFillAfter(true);
        animationFadeOut.setFillAfter(true);
    }


    void initUI() {
        initComponents();

    }

    private void startApp() {
        Intent intent;
//        if (!LibreExchangeSettingsUtils.getUserEmail().equals("")) {
        intent = new Intent(LoginScreenActivity.this, MainActivity.class);
//        } else
//            intent = new Intent(LoginScreenActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void performSubmit(View view) {
//        startApp();
        String emailVal = email.getText().toString().trim();
        String passwordVal = password.getText().toString();
        String firstName = email.getText().toString().trim();
        String lastName = password.getText().toString();
//        String country = this.getResources().getConfiguration().locale.getCountry();
        String country = "CA";
//        TelephonyManager tMgr = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
//        String mPhoneNumber = tMgr.getLine1Number();
//        if (FormValidationUtils.checkEmail(emailVal)) {
//            if (passwordVal.equals(passwordCVal)) {
        Customer userCustomer = new Customer();
        userCustomer.setEmail(emailVal);
                userCustomer.setPassword(passwordVal);
        userCustomer.setCountry(country);
        userCustomer.setFirstName(firstName);
        userCustomer.setLastName(lastName);
                Customer customer = ServerUtils.createCustomer(this, userCustomer);
                Log.d(TAG, "Customer created: "+customer);
                if (customer != null) {
                    ProfileManager.getCurrentUserProfile().setCustomer(customer);
                    ProfileManager.saveProfile();
                    LibreExchangeSettingsUtils.setUserEmail(emailVal);
                    startApp();
                }
//            } else
//                Toast.makeText(LoginScreenActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
//        } else
//            Toast.makeText(LoginScreenActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
    }
}