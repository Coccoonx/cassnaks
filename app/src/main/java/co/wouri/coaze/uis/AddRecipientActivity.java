package co.wouri.coaze.uis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import co.wouri.coaze.R;
import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.utils.UIUtils;

public class AddRecipientActivity extends AppCompatActivity {
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText phoneNumber;
    EditText city;
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipient);

        buildToolBar();


        firstName = (EditText) findViewById(R.id.name_add_recipient);

        city = (EditText) findViewById(R.id.city_add_recipient);
        address = (EditText) findViewById(R.id.address_add_recipient);

        email = (EditText) findViewById(R.id.email_add_recipient);

        phoneNumber = (EditText) findViewById(R.id.phone_add_recipient);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, firstName, city, address, email, phoneNumber);


        countries = (Spinner) findViewById(R.id.countries);

//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
//                R.array.countries, R.layout.custom_spinner_countries);

        final String[] countrie = {"", "Canada", "Cameroon", "China", "USA"};

        MyArrayAdapter
                mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, countrie);
        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        countries.setAdapter(mySpinnerArrayAdapter);
        addButton = (Button) findViewById(R.id.button_add_recipient);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Recipient recipient = new Recipient("yolande","Tchagwouo","30610Dla");
                recipient.setFirstName(firstName.getText().toString());
                recipient.setEmail(email.getText().toString());
                recipient.setPhoneNumber(phoneNumber.getText().toString());
                recipient.setCountry(countrie.toString());
                recipient.setCity(city.getText().toString());
                recipient.setAddress(address.getText().toString());

                String response = AccountManager.addRecipient(recipient);
                if (response != null) {
                    Toast.makeText(AddRecipientActivity.this, "Done", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(AddRecipientActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }



                Intent intent = new Intent(AddRecipientActivity.this, EditRecipientActivity.class);
               // intent.putExtra("name", name.getText().toString());

                startActivityForResult(intent, 1);
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