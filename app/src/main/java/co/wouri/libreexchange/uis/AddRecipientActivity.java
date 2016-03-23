package co.wouri.libreexchange.uis;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import static co.wouri.libreexchange.utils.FormValidationUtils.checkAddress;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkCity;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkCountry;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkEmail;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkFirstName;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkLastName;
import static co.wouri.libreexchange.utils.FormValidationUtils.checkPhone;

public class AddRecipientActivity extends AppCompatActivity {
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phone;
    EditText city;
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipient);

        buildToolBar();


        firstName = (EditText) findViewById(R.id.firstName_add_recipient);
        lastName = (EditText) findViewById(R.id.lastName_add_recipient);

        city = (EditText) findViewById(R.id.city_add_recipient);
        address = (EditText) findViewById(R.id.address_add_recipient);

        email = (EditText) findViewById(R.id.email_add_recipient);

        phone = (EditText) findViewById(R.id.phone_add_recipient);
        countries = (Spinner) findViewById(R.id.countries);
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, firstName, lastName, city, address, email, phone);

        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) countries.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorPrimary));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String[] country = {"Choose Country", "Canada", "Cameroon", "China", "USA"};

        MyArrayAdapter
                mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, country);
        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        countries.setAdapter(mySpinnerArrayAdapter);
        addButton = (Button) findViewById(R.id.button_add_recipient);
//        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position == 0)
//                    Toast.makeText(AddRecipientActivity.this,"Choose a country !",Toast.LENGTH_SHORT);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(AddRecipientActivity.this,"Choose a country !",Toast.LENGTH_SHORT);
//            }
//        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstNameValue = firstName.getText().toString();
                String lastNameValue = lastName.getText().toString();
                String emailValue = email.getText().toString();
                String phoneValue = phone.getText().toString();
                String countryValue = countries.getSelectedItem().toString();
                String cityValue = city.getText().toString();
                String addressValue = address.getText().toString();
                if (countries.getSelectedItemPosition() == 0) {
                    Toast.makeText(AddRecipientActivity.this, "Choose a country !", Toast.LENGTH_SHORT).show();
                } else if (!checkFirstName(AddRecipientActivity.this, firstNameValue)
                        || !checkLastName(AddRecipientActivity.this, lastNameValue)
                        || !checkEmail(AddRecipientActivity.this, emailValue)
                        || !checkPhone(AddRecipientActivity.this, phoneValue)
                        || !checkCity(AddRecipientActivity.this, cityValue)
                        || !checkAddress(AddRecipientActivity.this, addressValue)
                        || !checkCountry(AddRecipientActivity.this, countryValue)

                        ) {


                } else {
                    Recipient recipient = new Recipient();
                    recipient.setFirstName(firstNameValue);
                    recipient.setLastName(lastNameValue);
                    recipient.setEmail(emailValue);
//                    recipient.setPhoneNumber(phoneValue);
                    recipient.setCountry(countryValue);
                    recipient.setCity(cityValue);
                    recipient.setAddress(addressValue);
//                    recipient.setImageUri(R.drawable.user_profile);
                    ProfileManager.addRecipient(recipient);

                    //We must call a backend method here
                    Toast.makeText(AddRecipientActivity.this, "Recipient added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
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

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("ADD RECIPIENT");
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