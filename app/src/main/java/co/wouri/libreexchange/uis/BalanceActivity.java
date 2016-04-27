package co.wouri.libreexchange.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Currency;
import java.util.Locale;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;
import co.wouri.libreexchange.core.managers.PrefUtils;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Wallet;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.UIUtils;

import static co.wouri.libreexchange.core.managers.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;

public class BalanceActivity extends AppCompatActivity {

    private static final String TAG = "BalanceActivity";

    TextView balance;
    TextView amount;
    private Button addFundButton;
    private Profile profile;
    private DrawerLayout mDrawerLayout;
    Currency currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        initUI();
        addFundButton = (Button) findViewById(R.id.addFund);
        balance = (TextView) findViewById(R.id.balanceLabel);
//        amount = (TextView) findViewById(R.id.amountValue);
        Double availableBalance = ProfileManager.getCurrentUserProfile().getCustomer().getWallet().getAvailableBalance();
        Log.d(TAG, "loggedInUserName = " + availableBalance);
        Log.d(TAG, "currency.getSymbol() = " + currency.getSymbol());
        amount.setText(currency.getSymbol() + " " + availableBalance);
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, balance, addFundButton, amount);
        addFundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BalanceActivity.this, AddFundActivity.class));

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();
        buildToolBar();
        buildDrawer();
    }

    void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView menu = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView close = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setVisibility(View.VISIBLE);
        close.setVisibility(View.VISIBLE);

        title.setText("BALANCE");
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
                finish();
            }
        });


    }

    void buildDrawer() {

        Locale locale = Locale.getDefault();
         currency = Currency.getInstance(locale);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        LinearLayout linearProfile = (LinearLayout) navigationView.findViewById(R.id.linear_profile);
        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BalanceActivity.this, MainProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getCustomer());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
            }
        });

        TextView username = (TextView) navigationView.findViewById(R.id.username);
        TextView userEmail = (TextView) navigationView.findViewById(R.id.useremail);
        TextView userBalance = (TextView) navigationView.findViewById(R.id.userbalance);
        username.setText(getResources().getString(R.string.profile));
        userEmail.setText(profile.getCustomer().getEmail());

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
                    Intent intent = new Intent(BalanceActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
//                    startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    Intent intent = new Intent(BalanceActivity.this, TransferHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
//                    Intent intent = new Intent(AboutActivity.this, BalanceActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                } else if (menuItem.getItemId() == R.id.question_item) {
                    UIUtils.showAnswer(BalanceActivity.this);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }

    public Double getAvailableBalance() {
            final String loggedInUserName = PrefUtils.getFromPrefs(this, PrefUtils.PREFS_LOGIN_USERNAME_KEY, "noUserName");
            final String loggedInUserPassword = PrefUtils.getFromPrefs(this, PREFS_LOGIN_PASSWORD_KEY, "noPassword");

        final String email = ProfileManager.getCurrentUserProfile().getCustomer().getEmail();
        final String password = ProfileManager.getCurrentUserProfile().getCustomer().getPassword();
            Log.d(TAG, "loggedInUserName = " + loggedInUserName);
            Log.d(TAG, "loggedInUserPassword = " + loggedInUserPassword);
        Wallet wallet = new Wallet();
            if (FormValidationUtils.checkEmail(email)) {
               wallet = ServerUtils.getWallet(this, email, password);
            }else{
                Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
            }
        return wallet.getAvailableBalance();

    }

}
