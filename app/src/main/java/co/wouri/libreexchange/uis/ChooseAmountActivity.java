package co.wouri.libreexchange.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.adapters.ItemData;
import co.wouri.libreexchange.adapters.SpinnerAdapter;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Profile;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.core.models.TransferStatus;
import co.wouri.libreexchange.utils.LoadingTask;
import co.wouri.libreexchange.utils.UIUtils;

public class ChooseAmountActivity extends AppCompatActivity implements LoadingTask.LoadingTaskFinishedListener {

    LinearLayout amountComponentLayout1, amountComponentLayout2;
    RelativeLayout cardView;
    RelativeLayout bottomComponents;
    TextView currency1, currency2, amount2, textView;
    ImageView edit_picture;
    EditText amount1;
    Spinner sp1, sp2;
    CircularImageView imageView;
    Bundle extras;
    Intent intent;
    int USD = 0;
    int EUR = 1;
    private Recipient recipient;
    private Profile profile;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout rlSendingThread;
    private ProgressBar progressBar;
    private TextView sendingLabel;
    private RelativeLayout rlFillAmount;
    private ImageView menuToolbar;
    private TextView titleToolbar;
    private ImageView closeToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_amount);

        initUI();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (extras != null) {
            recipient = extras.getParcelable("recipient");
//            imageView.setImageBitmap(recipient.getImageUri());
            textView.setText(recipient.getFirstName());

        }

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
        } else
            super.onBackPressed();
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
        getMenuInflater().inflate(R.menu.menu_choose_amount, menu);
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

    public void performSend(View view) {
        Transfer transfer = new Transfer();
        if (recipient != null) {
            if (recipient.getFirstName() != null && !recipient.getFirstName().equalsIgnoreCase("")) {
                if (recipient.getPhoneNumbers() != null) {
                    if (recipient.getCity() != null) {
                        if (recipient.getCountry() != null) {
                            transfer.setRecipient(recipient);
                            String amount = amount1.getText().toString();
                            String currencyText1 = currency1.getText().toString();
                            String currencyText2 = currency2.getText().toString();
                            if (amount != null) {
                                double valAmount = Double.parseDouble(amount1.getText().toString());
                                double valReceiver = Double.parseDouble(amount2.getText().toString());
                                if (valAmount > 0) {
                                    updateUI();
                                    transfer.setAmount(valAmount);
                                    transfer.setSenderCurrency(currencyText1);
                                    transfer.setReceiverCurrency(currencyText2);
                                    transfer.setRecieverAmount(valReceiver);
                                    transfer.setTransferType("SENT");
                                    transfer.setStatus(TransferStatus.Pending);
                                    intent = new Intent(ChooseAmountActivity.this, SuccessActivity.class);
                                    ProfileManager.addTransfer(transfer);
                                    intent.putExtra("transfer", (Parcelable) transfer);

                                } else
                                    Toast.makeText(ChooseAmountActivity.this, "Amount value is incorrect.", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(ChooseAmountActivity.this, "Amount is incorrect.", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(ChooseAmountActivity.this, "Make sure the recipient's country is filled.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(ChooseAmountActivity.this, "Make sure the recipient's city is filled.", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ChooseAmountActivity.this, "Make sure the recipient has at least one valid phone number.", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(ChooseAmountActivity.this, "Make sure the recipient has at least the first name filled.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ChooseAmountActivity.this, "Recipient is null", Toast.LENGTH_SHORT).show();

    }

    void initUI() {
        profile = ProfileManager.getCurrentUserProfile();
        buildToolBar();
        buildDrawer();

        ArrayList<ItemData> list = new ArrayList<>();
        list.add(new ItemData("USD", R.drawable.usa));
        list.add(new ItemData("EUR", R.drawable.eur));

        bottomComponents = (RelativeLayout) findViewById(R.id.rlBottomComponents);
        rlSendingThread = (RelativeLayout) findViewById(R.id.rlSendingThread);
        rlFillAmount = (RelativeLayout) findViewById(R.id.rlFillAmount);
        progressBar = (ProgressBar) findViewById(R.id.activity_splash_progress_bar);
        sendingLabel = (TextView) findViewById(R.id.sendingLabel);

        sp1 = (Spinner) findViewById(R.id.spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.spinner_layout, R.id.txt, list);
        sp1.setAdapter(adapter);

        sp2 = (Spinner) findViewById(R.id.spinner2);
        //SpinnerAdapter adapter2=new SpinnerAdapter(this,R.layout.spinner_layout,R.id.txt,list);
        sp2.setAdapter(adapter);
//        sp2.setSelection(sp1.getSelectedItemPosition() + 1);

        sp1.setEnabled(false);
        sp2.setEnabled(false);

        amountComponentLayout1 = (LinearLayout) findViewById(R.id.amount_component_layout_1);
        currency1 = (TextView) amountComponentLayout1.findViewById(R.id.currency);
        amountComponentLayout2 = (LinearLayout) findViewById(R.id.amount_component_layout_2);
        currency2 = (TextView) amountComponentLayout2.findViewById(R.id.currency);
        amount1 = (EditText) amountComponentLayout1.findViewById(R.id.amount);
        amount2 = (TextView) amountComponentLayout2.findViewById(R.id.amount);
        cardView = (RelativeLayout) findViewById(R.id.contact_chooser_component);
        edit_picture = (ImageView) cardView.findViewById(R.id.edit_picture);
        extras = getIntent().getExtras();
        if (extras != null) {
            recipient = extras.getParcelable("recipient");
            imageView = (CircularImageView) cardView.findViewById(R.id.details_person_photo);
            textView = (TextView) cardView.findViewById(R.id.details_person_name);
//            imageView.setImageBitmap(recipient.getImageUri());
            Log.d("Recipient", "Recipient : " + recipient);
            textView.setText(recipient.getFirstName() + " " + (recipient.getLastName() != null ? recipient.getLastName() : ""));
        }


        setAllCurencies();
        setAmount();

        amount1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setAmount();
            }
        });

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

        edit_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAmountActivity.this, EditRecipientActivity.class);
                intent.putExtra("recipient", (Parcelable) recipient);
                startActivity(intent);

            }
        });

    }

    void updateUI() {
        rlFillAmount.setVisibility(View.GONE);
        rlSendingThread.setVisibility(View.VISIBLE);
        titleToolbar.setText("PROCESSING");
        runAnimation();
        new LoadingTask(progressBar, this).execute("www.google.com");
    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        menuToolbar = (ImageView) toolbar.findViewById(R.id.leftIcon);
        titleToolbar = (TextView) toolbar.findViewById(R.id.title);
        closeToolbar = (ImageView) toolbar.findViewById(R.id.rightIcon);

        titleToolbar.setVisibility(View.VISIBLE);
        closeToolbar.setVisibility(View.VISIBLE);

        titleToolbar.setText("CHOOSE AMOUNT");

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, titleToolbar);

        menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }
        });

        closeToolbar.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(ChooseAmountActivity.this, ProfileActivity.class);
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
                    Intent intent = new Intent(ChooseAmountActivity.this, AboutActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                    finish();
//                    startActivity(new Intent(AboutActivity.this, AboutActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_transfer) {
                    Intent intent = new Intent(ChooseAmountActivity.this, TransferHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
//                } else if (menuItem.getItemId() == R.id.nav_item_recipient) {
//                    startActivity(new Intent(MainActivity.this, RecipientActivity.class));
                } else if (menuItem.getItemId() == R.id.nav_item_balance) {
                    Intent intent = new Intent(ChooseAmountActivity.this, BalanceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.feedback_item) {
                } else if (menuItem.getItemId() == R.id.help_item) {
                } else if (menuItem.getItemId() == R.id.question_item) {
                    UIUtils.showAnswer(ChooseAmountActivity.this);
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void runAnimation() {
        Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.fadeinout);
        sendingLabel.startAnimation(animationFadeIn);
    }


    @Override
    public void onTaskFinished() {
        startActivity(intent);
        finish();
    }
}
