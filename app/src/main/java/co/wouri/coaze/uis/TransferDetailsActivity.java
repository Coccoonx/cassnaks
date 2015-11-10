package co.wouri.coaze.uis;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.core.models.Person;
import co.wouri.coaze.utils.UIUtils;

public class TransferDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_details);


        buildToolBar();


//       Person person= (Person) getIntent().getParcelableExtra("person");
        Intent intent = getIntent();
        Person person = new Person();
        person.setName(intent.getStringExtra("personName"));
        person.setAmount(intent.getIntExtra("personAmount", 0));
//        person.setPhotoId((Bitmap) intent.getParcelableExtra("personPhotoId"));
        person.setTransfertDate(intent.getStringExtra("personTransferDate"));
        person.setTransfertType(intent.getStringExtra("personTransferType"));
        byte[] byteArray = getIntent().getByteArrayExtra("personByteArray");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        person.setPhotoId(bmp);
        Log.d("Coaze", "person= " + person);
        // set header
        CardView headCardView = (CardView) findViewById(R.id.cardview_transfer_details_head);
        ImageView personPhotoView = (ImageView) headCardView.findViewById(R.id.details_person_photo);
        TextView personNameView = (TextView) headCardView.findViewById(R.id.details_person_name);
        TextView personAmountView = (TextView) headCardView.findViewById(R.id.details_person_amount);
        personPhotoView.setImageBitmap(person.getPhotoId());
        personNameView.setText(person.getName());
        personAmountView.setText("$" + person.getAmount() + ".00");
        //set Body
        CardView bodyCardView1 = (CardView) findViewById(R.id.cardview_transfer_details_body1);
        TextView titleView1 = (TextView) bodyCardView1.findViewById(R.id.details_title);
        TextView dateView1 = (TextView) bodyCardView1.findViewById(R.id.details_date);
        titleView1.setText("Send:");
        dateView1.setText("01.10.2015 / 12:32PM ");
        CardView bodyCardView2 = (CardView) findViewById(R.id.cardview_transfer_details_body2);
        TextView titleView2 = (TextView) bodyCardView2.findViewById(R.id.details_title);
        TextView dateView2 = (TextView) bodyCardView2.findViewById(R.id.details_date);
        titleView2.setText("Notified:");
        dateView2.setText("01.10.2015 / 12:35PM ");
        CardView bodyCardView3 = (CardView) findViewById(R.id.cardview_transfer_details_body3);
        TextView titleView3 = (TextView) bodyCardView3.findViewById(R.id.details_title);
        TextView dateView3 = (TextView) bodyCardView3.findViewById(R.id.details_date);
        titleView3.setText("Recieved:");
        dateView3.setText("01.10.2015 / 12:59PM ");
        //set End
        CardView endCardView = (CardView) findViewById(R.id.cardview_transfer_details_end);
        TextView endTitleView = (TextView) endCardView.findViewById(R.id.details_title);
        TextView endAmountView = (TextView) endCardView.findViewById(R.id.details_amount_recieved);
        endTitleView.setText("Amount Recieved:");
        endAmountView.setText("€75.00");
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
