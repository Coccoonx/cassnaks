package co.wouri.coaze.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import static co.wouri.coaze.utils.FormValidationUtils.checkAddress;
import static co.wouri.coaze.utils.FormValidationUtils.checkCity;
import static co.wouri.coaze.utils.FormValidationUtils.checkCountry;
import static co.wouri.coaze.utils.FormValidationUtils.checkEmail;
import static co.wouri.coaze.utils.FormValidationUtils.checkName;
import static co.wouri.coaze.utils.FormValidationUtils.checkPhone;
import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.utils.UIUtils;


public class EditRecipientActivity extends AppCompatActivity {

    public static final String TAG = " edit recipient";
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText name, email, phone, city, address;
    MyArrayAdapter mySpinnerArrayAdapter;
    Recipient recipient;
    String[] country = {"Canada", "Cameroon", "China", "USA"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipient);

        //initUI();
        buildToolBar();


        name = (EditText) findViewById(R.id.name_edit_recipient);

        city = (EditText) findViewById(R.id.city_edit_recipient);
        address = (EditText) findViewById(R.id.address_edit_recipient);

        email = (EditText) findViewById(R.id.email_edit_recipient);

        phone = (EditText) findViewById(R.id.phone_edit_recipient);
        countries = (Spinner) findViewById(R.id.countries);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, name, city, address, email, phone);
        mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, country);
        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        countries.setAdapter(mySpinnerArrayAdapter);
        addButton = (Button) findViewById(R.id.button_edit_recipient);


        Bundle bundle = getIntent().getExtras();
        recipient = bundle.getParcelable("recipient");
        name.setText(recipient.getFirstName() + " " + recipient.getLastName());
        city.setText(recipient.getCity());
        address.setText(recipient.getAddress());
        email.setText(recipient.getEmail());
        phone.setText(recipient.getPhoneNumber());
        countries.setSelection(mySpinnerArrayAdapter.getPosition(recipient.getCountry()));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String emailValue = email.getText().toString();
                String phoneValue = phone.getText().toString();
                String countryValue = countries.getSelectedItem().toString();
                String cityValue = city.getText().toString();
                String addressValue = address.getText().toString();
                if (!checkName(EditRecipientActivity.this, nameValue)
                        || !checkEmail(EditRecipientActivity.this, emailValue)
                        || !checkPhone(EditRecipientActivity.this, phoneValue)
                        || !checkCity(EditRecipientActivity.this, cityValue)
                        || !checkAddress(EditRecipientActivity.this, addressValue)
                        || !checkCountry(EditRecipientActivity.this, countryValue)
                        ) {
                    Intent intent = new Intent(EditRecipientActivity.this, EditRecipientActivity.class);
                    intent.putExtra("name", nameValue);
                    intent.putExtra("email", emailValue);
                    intent.putExtra("phone", phoneValue);
                    intent.putExtra("city", cityValue);
                    intent.putExtra("address", addressValue);
                    startActivityForResult(intent, 1);
                } else {
//                    Intent intent = new Intent(ProfileActivity.this, AddRecipientActivity.class);
//                    intent.putExtra("name", nameValue);
//                    startActivityForResult(intent, 1);
                    //We should call the backend functions here
                    Toast.makeText(EditRecipientActivity.this, "Recipient saved Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(EditRecipientActivity.this, MainActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });


    }


//   private void initUI(){
//
//       Bundle bundle = getIntent().getExtras();
//       recipient = bundle.getParcelable("recipient");
//       name.setText(recipient.getName());
//       city.setText(recipient.getCity());
//       address.setText(recipient.getAddress());
//       email.setText(recipient.getEmail());
//       phone.setText(recipient.getPhoneNumber());
//       countries.setSelection(mySpinnerArrayAdapter.getPosition(recipient.getCountry()));
//    }


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
