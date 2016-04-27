package co.wouri.libreexchange.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import co.wouri.libreexchange.core.models.Wallet;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.UIUtils;
import static co.wouri.libreexchange.core.managers.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;


public class LoginScreenActivity extends Activity {

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
    boolean isRegistered = true;


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
                isRegistered = false;
                firstName.setVisibility(View.VISIBLE);
                lastName.setVisibility(View.VISIBLE);
                submitButton.setText("SIGN UP");
                loginChoices.setVisibility(View.GONE);

            }
        });

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password,firstName,lastName, loginChoices, signUpText, forgotPasswordText);

    }


    void initUI() {
        initComponents();

    }

    private void startApp() {
        Intent intent = new Intent(LoginScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void performSubmit(View view) {
        String emailVal = email.getText().toString().trim();
        if (FormValidationUtils.checkEmail(emailVal)) {
            if(!isRegistered) {
                String passwordVal = password.getText().toString();
                String firstName = this.firstName.getText().toString().trim();
                String lastName = password.getText().toString();
                String country = this.getResources().getConfiguration().locale.getCountry();
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
                final String loggedInUserName = PrefUtils.getFromPrefs(this, PrefUtils.PREFS_LOGIN_USERNAME_KEY, "noUserName");
                final String loggedInUserPassword = PrefUtils.getFromPrefs(this, PREFS_LOGIN_PASSWORD_KEY, "noPassword");

                Log.d(TAG, "Customer created: " + customer);
                Wallet wallet = ServerUtils.getWallet(this, loggedInUserName, loggedInUserPassword);
                Log.d(TAG, "Wallet created: " + wallet);
                customer.setWallet(wallet);
                Log.d(TAG, "Customer with wallet setted : " + customer);
                if (customer != null) {
                    ProfileManager.getCurrentUserProfile().setCustomer(customer);
                    Profile profile = ProfileManager.saveProfile();
                    Log.d(TAG, "Profile saved = " + profile);
                    LibreExchangeSettingsUtils.setUserEmail(emailVal);
                }
            }else {
                // Test if Customer exists in the database and then logged in
                final String loggedInUserName = PrefUtils.getFromPrefs(this, PrefUtils.PREFS_LOGIN_USERNAME_KEY, "noUserName");
                final String loggedInUserPassword = PrefUtils.getFromPrefs(this, PREFS_LOGIN_PASSWORD_KEY, "noPassword");
                Log.d(TAG, "loggedInUserName = " + loggedInUserName);
                Log.d(TAG, "loggedInUserPassword = " + loggedInUserPassword);
                if (FormValidationUtils.checkEmail(loggedInUserName)) {
                    Customer customer = ServerUtils.getCustomer(this,loggedInUserName,loggedInUserPassword);
                    startApp();
                }else{
                    Toast.makeText(LoginScreenActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                };
            }
        } else
            Toast.makeText(LoginScreenActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
    }
}