package co.wouri.libreexchange.uis;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.managers.ProfileManager;
import co.wouri.libreexchange.core.models.Recipient;
import co.wouri.libreexchange.utils.FormValidationUtils;
import co.wouri.libreexchange.utils.UIUtils;


public class EditRecipientActivity extends AppCompatActivity {

    public static final String TAG = " edit recipient";
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText firstName, lastName, email, phone, city, address;
    //    MyArrayAdapter mySpinnerArrayAdapter;
    Recipient recipient;
    private EditText country;
//    String[] country = {"Canada", "Cameroon", "China", "USA"};


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
        country = (EditText) findViewById(R.id.country_edit_recipient);
//        countries = (Spinner) findViewById(R.id.countries);

//        countries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ((TextView) countries.getSelectedView()).setTextColor(getResources().getColor(R.color.textColorPrimary));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, firstName, lastName, city, address, email, phone);
//        mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, country);
//        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


//        countries.setAdapter(mySpinnerArrayAdapter);
        addButton = (Button) findViewById(R.id.button_edit_recipient);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            recipient = bundle.getParcelable("recipient");
            Log.d(TAG, "reference " + recipient);
            firstName.setText(recipient.getFirstName());
            lastName.setText(recipient.getLastName());
            city.setText(recipient.getCity());
            address.setText(recipient.getAddress());
            email.setText(recipient.getEmail());
            if (recipient.getPhoneNumbers().size() > 0)
                phone.setText(recipient.getPhoneNumbers().get(0));
            country.setText(recipient.getCountry());
//            countries.setSelection(mySpinnerArrayAdapter.getPosition(recipient.getCountry()));

        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recipient.setAddress(address.getText().toString());
                recipient.setEmail(email.getText().toString());
//                recipient.setPhoneNumber(phone.getText().toString());
                recipient.setFirstName(firstName.getText().toString());
                recipient.setLastName(lastName.getText().toString());
                recipient.setCity(city.getText().toString());
                recipient.setCountry(country.getText().toString());
                Log.d(TAG, "the recipient " + recipient);
                ProfileManager.updateRecipient(recipient);
                updateContactInPhoneBook(recipient);
                Toast.makeText(EditRecipientActivity.this, "recipient successfully edited", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditRecipientActivity.this, ChooseAmountActivity.class);
                intent.putExtra("recipient", (Parcelable) recipient);
                startActivity(intent);
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

    private boolean updateContactInPhoneBook(Recipient recipient) {
        return updateContact(recipient.getImageUri(),
                recipient.getFirstName() + " " + recipient.getLastName(),
//                recipient.getPhoneNumbers().get(0),
                recipient.getEmail(), recipient.getCity(),
                recipient.getCountry());
    }

    public boolean updateContact(String ContactId, String name, /*String number,*/ String email, String city, String country) {
        boolean success = true;
        String phnumexp = "^[0-9]*$";

        try {
            name = name.trim();
            email = email.trim();
//            number = number.trim();
            city = city.trim();
            country = country.trim();


            /*if (name.equals("") && number.equals("") && email.equals("")) {
                success = false;
            } else if ((!number.equals("")) && (!FormValidationUtils.checkPhone(number))) {
                success = false;
            } else */if ((!email.equals("")) && (!FormValidationUtils.checkEmail(email))) {
                success = false;
            } else {
                ContentResolver contentResolver = getContentResolver();

                String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";


                String[] emailParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE};
                String[] nameParams = new String[]{ContactId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                String[] numberParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
                String[] addressParams = new String[]{ContactId, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

                ArrayList<android.content.ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                if (!email.equals("")) {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, emailParams)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                            .build());
                }

                if (!name.equals("")) {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, nameParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                            .build());
                }

//                if (!number.equals("")) {
//
//                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
//                            .withSelection(where, numberParams)
//                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
//                            .build());
//                }

                if (!city.equals("")) {

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, addressParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, city)
                            .build());
                }

                if (!country.equals("")) {

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, addressParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, country)
                            .build());
                }
                contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
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
