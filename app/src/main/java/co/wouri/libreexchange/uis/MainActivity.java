package co.wouri.libreexchange.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
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

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.api.ServerUtils;
import co.wouri.libreexchange.core.managers.PrefUtils;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Customer;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Wallet;
import co.wouri.libreexchange.storage.LibreExchangeSettingsUtils;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.LoadingTask;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

import static co.wouri.libreexchange.core.managers.PrefUtils.PREFS_LOGIN_PASSWORD_KEY;


import co.wouri.libreexchange.utils.UIUtils;

public class MainActivity extends Activity{

    private static final String TAG = "MainActivity";

    TextView appName;
    TextView slogan;
    Button balance;
    Button transfer;
    private Profile profile;
    private DrawerLayout mDrawerLayout;
    ImageView materialDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initUI();
    }

    private void initComponents() {

        appName = (TextView) findViewById(R.id.appName);
        slogan = (TextView) findViewById(R.id.appSlogan);
        balance = (Button) findViewById(R.id.balance);
        transfer = (Button) findViewById(R.id.transfer);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, balance, transfer);

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BalanceActivity.class));

            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChooseRecipientActivity.class));
            }
        });
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, balance, transfer);
    }


    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        materialDrawer = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);

        materialDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

    }

    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();
        buildToolBar();
        buildDrawer();
        initComponents();

    }

    void buildDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        LinearLayout linearProfile = (LinearLayout) navigationView.findViewById(R.id.linear_profile);
        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainProfileActivity.class);
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

                if (menuItem.getItemId() == R.id.about_item) {
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    Intent intent = new Intent(MainActivity.this, TransferHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    Intent intent = new Intent(MainActivity.this, BalanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                } else if (menuItem.getItemId() == R.id.question_item) {
                    UIUtils.showAnswer(MainActivity.this);
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
            finish();
    }

}