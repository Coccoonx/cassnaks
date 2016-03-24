package co.wouri.libreexchange.uis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.utils.LoadingTask;
import co.wouri.libreexchange.utils.LoadingTask.LoadingTaskFinishedListener;
import co.wouri.libreexchange.utils.UIUtils;

public class SplashScreenActivity extends Activity implements LoadingTaskFinishedListener {

    TextView appName;
    TextView slogan;
    ProgressBar progressBar;
    Button balance;
    Button transfer;
    private Profile profile;
    private DrawerLayout mDrawerLayout;
    ImageView close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the activity_splash screen
        setContentView(R.layout.activity_splash);
        initUI();


        // Start your loading
        new LoadingTask(progressBar, this).execute("www.google.com"); // Pass in whatever you need a url is just an example we don't use it in this tutorial
//        completeSplash();
    }

    private void initComponents() {
        // Find the progress bar
        progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);

        appName = (TextView) findViewById(R.id.appName);
        slogan = (TextView) findViewById(R.id.appSlogan);

        balance = (Button) findViewById(R.id.balance);
        transfer = (Button) findViewById(R.id.transfer);

//        balance.setAlpha(0.0f);
//        transfer.setAlpha(0.0f);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, appName, slogan, balance, transfer);

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, BalanceActivity.class));

            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, TransferHistoryActivity.class));
            }
        });

    }

    // This is the callback for when your async task has finished
    @Override
    public void onTaskFinished() {
        completeSplash();
    }

    private void completeSplash() {
//        startApp();
//        finish(); // Don't forget to finish this Splash Activity so the account can't return to it!
        runAnimation();
    }

    private void runAnimation() {
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        balance.startAnimation(animationFadeIn);
        transfer.startAnimation(animationFadeIn);
        close.startAnimation(animationFadeIn);
        animationFadeIn.setFillAfter(true);
        progressBar.startAnimation(animationFadeOut);
        animationFadeOut.setFillAfter(true);
    }


    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        close.setVisibility(View.INVISIBLE);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

//        option.setVisibility(View.VISIBLE);

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
                Intent intent = new Intent(SplashScreenActivity.this, ProfileActivity.class);
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
        userBalance.setText("" + profile.getAccount().getBalance());

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
                    startActivity(new Intent(SplashScreenActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    startActivity(new Intent(SplashScreenActivity.this, TransferHistoryActivity.class));
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    startActivity(new Intent(SplashScreenActivity.this, BalanceActivity.class));
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }


    private void startApp() {
        Intent intent;
//        if (CoazeSettingsUtils.getUserLogged()) {
        intent = new Intent(SplashScreenActivity.this, ChooseRecipientActivity.class);

//        } else
//            intent = new Intent(SplashScreenActivity.this, ProfileActivity.class);

        startActivity(intent);
    }
}