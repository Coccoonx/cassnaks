package co.wouri.libreexchange.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.adapters.ItemData;
import co.wouri.libreexchange.adapters.SpinnerAdapter;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.utils.UIUtils;

public class MainActivityOld extends AppCompatActivity {

    LinearLayout amountComponentLayout1, amountComponentLayout2;
    TextView currency1, currency2, amount2;
    TextView amount1;
    int USD = 0;
    int EUR = 1;
    Spinner sp1, sp2;

    Toolbar toolbar;
    private DrawerLayout mDrawerLayout;

    Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (getIntent().getExtras() != null) {
            boolean b = getIntent().getBooleanExtra("customer", false);
            if (b) {
                Toast.makeText(MainActivityOld.this, "Customer created successfully.", Toast.LENGTH_SHORT).show();
            }
        }


        initUI();


        ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("USD", R.drawable.usa));
        list.add(new ItemData("EUR", R.drawable.eur));

        sp1 = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_layout, R.id.txt, list);
        sp1.setAdapter(adapter);

        sp2 = (Spinner) findViewById(R.id.spinner2);
        //SpinnerAdapter adapter2=new SpinnerAdapter(this,R.layout.spinner_layout,R.id.txt,list);
        sp2.setAdapter(adapter);
        sp2.setSelection(sp1.getSelectedItemPosition() + 1);
        amountComponentLayout1 = (LinearLayout) findViewById(R.id.amount_component_layout_1);
        currency1 = (TextView) amountComponentLayout1.findViewById(R.id.currency);
        amountComponentLayout2 = (LinearLayout) findViewById(R.id.amount_component_layout_2);
        currency2 = (TextView) amountComponentLayout2.findViewById(R.id.currency);
        amount1 = (TextView) amountComponentLayout1.findViewById(R.id.amount);
        amount2 = (TextView) amountComponentLayout2.findViewById(R.id.amount);
        setAllCurencies();
        setAmount();
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setOneCurrency(position, currency1);
                setAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                setAllCurencies();
                setAmount();
            }
        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                setOneCurrency(position, currency2);
                setAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                setAllCurencies();
                setAmount();
            }
        });

    }

    private void setAmount() {
        Double dollars, euros;
        if (sp1.getSelectedItemPosition() == sp2.getSelectedItemPosition()) {
            amount2.setText(amount1.getText());
        } else if (sp1.getSelectedItemPosition() == USD) { // ie sp2.getSelectedItemPosition() == EUR
            try {
                dollars = new Double(amount1.getText().toString());
            } catch (Exception e) {
                dollars = new Double(0);
            }
            euros = 0.93 * dollars;
            amount2.setText(euros + "");
        } else {
            try {
                euros = new Double(amount1.getText().toString());
            } catch (Exception e) {
                euros = new Double(0);
            }
            dollars = 1.07 * euros;
            amount2.setText(dollars + "");
        }
    }

    private void setOneCurrency(int position, TextView currency) {
        if (position == USD) {
            currency.setText("$");
        } else if (position == EUR) {
            currency.setText("€");
        }
    }

    private void setAllCurencies() {

        if (sp1.getSelectedItemPosition() == USD) {
            currency1.setText("$");
        } else {
            currency1.setText("€");
        }

        if (sp2.getSelectedItemPosition() == USD) {
            currency2.setText("$");
        } else {
            currency2.setText("€");
        }
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


    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        close.setImageResource(R.drawable.ic_menu);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        title.setText("");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);


        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ChooseRecipientActivity.this, AddRecipientActivity.class));
            }
        });

        title.setVisibility(View.VISIBLE);
//        option.setVisibility(View.VISIBLE);

    }


    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();

        buildToolBar();
        buildDrawer();

    }

    void buildDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        LinearLayout linearProfile = (LinearLayout) navigationView.findViewById(R.id.linear_profile);
        linearProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityOld.this, MainProfileActivity.class);
                intent.putExtra("profile", (Parcelable) profile.getCustomer());
                intent.putExtra("isUpdate", true);
                startActivity(intent);
            }
        });

        TextView username = (TextView) navigationView.findViewById(R.id.username);
        TextView userEmail = (TextView) navigationView.findViewById(R.id.useremail);
        TextView userBalance = (TextView) navigationView.findViewById(R.id.userbalance);

        String usern = profile.getCustomer().getFirstName() == null ? profile.getCustomer().getPhone()  : profile.getCustomer().getFirstName();
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
                    startActivity(new Intent(MainActivityOld.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    startActivity(new Intent(MainActivityOld.this, TransferHistoryActivity.class));
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    startActivity(new Intent(MainActivityOld.this, BalanceActivity.class));
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                } else if (menuItem.getItemId() == R.id.question_item) {
                    UIUtils.showAnswer(MainActivityOld.this);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }

    public void performHow(View v) {
        startActivity(new Intent(MainActivityOld.this, CostActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }

    public void performStart(View v) {
        startActivity(new Intent(MainActivityOld.this, ChooseRecipientActivity.class));

    }
}
