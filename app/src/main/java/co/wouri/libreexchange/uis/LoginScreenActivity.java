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
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Customer;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

public class LoginScreenActivity extends Activity implements LoadingTaskFinishedListener {

    private static final String TAG = "LoginScreenActivity";
    TextView appName;
    TextView slogan;
    ImageView menu;
    EditText email;
    EditText password;
    EditText passwordConf;

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

        email = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.userPassword);
        passwordConf = (EditText) findViewById(R.id.userPasswordConfirm);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, email, password, passwordConf);

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
        startApp();
//        String emailVal = email.getText().toString().trim();
//        String passwordVal = password.getText().toString();
//        String passwordCVal = passwordConf.getText().toString();
//        if (FormValidationUtils.checkEmail(emailVal)) {
//            if (passwordVal.equals(passwordCVal)) {
//                Customer userCustomer = new Customer();
//                userCustomer.setEmail(emailVal);
//                userCustomer.setPassword(passwordCVal);
//                Customer customer = ServerUtils.createCustomer(this, userCustomer);
//                Log.d(TAG, "Customer created: "+customer);
//                if (customer != null) {
//                    ProfileManager.getCurrentUserProfile().setCustomer(customer);
//                    ProfileManager.saveProfile();
//                    LibreExchangeSettingsUtils.setUserEmail(emailVal);
//                    startApp();
//                }
//            } else
//                Toast.makeText(LoginScreenActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
//        } else
//            Toast.makeText(LoginScreenActivity.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
    }
}