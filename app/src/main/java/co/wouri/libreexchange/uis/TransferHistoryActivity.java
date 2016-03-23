package co.wouri.libreexchange.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Currency;
import java.util.List;
import java.util.Locale;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.uis.transferhistory.TransferHistoryAdapter;
import co.wouri.libreexchange.utils.UIUtils;

public class TransferHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Transfer> transfers;
    TransferHistoryAdapter adapter;
    private Profile profile;
    private DrawerLayout mDrawerLayout;
//    private CardView cardView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_history);
        initUI();
        recyclerView = (RecyclerView) findViewById(R.id.transfer_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        cardView = (CardView) findViewById(R.id.card_view_item);

//        rv.setHasFixedSize(true);
        initializeData();
        initializeAdapter();
    }

    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();

        buildToolBar();
        buildDrawer();

    }

    private void initializeData() {
        transfers = ProfileManager.getTransferts();

//        persons.add(new Person("Beyonce Knowles", BitmapUtils.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.beyonce), 200), 100, "SEND", "18.07.2015"));
//        persons.add(new Person("Barack Obama", BitmapUtils.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.barackobama), 200), 50, "RECIEVED", "11.05.2015"));
//        persons.add(new Person("Barry Green", BitmapUtils.getRoundedCornerBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.unknown), 200), 200, "SEND", "12.03.2015"));
    }

    private void initializeAdapter() {
        adapter = new TransferHistoryAdapter(transfers, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        close.setImageResource(R.drawable.ic_menu);
        option.setVisibility(View.VISIBLE);
        option.setImageResource(R.drawable.ic_send_black_18dp);

        title.setText("TRANSFER HISTORY");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TransferHistoryActivity.this, ChooseRecipientActivity.class));
            }
        });

        title.setVisibility(View.VISIBLE);

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
                Intent intent = new Intent(TransferHistoryActivity.this, ProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getAccount());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
            }
        });

        TextView username = (TextView) navigationView.findViewById(R.id.username);
        TextView userEmail = (TextView) navigationView.findViewById(R.id.useremail);
        TextView userBalance = (TextView) navigationView.findViewById(R.id.userbalance);

        String usern = profile.getAccount().getFirstName() == null ? profile.getAccount().getPhoneNumber()  : profile.getAccount().getFirstName();
        username.setText(usern);
        userEmail.setText(profile.getAccount().getEmail());
        userBalance.setText(currency.getSymbol()+" " + profile.getAccount().getBalance());

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
                    startActivity(new Intent(TransferHistoryActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    startActivity(new Intent(TransferHistoryActivity.this, TransferHistoryActivity.class));
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    startActivity(new Intent(TransferHistoryActivity.this, BalanceActivity.class));
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer_history, menu);
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
            Log.d("Coaze", "Click on Finish button");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
