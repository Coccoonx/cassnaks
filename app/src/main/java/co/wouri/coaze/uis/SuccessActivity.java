package co.wouri.coaze.uis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Transfer;
import co.wouri.coaze.utils.UIUtils;

public class SuccessActivity extends AppCompatActivity {

    TextView successTV;
    TextView details1;
    TextView details2;
    TextView details3;
    TextView recipientName;
    TextView amount;
    EditText amount2;
    TextView was_sent;
    Button done;
    Button transfer;
    public static String TAG = "SuccessActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Bundle bundle = getIntent().getExtras();
        Log.d(TAG, "bundle "+ bundle);

        successTV = (TextView) findViewById(R.id.successLabel);
        details1 = (TextView) findViewById(R.id.details_text1);
        details2 = (TextView) findViewById(R.id.details_text2);
        details3 = (TextView) findViewById(R.id.details_text3);
        recipientName = (TextView) findViewById(R.id.recipient_name);
        amount = (TextView) findViewById(R.id.amountValue);
        amount2 = (EditText)findViewById(R.id.amount);
        was_sent = (TextView) findViewById(R.id.was_sent);

        if (bundle != null){
            Transfer transfer =(Transfer) bundle.getParcelable("transfer");
            //amount2.getText().toString();

            if (transfer != null) {
                amount.setText(transfer.getSenderCurrency());
                recipientName.setText(transfer.getRecipient().getFirstName());
            }


        }
        buildToolBar();
        done = (Button) findViewById(R.id.done);
        transfer = (Button) findViewById(R.id.transfers);

        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, successTV, details1, details2, details3, amount,
                recipientName, done, was_sent, transfer);

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessActivity.this, TransferHistoryActivity.class));
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessActivity.this, MainActivity.class));
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
//        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("");
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, title);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        title.setVisibility(View.VISIBLE);
//        option.setVisibility(View.VISIBLE);

    }
}
