package co.wouri.libreexchange.uis;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Currency;
import java.util.Locale;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;
import co.wouri.libreexchange.api.netflow.net.Request;
import co.wouri.libreexchange.api.netflow.net.Response;
import co.wouri.libreexchange.api.netflow.net.ResponseListener;
import co.wouri.libreexchange.api.netflow.net.Web;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Account;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.storage.CoazeSettingsUtils;
import co.wouri.libreexchange.utils.CoreUtils;
import co.wouri.libreexchange.utils.UIUtils;

import static co.wouri.libreexchange.utils.FormValidationUtils.checkAddress;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkCity;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkCountry;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkEmail;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkLastName;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkFirstName;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkPhone;

public class ProfileActivity extends AppCompatActivity implements ResponseListener {
    private static final String TAG = "coaze profile";
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText lastname;
    EditText email;
    EditText phone;
    EditText city;
    EditText address;
    EditText socialSecurityNumber;
    private EditText firstName;
    private EditText state;
    private EditText password;
    private ProgressDialog progressDialog;

    MyArrayAdapter
            mySpinnerArrayAdapter;
    private Profile profile;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        buildToolBar();


        lastname = (EditText) findViewById(R.id.lastname_edit_recipient);
        firstName = (EditText) findViewById(R.id.firstname_edit_recipient);

        city = (EditText) findViewById(R.id.city_edit_recipient);
        state = (EditText) findViewById(R.id.state_edit_recipient);
        address = (EditText) findViewById(R.id.address_edit_recipient);

        email = (EditText) findViewById(R.id.email_edit_recipient);

        phone = (EditText) findViewById(R.id.phone_edit_recipient);
        socialSecurityNumber = (EditText) findViewById(R.id.ssn_edit_recipient);
        password = (EditText) findViewById(R.id.password_edit_recipient);
        countries = (Spinner) findViewById(R.id.countries);

        String[] countrie = {"Canada", "Cameroon", "China", "USA"};


        mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, countrie);
        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) countries.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        countries.setAdapter(mySpinnerArrayAdapter);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, firstName, lastname, city, address, email, phone);


        if (getIntent().getExtras() != null) {
            updateUi();
        }


        addButton = (Button) findViewById(R.id.button_edit_recipient);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = lastname.getText().toString();
                String firstNameValue = firstName.getText().toString();
                String lastnameValue = lastname.getText().toString();
                String emailValue = email.getText().toString();
                String phoneValue = phone.getText().toString();
                String countryValue = countries.getSelectedItem().toString();
                String cityValue = city.getText().toString();
                String stat = state.getText().toString();
                String addressValue = address.getText().toString();
                String ssn = socialSecurityNumber.getText().toString();
                String passwor = password.getText().toString();
                if (!checkLastName(ProfileActivity.this, lastnameValue)
                        || !checkFirstName(ProfileActivity.this, firstNameValue)
                        || !checkEmail(ProfileActivity.this, emailValue)
                        || !checkPhone(ProfileActivity.this, phoneValue)
                        || !checkCity(ProfileActivity.this, cityValue)
                        || !checkAddress(ProfileActivity.this, addressValue)
                        || !checkCountry(ProfileActivity.this, countryValue)
                        ) {

                } else {
                    //We should call the backend functions here
//                    JSONObject obj = new JSONObject();
//                    try {
//                        obj.put("email", emailValue);
//                        obj.put("phoneNumber", phoneValue);
//                        obj.put("firstName", firstNameValue);
//                        obj.put("lastName", nameValue);
//                        obj.put("city", cityValue);
//                        obj.put("state", stat);
//                        obj.put("country", countryValue);
//                        obj.put("address", addressValue);
//                        obj.put("socialSecurityNumber", ssn);
//                        obj.put("password", "acmesecret1");
//                        obj.put("deviceId", CoreUtils.getDeviceId());
//
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                        Log.d(TAG, "Error : " + Log.getStackTraceString(e));
//                    }

                    Account account = new Account();

                    account.setEmail(emailValue);
                    account.setPhoneNumber(phoneValue);
                    account.setFirstName(firstNameValue);
                    account.setLastName(nameValue);
                    account.setCity(cityValue);
                    account.setState(stat);
                    account.setCountry(countryValue);
                    account.setAddress(addressValue);
                    account.setSocialSecurityNumber(ssn);
                    account.setPassword("acmesecret1");
                    CoazeSettingsUtils.setUserPassword("acmesecret1");
                    account.setDeviceId(CoreUtils.getDeviceId());
//                    showBusy();
//                    if (isUpdate) {
//                        Prefs.getInstance().loadPrefs();
//                        String token = Prefs.getInstance().token;
//                        Web.requestAsynData(new Request(Web.getUpdateAccountUrl(), true, token, "PUT", obj.toString(), this, REQUEST_UPDATE_ACCOUNT));
//
//                    } else {
                    progressDialog = new ProgressDialog(ProfileActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(true);
                    progressDialog.setMessage("Retrieving data...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    new CreateAccount().execute(account);
//                    Web.requestAsynData(new Request(Web.getCreateAccountUrl(), false, null, "POST", obj.toString(), this, REQUEST_CREATE_ACCOUNT));
//                    }

                }
            }
        });

    }

    private void updateUi() {
        Account account = getIntent().getParcelableExtra("profile");

        lastname.setText(account.getLastName());
        firstName.setText(account.getFirstName());

        city.setText(account.getCity());
        state.setText(account.getState());
        address.setText(account.getAddress());

        email.setText(account.getEmail());

        phone.setText(account.getPhoneNumber());
        socialSecurityNumber.setText(account.getSocialSecurityNumber());
        password.setEnabled(false);
        countries.setSelection(mySpinnerArrayAdapter.getPosition(account.getCountry()));

    }

    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();

        buildToolBar();
        buildDrawer();

    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView menu = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView close = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setVisibility(View.VISIBLE);
        close.setVisibility(View.VISIBLE);

        title.setText("PROFILE");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                finish();
            }
        });


    }

    void buildDrawer() {

        Locale locale = Locale.getDefault();
        Currency currency = Currency.getInstance(locale);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        LinearLayout linearProfile = (LinearLayout) navigationView.findViewById(R.id.linear_profile);
        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getAccount());
                intent.putExtra("isUpdate", true);
