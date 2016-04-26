package co.wouri.libreexchange.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Customer;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

public class LoginScreenActivityOld extends Activity implements LoadingTaskFinishedListener {

    private static final String TAG = "LoginScreenActivity";
    TextView appName;
    TextView slogan;
    ImageView menu;
    EditText email;
    EditText password;
    EditText passwordConf;
    EditText firstName;
    EditText lastName;
    EditText phone;
    EditText city;
    Spinner country;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the activity_splash screen
        setContentView(R.layout.activity_register);
        initUI();
    }

    private void initComponents() {

        firstName = (EditText) findViewById(R.id.firstname_edit_register);
        lastName = (EditText) findViewById(R.id.lastname_edit_register);

        email = (EditText) findViewById(R.id.email_edit_register);
        password = (EditText) findViewById(R.id.password_edit_register);
        city = (EditText) findViewById(R.id.city_edit_register);
        phone = (EditText) findViewById(R.id.phone_edit_register);
        country = (Spinner) findViewById(R.id.countries_spinner_register);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, firstName, lastName, email, password, passwordConf,city,phone,country);

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
            intent = new Intent(LoginScreenActivityOld.this, MainActivity.class);
//        } else
//            intent = new Intent(LoginScreenActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void performSubmit(View view) {
//        startApp();
        String emailVal = email.getText().toString().trim();
        String passwordVal = password.getText().toString();
        String firstNameVal = firstName.getText().toString();
        String lastNameVal = lastName.getText().toString();
        String countryVal = country.getContext().toString();
        String passwordCVal = passwordConf.getText().toString();
        String cityVal = city.getText().toString();
        String phoneVal = phone.getText().toString().trim();
//        if (FormValidationUtils.checkEmail(emailVal)) {
            if (passwordVal.equals(passwordCVal)) {
                Customer userCustomer = new Customer();
                userCustomer.setEmail(emailVal);
                userCustomer.setPassword(passwordCVal);
                userCustomer.setCountry(countryVal);
                userCustomer.setFirstName(firstNameVal);
                userCustomer.setLastName(lastNameVal);
                userCustomer.setCity(cityVal);
                userCustomer.setPhone(phoneVal);
                Customer customer = ServerUtils.createCustomer(this, userCustomer);
                Log.d(TAG, "Customer created: "+customer);
                if (customer != null) {
                    ProfileManager.getCurrentUserProfile().setCustomer(customer);
                   ProfileManager.saveProfile();
                   LibreExchangeSettingsUtils.setUserEmail(emailVal);
                    startApp();
                }
            } else
                Toast.makeText(LoginScreenActivityOld.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
//        } else
//            Toast.makeText(LoginScreenActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
    }
}