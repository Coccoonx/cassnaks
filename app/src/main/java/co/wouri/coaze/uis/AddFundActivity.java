package co.wouri.coaze.uis;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import co.wouri.coaze.R;
import co.wouri.coaze.utils.UIUtils;

public class AddFundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_funds);
        buildToolBar();


        TextView addFundText = (TextView) findViewById(R.id.add_fund_text);
        CardView addFundCard1 = (CardView) findViewById(R.id.cardview_add_funds_body1);
        TextView labelText1 = (TextView) addFundCard1.findViewById(R.id.details_title);
        TextView contentText1 = (TextView) addFundCard1.findViewById(R.id.details_date);
        labelText1.setText("Email:");
        contentText1.setText("addfunds@coaze.com");
        CardView addFundCard2 = (CardView) findViewById(R.id.cardview_add_funds_body2);
        TextView labelText2 = (TextView) addFundCard2.findViewById(R.id.details_title);
        TextView contentText2 = (TextView) addFundCard2.findViewById(R.id.details_amount_recieved);
        labelText2.setText("Question:");
        contentText2.setText("x136");
        CardView addFundCard3 = (CardView) findViewById(R.id.cardview_add_funds_body3);
        TextView labelText3 = (TextView) addFundCard3.findViewById(R.id.details_title);
        TextView contentText3 = (TextView) addFundCard3.findViewById(R.id.details_amount_recieved);
        labelText3.setText("Answer:");
        contentText3.setText("3217");
        Button addButton = (Button) findViewById(R.id.got_it);
        addButton.setText("GOT IT");

        // setFont
        UIUtils.setFont(UIUtils.Font.MUSEOSANS_500, addFundText, labelText1, contentText1,
                labelText2, contentText2, labelText3, contentText3, addButton);


    }

    private void buildToolBar() {
        View toolbar = findViewById(R.id.toolbar);

        ImageView close = (ImageView) toolbar.findViewById(R.id.leftIcon);
        TextView title = (TextView) toolbar.findViewById(R.id.title);
        ImageView option = (ImageView) toolbar.findViewById(R.id.rightIcon);

        title.setText("ADD FUNDS");
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
        getMenuInflater().inflate(R.menu.menu_add_fund, menu);
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
}