//                startActivity(intent);
            }
        });

        TextView username = (TextView) navigationView.findViewById(R.id.username);
        TextView userEmail = (TextView) navigationView.findViewById(R.id.useremail);
        TextView userBalance = (TextView) navigationView.findViewById(R.id.userbalance);

        String usern = profile.getAccount().getFirstName() == null ? profile.getAccount().getPhoneNumber() : profile.getAccount().getFirstName();
        username.setText(getResources().getString(R.string.profile));
        userEmail.setText(profile.getAccount().getEmail());
        userBalance.setText(currency.getSymbol() + " " + profile.getAccount().getBalance());

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, userBalance, userEmail, username);


        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    UIUtils.applyFontToMenuItem(UIUtils.Font.MUSEOSANS_500, subMenuItem);
                }
            }

            //the method we have create in activity
            UIUtils.applyFontToMenuItem(UIUtils.Font.MUSEOSANS_500, mi);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                menuItem.setChecked(true);

                if (menuItem.getItemId() == R.id.about_item) {
                    Intent intent = new Intent(ProfileActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
//                    startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    Intent intent = new Intent(ProfileActivity.this, TransferHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    Intent intent = new Intent(ProfileActivity.this, BalanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                } else if (menuItem.getItemId() == R.id.question_item) {
                    UIUtils.showAnswer(ProfileActivity.this);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onResponse(Response r, int rid) {

        if (rid == REQUEST_CREATE_LOGIN) {
            if (check(r, rid)) {
//                {"access_token":"f10e6ac1-47c8-466b-b465-3f0431458f8f","token_type":"bearer","refresh_token":"e4ff8824-9aa8-49be-ab42-b75415e3b35c","expires_in":43200,"scope":"openid"}
                Log.d(TAG, "REQUEST_CREATE_LOGIN no error_ Obj:" + r.obj);
               // Toast.makeText(ProfileActivity.this, "Account created successfully.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ProfileActivity.this,MainActivity.class);
                intent.putExtra("account",true);
                startActivity(intent);
                try {
                    JSONObject obj = r.obj;
                    if (obj != null) {

                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            return false;
        }
        return false;

    }


    boolean check(Response r, int rid) {
        if (rid == REQUEST_CREATE_LOGIN) {
            if (!r.isError()) {
                try {
                    JSONObject obj = r.obj;
                    CoazeSettingsUtils.setAccessToken(obj.getString("access_token"));
                    CoazeSettingsUtils.setTokenType(obj.getString("token_type"));
                    CoazeSettingsUtils.setExpireIn(obj.getInt("expires_in"));
                    CoazeSettingsUtils.setUserFirstLogin(System.currentTimeMillis());
                    CoazeSettingsUtils.setUserLogged(true);
                    return true;
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
        return false;
    }

    @Override
    public void noNetwork() {

    }


    public class MyArrayAdapter extends ArrayAdapter {

        public MyArrayAdapter(Context context, int textViewResourceId, String[] countries) {
            super(context, textViewResourceId, countries);
        }

        public TextView getView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) super.getView(position, convertView, parent);
            v.setTypeface(UIUtils.getTypeface(UIUtils.Font.MUSEOSANS_500));
            return v;
        }

        public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) super.getView(position, convertView, parent);
            v.setTypeface(UIUtils.getTypeface(UIUtils.Font.MUSEOSANS_500));
            return v;
        }

    }

    private class CreateAccount extends AsyncTask<Account, Void, Account> {


        @Override
        protected Account doInBackground(Account... params) {
//            progressDialog.show();

            Account account = ServerUtils.createAccount(ProfileActivity.this, params[0]);
            return account;
        }


        @Override
        protected void onPostExecute(Account account) {
            super.onPostExecute(account);
//            progressDialog.cancel();
            if (account != null) {

                CoazeSettingsUtils.setUserUid(account.getId());
                CoazeSettingsUtils.setUserEmail(account.getEmail());
                ProfileManager.getCurrentUserProfile().setAccount(account);
                ProfileManager.saveProfile();

//                new Login().execute();

                loginRequest();
            } else
                Toast.makeText(ProfileActivity.this, "An error occurred while creating your account", Toast.LENGTH_LONG).show();
        }
    }

    protected void loginRequest() {
        try {
            String req = "grant_type=password&password=acmesecret1&username=" + CoazeSettingsUtils.getUserEmail();
            Web.requestAsynData(new Request(Web.getLoginUrl(), true, null, "POST", req, this, REQUEST_CREATE_LOGIN));

        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    private class Login extends AsyncTask<Void, Void, JSONObject> {


        @Override
        protected JSONObject doInBackground(Void... params) {
//            progressDialog.show();

            JSONObject jsonObject = ServerUtils.login(ProfileActivity.this);
            return jsonObject;
        }


        @Override
        protected void onPostExecute(JSONObject account) {
            super.onPostExecute(account);
//            progressDialog.cancel();
            if (account != null) {

                try {
                    CoazeSettingsUtils.setAccessToken(account.getString("access_token"));
                    CoazeSettingsUtils.setTokenType(account.getString("token_type"));
                    CoazeSettingsUtils.setExpireIn(account.getInt("expires_in"));
                    CoazeSettingsUtils.setUserFirstLogin(System.currentTimeMillis());
                    CoazeSettingsUtils.setUserLogged(true);

                    Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                    intent.putExtra("drawer", true);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else
                Toast.makeText(ProfileActivity.this, "An error occurred while creating your account", Toast.LENGTH_LONG).show();
        }
    }


}