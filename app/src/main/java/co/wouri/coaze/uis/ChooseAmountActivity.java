package co.wouri.coaze.uis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import co.wouri.coaze.R;
import co.wouri.coaze.adapters.ItemData;
import co.wouri.coaze.adapters.SpinnerAdapter;
import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.utils.UIUtils;

public class ChooseAmountActivity extends AppCompatActivity {

    LinearLayout amountComponentLayout1, amountComponentLayout2;
    CardView cardView;
    TextView currency1, currency2, amount2;
    ImageView edit_picture;
    EditText amount1;
    Spinner sp1, sp2;

    int USD = 0;
    int EUR = 1;
    private Recipient recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_amount);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            recipient = extras.getParcelable("recipient");
            CircularImageView imageView = (CircularImageView) findViewById(R.id.details_person_photo);
            TextView textView = (TextView) findViewById(R.id.details_person_name);
            imageView.setImageResource(recipient.getImage());
            textView.setText(recipient.getName());
        }

        buildToolBar();

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
        amount1 = (EditText) amountComponentLayout1.findViewById(R.id.amount);
        amount2 = (TextView) amountComponentLayout2.findViewById(R.id.amount);
        cardView = (CardView) findViewById(R.id.contact_chooser_component);
        edit_picture = (ImageView) cardView.findViewById(R.id.edit_picture);


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
                //Recipient recipient = new Recipient();
                intent.putExtra("recipient", (Parcelable) recipient);
                startActivity(intent);

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
            transfer.setRecipient(recipient);
            String amount = amount1.getText().toString();
            String currencyText1 = currency1.getText().toString();
            String currencyText2 = currency2.getText().toString();
            String amountCurrency = amount + " " + currencyText1;
            if (amount != null) {
                double valAmount = Double.parseDouble(amount1.getText().toString());
                double valReceiver = Double.parseDouble(amount2.getText().toString());
                if (valAmount > 0) {
                    transfer.setAmount(valAmount);
                    transfer.setSenderCurrency(currencyText1);
                    transfer.setReceiverAmount(valReceiver);
                    transfer.setReceiverCurrency(currencyText2);

                    Date now = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy / hh:mmaa");
                    transfer.setSendDate(sdf.format(now));
                    transfer.setNotifiedDate("pending");
                    transfer.setReceivedDate("pending");
                    transfer.setTransferType("SENT");
                    transfer.setReceiverCurrency(currencyText2);
                    // transfer.setSenderCurrency(currency);
                    Intent intent = new Intent(ChooseAmountActivity.this, SuccessActivity.class);
                    AccountManager.addTransfer(transfer);
                    intent.putExtra("transfer", (Parcelable) transfer);
                    startActivity(intent);
                } else
                    Toast.makeText(ChooseAmountActivity.this, "Amount value is null", Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(ChooseAmountActivity.this, "Amount is null", Toast.LENGTH_SHORT).show();


        } else
            Toast.makeText(ChooseAmountActivity.this, "Recipient is null", Toast.LENGTH_SHORT).show();

    }

    public void performChoose(View v) {
//        Toast.makeText(ChooseAmountActivity.this, "Not yet implement", Toast.LENGTH_LONG).show();
        startActivity(new Intent(ChooseAmountActivity.this, EditRecipientActivity.class));
    }

    public void performClose(View view) {
        finish();
    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("CHOOSE AMOUNT");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(ChooseRecipientActivity.this, AddRecipientActivity.class));
            }
        });

        title.setVisibility(View.VISIBLE);
//        option.setVisibility(View.VISIBLE);

    }
}
