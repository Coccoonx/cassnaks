package co.wouri.libreexchange.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.utils.UIUtils;


public class EditRecipientActivity extends AppCompatActivity {

    public static final String TAG = " edit recipient";
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText firstName, lastName, email, phone, city, address;
    MyArrayAdapter mySpinnerArrayAdapter;
    Recipient recipient;
    String[] country = {"Canada", "Cameroon", "China", "USA"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipient);

        //initUI();
        buildToolBar();


        firstName = (EditText) findViewById(R.id.firstName_edit_recipient);
        lastName = (EditText) findViewById(R.id.lastName_edit_recipient);

        city = (EditText) findViewById(R.id.city_edit_recipient);
        address = (EditText) findViewById(R.id.address_edit_recipient);

        email = (EditText) findViewById(R.id.email_edit_recipient);

        phone = (EditText) findViewById(R.id.phone_edit_recipient);
        countries = (Spinner) findViewById(R.id.countries);

        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) countries.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, firstName, lastName, city, address, email, phone);
        mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, country);
        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        countries.setAdapter(mySpinnerArrayAdapter);
        addButton = (Button) findViewById(R.id.button_edit_recipient);


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            recipient = bundle.getParcelable("recipient");
            Log.d(TAG, "reference " + recipient);
            firstName.setText(recipient.getFirstName());
            lastName.setText(recipient.getLastName());
            city.setText(recipient.getCity());
            address.setText(recipient.getAddress());
            email.setText(recipient.getEmail());
            phone.setText(recipient.getPhoneNumber());
            countries.setSelection(mySpinnerArrayAdapter.getPosition(recipient.getCountry()));

        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recipient.setAddress(address.getText().toString());
                recipient.setEmail(email.getText().toString());
                recipient.setPhoneNumber(phone.getText().toString());
                recipient.setFirstName(firstName.getText().toString());
                recipient.setLastName(lastName.getText().toString());
                recipient.setCity(city.getText().toString());
                recipient.setCountry(countries.getSelectedItem().toString());
                Log.d(TAG, "the recipient " + recipient);
                ProfileManager.updateRecipient(recipient);
                Toast.makeText(EditRecipientActivity.this,"recipient successfully edited",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("EDIT RECIPIENT");
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


    public class MyArrayAdapter extends ArrayAdapter {

        public MyArrayAdapter(Context context, int textViewResourceId, String[] countries) {
            super(context, textViewResourceId, countries);
        }

        public TextView getView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) super.getView(position, convertView, parent);
            v.setTypeface(UIUtils.getTypeface(UIUtils.Font.MUSEOSANS_500));
            return v;
        }

        public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView v = (TextView) super.getView(position, convertView, parent);
            v.setTypeface(UIUtils.getTypeface(UIUtils.Font.MUSEOSANS_500));
            return v;
        }

    }
}
