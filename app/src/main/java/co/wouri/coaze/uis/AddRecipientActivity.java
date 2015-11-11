package co.wouri.coaze.uis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import co.wouri.coaze.R;
import co.wouri.coaze.core.managers.AccountManager;
import co.wouri.coaze.core.models.Recipient;
import co.wouri.coaze.utils.UIUtils;

import static co.wouri.coaze.utils.FormValidationUtils.checkAddress;
import static co.wouri.coaze.utils.FormValidationUtils.checkCity;
import static co.wouri.coaze.utils.FormValidationUtils.checkCountry;
import static co.wouri.coaze.utils.FormValidationUtils.checkEmail;
import static co.wouri.coaze.utils.FormValidationUtils.checkName;
import static co.wouri.coaze.utils.FormValidationUtils.checkPhone;

public class AddRecipientActivity extends AppCompatActivity {
    Spinner countries;
    Toolbar toolbar;
    Button addButton;
    EditText name;
    EditText email;
    EditText phone;
    EditText city;
    EditText address;
    LinearLayout gapLayout, frameContainer;
    RelativeLayout toolbarSpecial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipient);

        buildToolBar();


        name = (EditText) findViewById(R.id.name_add_recipient);

        city = (EditText) findViewById(R.id.city_add_recipient);
        address = (EditText) findViewById(R.id.address_add_recipient);

        email = (EditText) findViewById(R.id.email_add_recipient);

        phone = (EditText) findViewById(R.id.phone_add_recipient);
        countries = (Spinner) findViewById(R.id.countries);
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, name, city, address, email, phone);


        String[] country = {"Choose Country","Canada", "Cameroon", "China", "USA"};

        MyArrayAdapter
                mySpinnerArrayAdapter = new MyArrayAdapter(this, R.layout.custom_spinner_countries, country);
        mySpinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        countries.setAdapter(mySpinnerArrayAdapter);
        addButton = (Button) findViewById(R.id.button_add_recipient);
        toolbarSpecial = (RelativeLayout) findViewById(R.id.toolbar);

        // Set Button height
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        int screenHeight = display.getHeight();
//        DisplayMetrics metrics = getResources().getDisplayMetrics();
//
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
         gapLayout = (LinearLayout) findViewById(R.id.gap_layout);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        switch(metrics.densityDpi){
//            case DisplayMetrics.DENSITY_LOW:
//                Log.d("densityDpi", "Low");
//                gapLayout.setMinimumHeight(5);
//                break;
//            case DisplayMetrics.DENSITY_MEDIUM:
//                Log.d("densityDpi", "Medium");
//                gapLayout.setMinimumHeight(10);
//                break;
//            case DisplayMetrics.DENSITY_HIGH:
//                Log.d("densityDpi", "High");
//                gapLayout.setMinimumHeight(25);
//                break;
//        }
//        addButton.setHeight(20);
//        frameContainer = (LinearLayout) findViewById(R.id.frame_container);
//        gapLayout.setMinimumHeight(height-(toolbarSpecial.getHeight()+20+frameContainer.getHeight()));

        switch (getDensityName(this)){
            case "xxxhdpi":
                Log.d("densityDpi", "Very Big");
                gapLayout.setMinimumHeight(75);
                break;
            case "xxhdpi":
                Log.d("densityDpi", "Big");
                gapLayout.setMinimumHeight(50);
                break;
            case "xhdpi":
                Log.d("densityDpi", "Medium");
                gapLayout.setMinimumHeight(30);
                break;
            case "hdpi":
                Log.d("densityDpi", "Normal");
                gapLayout.setMinimumHeight(25);
                break;
            case "mdpi":
                Log.d("densityDpi", "Small");
                gapLayout.setMinimumHeight(15);
                break;
            case "ldpi":
                Log.d("densityDpi", "Very Small");
                gapLayout.setMinimumHeight(5);
                break;
        }
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameValue = name.getText().toString();
                String emailValue = email.getText().toString();
                String phoneValue = phone.getText().toString();
                String countryValue = countries.getSelectedItem().toString();
                String cityValue = city.getText().toString();
                String addressValue = address.getText().toString();
                if (!checkName(AddRecipientActivity.this, nameValue)
                        || !checkEmail(AddRecipientActivity.this, emailValue)
                        || !checkPhone(AddRecipientActivity.this, phoneValue)
                        || !checkCity(AddRecipientActivity.this, cityValue)
                        || !checkAddress(AddRecipientActivity.this, addressValue)
                        || !checkCountry(AddRecipientActivity.this, countryValue)
                        ) {
                    Intent intent = new Intent(AddRecipientActivity.this, AddRecipientActivity.class);
                    intent.putExtra("name", nameValue);
                    intent.putExtra("email", emailValue);
                    intent.putExtra("phone", phoneValue);
                    intent.putExtra("city", cityValue);
                    intent.putExtra("address", addressValue);
                    startActivityForResult(intent, 1);

                } else {

                    //We must call a backend method here
                    Toast.makeText(AddRecipientActivity.this, "Recipient added Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    Intent intent = new Intent(AddRecipientActivity.this, MainActivity.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
        Intent intent1 = getIntent();
        name = (EditText) findViewById(R.id.name_add_recipient);
        city = (EditText) findViewById(R.id.city_add_recipient);
        address = (EditText) findViewById(R.id.address_add_recipient);
        email = (EditText) findViewById(R.id.email_add_recipient);
        phone = (EditText) findViewById(R.id.phone_add_recipient);

        if (intent1 != null) {
            name.setText(intent1.getStringExtra("name"));
            city.setText(intent1.getStringExtra("city"));
            address.setText(intent1.getStringExtra("address"));
            email.setText(intent1.getStringExtra("email"));
            phone.setText(intent1.getStringExtra("phone"));
        }
    }

    private static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
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