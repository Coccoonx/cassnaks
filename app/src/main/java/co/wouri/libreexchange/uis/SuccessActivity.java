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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Currency;
import java.util.Locale;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.utils.UIUtils;

public class SuccessActivity extends AppCompatActivity {

    TextView successTV;
    TextView details1;
    TextView details2;
    TextView details3;
    TextView recipientName;
    TextView amount;
    EditText amount2;
    TextView was_sent;
    Button done;
    Button transfer;
    public static String TAG = "SuccessActivity";
    private DrawerLayout mDrawerLayout;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Bundle bundle = getIntent().getExtras();
        Log.d(TAG, "bundle " + bundle);

        successTV = (TextView) findViewById(R.id.successLabel);
        details1 = (TextView) findViewById(R.id.details_text1);
        details2 = (TextView) findViewById(R.id.details_text2);
        details3 = (TextView) findViewById(R.id.details_text3);
        recipientName = (TextView) findViewById(R.id.recipient_name);
        amount = (TextView) findViewById(R.id.amountValue);
        amount2 = (EditText) findViewById(R.id.amount);
        was_sent = (TextView) findViewById(R.id.was_sent);

        if (bundle != null) {
            Transfer transfer = (Transfer) bundle.getParcelable("transfer");
            //amount2.getText().toString();

            if (transfer != null) {
                amount.setText(transfer.getSenderCurrency() + transfer.getAmount());
                recipientName.setText(transfer.getRecipient().getFirstName());
            }


        }
        buildToolBar();
        done = (Button) findViewById(R.id.done);
        transfer = (Button) findViewById(R.id.transfers);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, successTV, details1, details2, details3, amount,
                recipientName, done, was_sent, transfer);

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessActivity.this, TransferHistoryActivity.class));
                finish();

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, SplashScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();
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

        title.setText("");
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
        Currency currency = Currency.getInstance(locale);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        LinearLayout linearProfile = (LinearLayout) navigationView.findViewById(R.id.linear_profile);
        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, ProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getAccount());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
            }
        });

        TextView username = (TextView) navigationView.findViewById(R.id.username);
        TextView userEmail = (TextView) navigationView.findViewById(R.id.useremail);
        TextView userBalance = (TextView) navigationView.findViewById(R.id.userbalance);

        String usern = profile.getAccount().getFirstName() == null ? profile.getAccount().getPhoneNumber() : profile.getAccount().getFirstName();
        username.setText(usern);
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
                    Intent intent = new Intent(SuccessActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
//                    startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    Intent intent = new Intent(SuccessActivity.this, TransferHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    Intent intent = new Intent(SuccessActivity.this, BalanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }
}
