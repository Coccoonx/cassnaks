package co.wouri.libreexchange.uis;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import co.wouri.libreexchange.R;
import co.wouri.libreexchange.core.models.Transfer;
import co.wouri.libreexchange.utils.UIUtils;

public class TransferDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);

        buildToolBar();

        Intent intent = getIntent();

        Transfer transfer = intent.getParcelableExtra("transfer");

        // set header
        CardView headCardView = (CardView) findViewById(R.id.cardview_transfer_details_head);
        ImageView personPhotoView = (ImageView) headCardView.findViewById(R.id.details_person_photo);
        TextView personNameView = (TextView) headCardView.findViewById(R.id.details_person_name);
        TextView personAmountView = (TextView) headCardView.findViewById(R.id.details_person_amount);

        // Image of the recipient
//        personPhotoView.setImageBitmap(person.getPhotoId());
        personPhotoView.setImageResource(transfer.getRecipient().getImage());
        personNameView.setText(transfer.getRecipient().getFirstName());
        personAmountView.setText(transfer.getSenderCurrency() + " " + transfer.getAmount());

        //set Body
        CardView bodyCardView1 = (CardView) findViewById(R.id.cardview_transfer_details_body1);
        TextView titleView1 = (TextView) bodyCardView1.findViewById(R.id.details_title);
        TextView dateView1 = (TextView) bodyCardView1.findViewById(R.id.details_date);
        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy / hh:mmaa");
        String transferDate = sdf.format(transfer.getCreationDate());
        titleView1.setText("Send:");
        dateView1.setText(transferDate);

        CardView bodyCardView2 = (CardView) findViewById(R.id.cardview_transfer_details_body2);
        TextView titleView2 = (TextView) bodyCardView2.findViewById(R.id.details_title);
        TextView dateView2 = (TextView) bodyCardView2.findViewById(R.id.details_date);
        titleView2.setText("Notified:");
        dateView2.setText(transfer.getStatus().toString());

        CardView bodyCardView3 = (CardView) findViewById(R.id.cardview_transfer_details_body3);
        TextView titleView3 = (TextView) bodyCardView3.findViewById(R.id.details_title);
        TextView dateView3 = (TextView) bodyCardView3.findViewById(R.id.details_date);
        titleView3.setText("Received:");
        dateView3.setText("pending");

        //set End
        CardView endCardView = (CardView) findViewById(R.id.cardview_transfer_details_end);
        TextView endTitleView = (TextView) endCardView.findViewById(R.id.details_title);
        TextView endAmountView = (TextView) endCardView.findViewById(R.id.details_amount_recieved);
        endTitleView.setText("Amount Received:");
        endAmountView.setText(transfer.getReceiverCurrency() + " " + transfer.getRecieverAmount());

        // set Font
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, endAmountView, endTitleView, titleView3,
                dateView3, dateView2, titleView2, dateView1, titleView1, personAmountView, personNameView);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_transfer_details, menu);
        return true;
    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("TRANSFER DETAILS");
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_quit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}