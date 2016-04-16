package co.wouri.libreexchange.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Locale;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.utils.UIUtils;

public class TransferDetailsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);
        initUI();

        Intent intent = getIntent();

        Transfer transfer = intent.getParcelableExtra("transfer");

        // set header
        CardView headCardView = (CardView) findViewById(R.id.cardview_transfer_details_head);
        ImageView personPhotoView = (ImageView) headCardView.findViewById(R.id.details_person_photo);
        TextView personNameView = (TextView) headCardView.findViewById(R.id.details_person_name);
        TextView personAmountView = (TextView) headCardView.findViewById(R.id.details_person_amount);

        // Image of the recipient
//        personPhotoView.setImageBitmap(person.getPhotoId());
//        personPhotoView.setImageBitmap(transfer.getRecipient().getImageUri());
        personNameView.setText(transfer.getRecipient().getFirstName());
        personAmountView.setText(transfer.getSenderCurrency() + " " + transfer.getAmount());

        //set Body
        CardView bodyCardView1 = (CardView) findViewById(R.id.cardview_transfer_details_body1);
        TextView titleView1 = (TextView) bodyCardView1.findViewById(R.id.details_title);
        TextView dateView1 = (TextView) bodyCardView1.findViewById(R.id.details_date);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy / hh:mmaa");
        String transferDate = sdf.format(transfer.getCreationDate());
        titleView1.setText("Send:");
        dateView1.setText(transferDate);

        CardView bodyCardView2 = (CardView) findViewById(R.id.cardview_transfer_details_body2);
        TextView titleView2 = (TextView) bodyCardView2.findViewById(R.id.details_title);
        TextView dateView2 = (TextView) bodyCardView2.findViewById(R.id.details_date);
        titleView2.setText("Notified:");
        dateView2.setText(transfer.getStatus().toString());

        CardView bodyCardView3 = (CardView) findViewById(R.id.cardview_transfer_details_body3);
        TextView titleView3 = (TextView) bodyCardView3.findViewById(R.id.details_title);
        TextView dateView3 = (TextView) bodyCardView3.findViewById(R.id.details_date);
        titleView3.setText("Received:");
        dateView3.setText("pending");

        //set End
        CardView endCardView = (CardView) findViewById(R.id.cardview_transfer_details_end);
        TextView endTitleView = (TextView) endCardView.findViewById(R.id.details_title);
        TextView endAmountView = (TextView) endCardView.findViewById(R.id.details_amount_recieved);
        endTitleView.setText("Amount Received:");
        endAmountView.setText(transfer.getReceiverCurrency() + " " + transfer.getRecieverAmount());

        // set Font
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, endAmountView, endTitleView, titleView3,
                dateView3, dateView2, titleView2, dateView1, titleView1, personAmountView, personNameView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quit) {
            finish();
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

        title.setText("TRANSFER DETAILS");
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
                Intent intent = new Intent(TransferDetailsActivity.this, ProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getAccount());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
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
                    Intent intent = new Intent(TransferDetailsActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
//                    startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    Intent intent = new Intent(TransferDetailsActivity.this, TransferHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    Intent intent = new Intent(TransferDetailsActivity.this, BalanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                } else if (menuItem.getItemId() == R.id.question_item) {
                    UIUtils.showAnswer(TransferDetailsActivity.this);
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
}
