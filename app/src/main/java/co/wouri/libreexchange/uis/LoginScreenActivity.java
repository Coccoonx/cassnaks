package co.wouri.libreexchange.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kbeanie.pinscreenlibrary.views.PinEntryAuthenticationListener;
import com.kbeanie.pinscreenlibrary.views.PinEntrySetupListener;
import com.kbeanie.pinscreenlibrary.views.PinView;
import co.wouri.libreexchange.R;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

public class LoginScreenActivity extends Activity implements LoadingTaskFinishedListener, PinEntryAuthenticationListener, PinEntrySetupListener {

    private static final String TAG = "LoginScreenActivity";
    TextView appName;
    TextView slogan;
    ImageView menu;
    EditText email;
    EditText password;
    PinView pinView;
    TextView loginWithEmailOrPin;
    TextView signUp;
    RelativeLayout loginForm;
    LinearLayout loginChoices;
    EditText firstName;
    EditText lastName;

    private boolean inSignUpForm = false;

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

        pinView = (PinView) findViewById(R.id.pinView);
        loginWithEmailOrPin = (TextView) findViewById(R.id.loginEmail);
        signUp = (TextView) findViewById(R.id.signUp);

        loginForm = (RelativeLayout) findViewById(R.id.loginForm);
        loginChoices = (LinearLayout) findViewById(R.id.loginChoices) ;

        pinView.setModeAuthenticate(this);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password,firstName,lastName);
        loginWithEmailOrPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inSignUpForm = false;
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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inSignUpForm = true;
                pinView.setVisibility(View.GONE);
                loginChoices.setVisibility(View.GONE);
                loginForm.setVisibility(View.VISIBLE);
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
            }
        });

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password);

    }

    @Override
    public void onPinEntered(String pin) {

    }

    @Override
    public void onPinConfirmed(String pin) {

    }

    @Override
    public void onPinMismatch() {

    }

    @Override
    public void onPinSet(String pin) {
        startApp();
    }

    @Override
    public void onPinCorrect() {
        startApp();
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
        Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void performSubmit(View view) {
        loginForm.setVisibility(View.GONE);
        pinView.setVisibility(View.VISIBLE);
        if(inSignUpForm) {
            //        String emailVal = email.getText().toString().trim();
//        String passwordVal = password.getText().toString();
//        String firstName = email.getText().toString().trim();
//        String lastName = password.getText().toString();
//        String country = "CA";
//        Customer userCustomer = new Customer();
//        userCustomer.setEmail(emailVal);
//        userCustomer.setPassword(passwordVal);
//        userCustomer.setCountry(country);
//        userCustomer.setFirstName(firstName);
//        userCustomer.setLastName(lastName);
//        Customer customer = ServerUtils.createCustomer(this, userCustomer);
//        Log.d(TAG, "Customer created: "+customer);
//        if (customer != null) {
//            ProfileManager.getCurrentUserProfile().setCustomer(customer);
//            ProfileManager.saveProfile();
//            LibreExchangeSettingsUtils.setUserEmail(emailVal);
//            pinView.setModeSetup(this);
//        }
            pinView.setModeSetup(this);
        }else{
            // Test if Customer exists in the database and then logged in
            startApp();
        }


    }
}