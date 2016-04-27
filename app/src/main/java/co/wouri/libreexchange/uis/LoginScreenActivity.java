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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;

import co.wouri.libreexchange.core.managers.PrefUtils;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Customer;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

import static co.wouri.libreexchange.core.managers.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;

public class LoginScreenActivity extends Activity implements LoadingTaskFinishedListener {

    private static final String TAG = "LoginScreenActivity";
    TextView appName;
    TextView slogan;
    ImageView menu;
    EditText email;
    EditText password;
    EditText firstName;
    EditText lastName;
    RelativeLayout loginChoices ;
    TextView signUpText;
    TextView forgotPasswordText;
    Button submitButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        loginChoices = (RelativeLayout) findViewById(R.id.loginChoices);

        signUpText = (TextView) findViewById(R.id.signUpText);
        forgotPasswordText = (TextView) findViewById(R.id.forgotPasswordText);

        submitButton = (Button) findViewById(R.id.submitButton);

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                submitButton.setText("SIGN UP");
                loginChoices.setVisibility(View.GONE);

            }
        });

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password,firstName,lastName, loginChoices, signUpText, forgotPasswordText);

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
//        startApp();
        String emailVal = email.getText().toString().trim();
        String passwordVal = password.getText().toString();
        String firstName = email.getText().toString().trim();
        String lastName = password.getText().toString();
        String country = this.getResources().getConfiguration().locale.getCountry();
        if (FormValidationUtils.checkEmail(emailVal)) {
                Customer userCustomer = new Customer();
                userCustomer.setEmail(emailVal);
                userCustomer.setPassword(passwordVal);
                userCustomer.setCountry(country);
                userCustomer.setFirstName(firstName);
                userCustomer.setLastName(lastName);
                userCustomer.setEnabled(true);
                Customer customer = ServerUtils.createCustomer(this, userCustomer);
                //credentials on successful login case
                PrefUtils.saveToPrefs(LoginScreenActivity.this, PrefUtils.PREFS_LOGIN_USERNAME_KEY, userCustomer.getEmail());
                PrefUtils.saveToPrefs(LoginScreenActivity.this, PrefUtils.PREFS_LOGIN_PASSWORD_KEY, userCustomer.getPassword());

               // To retrieve values back

                Log.d(TAG, "Customer created: "+customer);
                if (customer != null) {
                    ProfileManager.getCurrentUserProfile().setCustomer(customer);
                    Profile profile=ProfileManager.saveProfile();
                    Log.d(TAG, "Profile saved = " + profile);
                    LibreExchangeSettingsUtils.setUserEmail(emailVal);
                    startApp();
                }
        } else
            Toast.makeText(LoginScreenActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
    }
}