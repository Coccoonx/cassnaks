package co.wouri.libreexchange.uis;

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

import java.util.ArrayList;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.adapters.ItemData;
import co.wouri.libreexchange.adapters.SpinnerAdapter;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.core.models.TransferStatus;
import co.wouri.libreexchange.utils.UIUtils;

public class ChooseAmountActivity extends AppCompatActivity {

    LinearLayout amountComponentLayout1, amountComponentLayout2;
    CardView cardView;
    TextView currency1, currency2, amount2, textView;
    ImageView edit_picture;
    EditText amount1;
    Spinner sp1, sp2;
    CircularImageView imageView;
    Bundle extras;

    int USD = 0;
    int EUR = 1;
    private Recipient recipient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_amount);

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
//        sp2.setSelection(sp1.getSelectedItemPosition() + 1);

        sp1.setEnabled(false);
        sp2.setEnabled(false);

        amountComponentLayout1 = (LinearLayout) findViewById(R.id.amount_component_layout_1);
        currency1 = (TextView) amountComponentLayout1.findViewById(R.id.currency);
        amountComponentLayout2 = (LinearLayout) findViewById(R.id.amount_component_layout_2);
        currency2 = (TextView) amountComponentLayout2.findViewById(R.id.currency);
        amount1 = (EditText) amountComponentLayout1.findViewById(R.id.amount);
        amount2 = (TextView) amountComponentLayout2.findViewById(R.id.amount);
        cardView = (CardView) findViewById(R.id.contact_chooser_component);
        edit_picture = (ImageView) cardView.findViewById(R.id.edit_picture);
        extras = getIntent().getExtras();
        if (extras != null) {
            recipient = extras.getParcelable("recipient");
            imageView = (CircularImageView) cardView.findViewById(R.id.details_person_photo);
            textView = (TextView) cardView.findViewById(R.id.details_person_name);
//            imageView.setImageBitmap(recipient.getImageUri());
            textView.setText(recipient.getFirstName());
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
                //Recipient recipient = new Recipient();
                intent.putExtra("recipient", (Parcelable) recipient);
                startActivity(intent);

            }
        });

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
            if (amount != null) {
                double valAmount = Double.parseDouble(amount1.getText().toString());
                double valReceiver = Double.parseDouble(amount2.getText().toString());
                if (valAmount > 0) {
                    transfer.setAmount(valAmount);
                    transfer.setSenderCurrency(currencyText1);
                    transfer.setReceiverCurrency(currencyText2);
                    transfer.setRecieverAmount(valReceiver);
                    transfer.setTransferType("SENT");
                    transfer.setStatus(TransferStatus.Pending);
                    Intent intent = new Intent(ChooseAmountActivity.this, SuccessActivity.class);
                    ProfileManager.addTransfer(transfer);
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
